package fit.hcp.imoocsecskill.dao.cache;

import fit.hcp.imoocsecskill.bean.SeckillProduct;
import fit.hcp.imoocsecskill.dao.SeckillProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * @Date: 2022/02/04/9:15 AM
 */
@Repository
@CacheConfig(cacheNames = "SeckillProduct")
public class SeckillProductCacheDao {
    @Autowired
    SeckillProductDao seckillProductDao;

    @Cacheable
    public SeckillProduct queryById(long seckillId){
        return seckillProductDao.queryById(seckillId);
    }
}
