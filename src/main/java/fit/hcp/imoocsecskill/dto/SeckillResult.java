package fit.hcp.imoocsecskill.dto;

import fit.hcp.imoocsecskill.bean.SuccessKilledRecord;
import fit.hcp.imoocsecskill.enums.SeckillStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SeckillResult {
    private long seckillId;
    // 如果直接使用 SeckillStateEnum 作为成员变量，SpringBoot将SeckillResult转为json回传给客户端时，无法得到SeckillStateEnum内部变量的值state, stateInfo，而只能得到类似SUCCESS的字符串
    private int state; // 指明秒杀是否成功
    private String stateInfo; // 秒杀结果的文本描述
    private SuccessKilledRecord successKilledRecord; // 该次秒杀对应的秒杀记录

    public SeckillResult(long seckillId, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public SeckillResult(long seckillId, SeckillStateEnum stateEnum, SuccessKilledRecord successKilledRecord) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successKilledRecord = successKilledRecord;
    }
}
