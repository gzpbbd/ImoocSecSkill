package fit.hcp.imoocsecskill.dto;

import lombok.Data;

/**
 * 用于给前端返回数据
 * @Date: 2022/01/27/5:40 PM
 */
@Data
public class SeckillResultForClient<T> {
    private boolean success; // todo：解决歧义性
    private T data;
    private String errorMessage;

    public SeckillResultForClient(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResultForClient(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }
}
