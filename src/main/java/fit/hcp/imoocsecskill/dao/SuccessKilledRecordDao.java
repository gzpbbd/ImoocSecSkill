package fit.hcp.imoocsecskill.dao;

import fit.hcp.imoocsecskill.bean.SuccessKilledRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SuccessKilledRecordDao {
    /**
     * 插入新的秒杀成功的记录
     *
     * @param seckillId
     * @param userPhone
     * @return 影响的行数：>=1 成功，0 失败
     */
    int insertRecord(long seckillId, String userPhone);

    /**
     * 查询记录，并附带对应的秒杀产品返回
     *
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilledRecord queryByIdWithProduct(long seckillId, String userPhone);
}
