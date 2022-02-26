package fit.hcp.imoocsecskill.service;

import fit.hcp.imoocsecskill.bean.SeckillProduct;
import fit.hcp.imoocsecskill.dto.SeckillResult;
import fit.hcp.imoocsecskill.dto.SeckillUrlExporter;
import fit.hcp.imoocsecskill.exception.RepeatSeckillException;
import fit.hcp.imoocsecskill.exception.SeckillCloseException;
import fit.hcp.imoocsecskill.exception.SeckillException;

import java.util.List;


public interface SeckillService {
    /**
     * 查询所有秒杀商品
     *
     * @return
     */
    List<SeckillProduct> getAllSeckillProduct();


    /**
     * 查询单个秒杀商品
     *
     * @param seckillId
     * @return
     */
    SeckillProduct getSeckillProductById(long seckillId);

    /**
     * 暴露秒杀地址
     *
     * @param seckillId
     */
    SeckillUrlExporter exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatSeckillException
     * @throws SeckillCloseException
     */
    SeckillResult executeSeckill(long seckillId, String userPhone, String md5)
            throws SeckillException, RepeatSeckillException, SeckillCloseException;

    /**
     * 执行秒杀操作的存储过程
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatSeckillException
     * @throws SeckillCloseException
     */
    SeckillResult executeSeckillProcedure(long seckillId, String userPhone, String md5);
}
