package fit.hcp.imoocsecskill.service.impl;

import fit.hcp.imoocsecskill.bean.SeckillProduct;
import fit.hcp.imoocsecskill.bean.SuccessKilledRecord;
import fit.hcp.imoocsecskill.dao.SeckillProductDao;
import fit.hcp.imoocsecskill.dao.SuccessKilledRecordDao;
import fit.hcp.imoocsecskill.dao.cache.SeckillProductCacheDao;
import fit.hcp.imoocsecskill.dto.SeckillResult;
import fit.hcp.imoocsecskill.dto.SeckillUrlExporter;
import fit.hcp.imoocsecskill.enums.SeckillStateEnum;
import fit.hcp.imoocsecskill.exception.RepeatSeckillException;
import fit.hcp.imoocsecskill.exception.SeckillCloseException;
import fit.hcp.imoocsecskill.exception.SeckillException;
import fit.hcp.imoocsecskill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.thymeleaf.util.MapUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Date: 2022/01/27/10:06 AM
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {
    String slat = "0ljzxvcpu21308i3]sadf[asfd";

    @Autowired
    SeckillProductDao seckillProductDao;

    @Autowired
    SeckillProductCacheDao seckillProductCacheDao;

    @Autowired
    SuccessKilledRecordDao successKilledRecordDao;

    @Override
    public List<SeckillProduct> getAllSeckillProduct() {
        return seckillProductDao.queryAll(0, 4);
    }

    @Override
    public SeckillProduct getSeckillProductById(long seckillId) {

        return seckillProductDao.queryById(seckillId); // 不能使用缓存
    }

    @Override
    public SeckillUrlExporter exportSeckillUrl(long seckillId) {
//        SeckillProduct seckillProduct = seckillProductDao.queryById(seckillId); // todo:用缓存
        SeckillProduct seckillProduct = seckillProductCacheDao.queryById(seckillId);
        if (seckillProduct == null)
            return new SeckillUrlExporter(false, seckillId);
        Date startTime = seckillProduct.getStartTime();
        Date endTime = seckillProduct.getEndTime();
        Date currentTime = new Date();
        if (currentTime.getTime() < startTime.getTime() || currentTime.getTime() > endTime.getTime())
            return new SeckillUrlExporter(false, seckillId, currentTime.getTime(), startTime.getTime(), endTime.getTime());

        String md5 = getMD5(seckillId);
        return new SeckillUrlExporter(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "+" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    @Override
    @Transactional
    public SeckillResult executeSeckill(long seckillId, String userPhone, String md5) throws SeckillException, RepeatSeckillException, SeckillCloseException {
        if (!getMD5(seckillId).equals(md5))
            throw new SeckillException("seckill data rewrite");
        Date nowDate = new Date();
        try {
            // 由于减库存会添加行级锁锁住库存，而插入记录添加的行锁之后不会影响其它记录，所以 1. 查记录 -> 2. 减库存 的顺序可以缩短库存被锁的时间            // 添加秒杀成功的记录
            int insertCount = successKilledRecordDao.insertRecord(seckillId, userPhone);
            if (insertCount <= 0)
                throw new RepeatSeckillException("seckill repeated");
            // 减库存
            int updateCount = seckillProductDao.reduceNumber(seckillId, nowDate);
            if (updateCount <= 0)
                throw new SeckillCloseException("seckill is closed");
            // 秒杀成功
            SuccessKilledRecord successKilledRecord = successKilledRecordDao.queryByIdWithProduct(seckillId, userPhone);
            return new SeckillResult(seckillId, SeckillStateEnum.SUCCESS, successKilledRecord);
        } catch (SeckillCloseException | RepeatSeckillException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }

    @Override
    public SeckillResult executeSeckillProcedure(long seckillId, String userPhone, String md5) {
        if (!getMD5(seckillId).equals(md5))
            throw new SeckillException("seckill data rewrite");
        Date killTime = new Date();
        HashMap<String, Object> map = new HashMap<>();
        map.put("seckillId", seckillId);
        map.put("userPhone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        // 执行存储过程
        seckillProductDao.killByProcedure(map);
        Integer result = (Integer) map.get("result");
        // out_result: 1 秒杀成功, 0 秒杀关闭, -1 重复秒杀, -2 内部错误
        if (result.equals(1)) {
            SuccessKilledRecord successKilledRecord = successKilledRecordDao.queryByIdWithProduct(seckillId, userPhone);
            return new SeckillResult(seckillId, SeckillStateEnum.SUCCESS, successKilledRecord);
        } else if (result.equals(0)) {
            throw new SeckillCloseException("seckill is closed");
        } else if (result.equals(-1)) {
            throw new RepeatSeckillException("seckill repeated");
        } else {
            throw new SeckillException("seckill inner error");
        }
    }
}
