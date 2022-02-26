package fit.hcp.imoocsecskill.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
public class SeckillProduct implements Serializable {
    // 商品 id
    private Long seckillId;
    // 商品名称
    private String name;
    // 库存
    private int number;
    // 秒杀开始时间
    private Date startTime;
    // 秒杀结束时间
    private Date endTime;
    // 秒杀商品创建时间
    private Date createTime;
}
