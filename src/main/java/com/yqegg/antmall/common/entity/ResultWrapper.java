package com.yqegg.antmall.common.entity;

import com.yqegg.antmall.common.constant.ResultCode;
import lombok.Data;

// 这个是和前端进行交互的时候，统一的返回实体
@Data
public class ResultWrapper<T> {
    // 是否成功
    private Boolean success = true;
    // 状态码（结果码是从constant.ResultCode来取的
    private Integer code;
    // 实际的数据
    private T data;
    // 失败信息放到message
    private String message;

    private ResultWrapper() {
    }

    public static <T> ResultWrapper<T> success() {
        return success(null);
    }

    public static <T> ResultWrapper<T> success(T data) {
        return assemble(ResultCode.SUCCESS.getCode(), true, data);
    }

    public static <T> ResultWrapper<T> error() {
        return error(null);
    }

    public static <T> ResultWrapper<T> error(T data) {
        return assemble(ResultCode.SYSTEM_FAILURE.getCode(), false, data);
    }

    public ResultWrapper<T> data(T data) {
        this.setData(data);
        return this;
    }

    public ResultWrapper<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public ResultWrapper<T> code(int code) {
        this.setCode(code);
        return this;
    }

    public static <T> ResultWrapper<T> assemble(int code, boolean success, T data) {
        ResultWrapper<T> resultWrapper = new ResultWrapper<>();
        resultWrapper.setCode(code);
        resultWrapper.setSuccess(success);
        resultWrapper.setData(data);

        return resultWrapper;
    }
}