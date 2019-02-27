package com.mx.exception;

/**
 * @author 小米线儿
 * @time 2019/2/13 0013
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public enum ExceptionEnum {

    UNKNOW(999,"未知错误"),
    SYSTEM_ERROR(100,"系统错误");

    ExceptionEnum(Integer status,String msg) {
        this.msg = msg;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    private Integer status;

    private String msg;

}
