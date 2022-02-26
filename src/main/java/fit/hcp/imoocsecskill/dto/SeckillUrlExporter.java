package fit.hcp.imoocsecskill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


/**
 * 用于暴露秒杀地址
 * 数据传输层实体，在业务代码间传输数据使用。
 *
 */
@Data
public class SeckillUrlExporter {
    private boolean exposed; // 是否开启秒杀
    private String md5; // 加密之后的字符串
    private long seckillId;
    private long currentTimeStamp;
    private long startTimeStamp;
    private long endTimeStamp;

    public SeckillUrlExporter(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public SeckillUrlExporter(boolean exposed, long seckillId, long currentTimeStamp, long startTimeStamp, long endTimeStamp) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.currentTimeStamp = currentTimeStamp;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
    }

    public SeckillUrlExporter(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }
}
