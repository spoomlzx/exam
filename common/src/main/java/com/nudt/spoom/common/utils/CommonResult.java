package com.nudt.spoom.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * package com.spoom.gear.utils
 *
 * @author spoomlan
 * @date 2019-04-04
 */
@Setter
@Getter
@ToString
public class CommonResult {

    //操作成功
    public static final int SUCCESS = 200;
    //操作失败
    public static final int ERROR = 500;
    //参数校验失败
    public static final int VALIDATE_FAILED = 501;
    //未认证, authentication failure
    public static final int UNAUTHENTICATED = 401;
    //未授权,insufficient permission
    public static final int UNAUTHORIZED = 403;
    private int code;
    private String message;
    private Object data;

    public String toJson() {
        return JSON.toJSONString(this);
    }

    /**
     * success
     *
     * @param data 获取的数据
     */
    public CommonResult success(Object data) {
        this.code = SUCCESS;
        this.message = "success";
        this.data = data;
        return this;
    }

    /**
     * common error
     */
    public CommonResult error() {
        this.code = ERROR;
        this.message = "error";
        return this;
    }

    /**
     * unauthenticated
     *
     * @param message exception message
     */
    public CommonResult unauthenticated(String message) {
        this.code = UNAUTHENTICATED;
        this.message = "unauthenticated";
        this.data = message;
        return this;
    }

    /**
     * insufficient permission
     *
     * @param message exception message
     */
    public CommonResult unauthorized(String message) {
        this.code = UNAUTHORIZED;
        this.message = "insufficient permission";
        this.data = message;
        return this;
    }

    /**
     * argument validate failure
     *
     * @param message exception message
     */
    public CommonResult validateFailed(String message) {
        this.code = VALIDATE_FAILED;
        this.message = message;
        return this;
    }

}
