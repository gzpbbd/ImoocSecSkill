package fit.hcp.imoocsecskill.exception;

/**
 * 所有秒杀业务异常的父类
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
