package fit.hcp.imoocsecskill.dao;

import fit.hcp.imoocsecskill.bean.SeckillProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SeckillProductDao {
    /**
     * 减库存
     *
     * @param seckillId
     * @param killTime
     * @return 影响的行数：>=1 成功，0 失败
     */
    int reduceNumber(long seckillId, Date killTime);

    /**
     * 根据可秒杀的产品的id查询秒杀信息
     *
     * @param seckillId
     * @return
     */
//    @Select("select * from seckill_product where seckill_id = #{seckillId}")
//    @Cacheable(value = "redisCache", key = "'redis_product_'+#seckillId")
    SeckillProduct queryById(long seckillId);

    /**
     * 根据偏离量查询秒杀商品列表
     *
     * @param offset
     * @param limit
     * @return
     */
    List<SeckillProduct> queryAll(int offset, int limit);

    /**
     * 调用存储过程执行秒杀
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);
}
