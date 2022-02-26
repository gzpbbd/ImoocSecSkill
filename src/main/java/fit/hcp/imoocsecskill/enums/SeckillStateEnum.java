package fit.hcp.imoocsecskill.enums;

/**
 * @Date: 2022/01/27/11:20 AM
 */
public enum SeckillStateEnum {
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWARITE(-3, "数据被篡改");

    private int state;
    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStateEnum stateOf(int state) {
        for (SeckillStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state)
                return stateEnum;
        }
        return null;
    }

    @Override
    public String toString() {
        return "SeckillStatEnum." + super.toString() + "{" +
                "state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                '}';
    }
}
