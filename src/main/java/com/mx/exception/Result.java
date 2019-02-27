package com.mx.exception;

/**
 * @author 小米线儿
 * @time 2019/2/13 0013
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class Result<T> {

    private Integer status;

    private String msg;

    private T data;

    public Result(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
