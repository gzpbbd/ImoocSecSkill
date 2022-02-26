package fit.hcp.imoocsecskill.bean;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
public class SuccessKilledRecord {
    private Long seckillId;
    private String userPhone;
    private Integer state;
    private Date createTime;
    // 携带秒杀对象实体
    private SeckillProduct seckillProduct;
}
