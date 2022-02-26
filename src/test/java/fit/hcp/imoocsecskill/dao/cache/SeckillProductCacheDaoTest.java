package fit.hcp.imoocsecskill.dao.cache;

import fit.hcp.imoocsecskill.bean.SeckillProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Date: 2022/02/04/10:16 AM
 */
@SpringBootTest
class SeckillProductCacheDaoTest {

    @Autowired
    SeckillProductCacheDao seckillProductCacheDao;

    @Test
    void queryById() {
        SeckillProduct seckillProduct = seckillProductCacheDao.queryById(1001);
        System.out.println(seckillProduct);
    }
}