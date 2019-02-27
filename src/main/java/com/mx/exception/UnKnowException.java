package com.mx.exception;

/**
 * @author 小米线儿
 * @time 2019/2/13 0013
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class UnKnowException extends RuntimeException {


    private Integer code;

    public UnKnowException(ExceptionEnum e) {
        super(e.getMsg());
        this.code = e.getStatus();
    }

    public UnKnowException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
