package com.yqegg.antmall.common.constant;

import lombok.Getter;

@Getter
public enum ResultCode {
    // 结果码，随便定义的
    SUCCESS(1000, "访问成功"),
    SYSTEM_FAILURE(1001, "系统异常"),
    ;

    private final int code;
    private final String description;

    ResultCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

}
