package fit.hcp.imoocsecskill.dao;

import fit.hcp.imoocsecskill.bean.SeckillProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeckillProductDaoTest {

    @Autowired
    private SeckillProductDao seckillProductDao;

    @Test
    void reduceNumber() {
        System.out.println(seckillProductDao.queryById(1000));
        int count = seckillProductDao.reduceNumber(1000, new Date());
        System.out.println("count: "+count  );
        System.out.println(seckillProductDao.queryById(1000));
    }

    @Test
    void queryById() {
        System.out.println("------------");
        SeckillProduct seckillProduct = seckillProductDao.queryById(1001);
        System.out.println(seckillProduct);
        System.out.println("------------");
         seckillProduct = seckillProductDao.queryById(1001);
        System.out.println(seckillProduct);
        System.out.println("------------");
    }

    @Test
    void queryAll() {
        for (SeckillProduct product : seckillProductDao.queryAll(0, 100)) {
            System.out.println(product);
        }
    }
}