package fit.hcp.imoocsecskill.service.impl;

import fit.hcp.imoocsecskill.bean.SeckillProduct;
import fit.hcp.imoocsecskill.dto.SeckillResult;
import fit.hcp.imoocsecskill.dto.SeckillUrlExporter;
import fit.hcp.imoocsecskill.exception.RepeatSeckillException;
import fit.hcp.imoocsecskill.exception.SeckillCloseException;
import fit.hcp.imoocsecskill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date: 2022/01/27/1:54 PM
 */
@Slf4j
@SpringBootTest
class SeckillServiceImplTest {

    @Autowired
    private SeckillService seckillService;

    @Test
    void getAllSeckillProduct() {
        for (SeckillProduct seckillProduct : seckillService.getAllSeckillProduct()) {
            log.info("seckill product --> {}", seckillProduct);
        }
    }

    @Test
    void getSeckillProductById() {
        SeckillProduct seckillProduct = seckillService.getSeckillProductById(1001);
        log.info("seckill product --> {}", seckillProduct);
    }

    /**
     * 测试秒杀逻辑（同时使用seckillService 的 exportSeckillUrl 与 executeSeckill 方法）
     */
    @Test
    void seckillLogic() {
        long seckillId = 1000;
        String userPhone = "12313123";
        // 生成秒杀 url
        SeckillUrlExporter seckillUrlExporter = seckillService.exportSeckillUrl(seckillId);
        if (!seckillUrlExporter.isExposed()) {
            log.error("生成秒杀 url 失败：{}", seckillUrlExporter);
            return;
        }
        // 执行秒杀
        try {
            SeckillResult seckillResult = seckillService.executeSeckill(seckillId, userPhone, seckillUrlExporter.getMd5());
            log.info("秒杀成功: {}",seckillResult);
        } catch (SeckillCloseException | RepeatSeckillException e) {
            log.warn(e.getMessage());
        }
    }
    /**
     * 测试MySql的秒杀存储过程（同时使用seckillService 的 exportSeckillUrl 与 executeSeckill 方法）
     */
    @Test
    void executeSeckillProcedure() {
        long seckillId = 1001;
        String userPhone = "12313123";
        // 生成秒杀 url
        SeckillUrlExporter seckillUrlExporter = seckillService.exportSeckillUrl(seckillId);
        if (!seckillUrlExporter.isExposed()) {
            log.error("生成秒杀 url 失败：{}", seckillUrlExporter);
            return;
        }
        // 执行秒杀
        try {
            SeckillResult seckillResult = seckillService.executeSeckillProcedure(seckillId, userPhone, seckillUrlExporter.getMd5());
            log.info("秒杀成功: {}",seckillResult);
        } catch (SeckillCloseException | RepeatSeckillException e) {
            log.warn(e.getMessage());
        }
    }
}