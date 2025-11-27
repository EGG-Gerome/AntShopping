package com.yqegg.antmall.common.exception;

// 自定义的业务异常（比如：当前用户不存在）
public class BusinessException extends RuntimeException{
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
// 系统异常（发生了死锁，内存不足）需要定义一个SystemException，我没有写，就写了这一个