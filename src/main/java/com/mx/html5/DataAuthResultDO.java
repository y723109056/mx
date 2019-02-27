package com.mx.html5;

import java.io.Serializable;

/**
 * Created by wdc on 2017/5/16.
 */
public class DataAuthResultDO implements Serializable{

    private String data;

    private Boolean flag;

    private Integer status;

    private String errorCode;

    private String errorMsg;

    private String params;

    public DataAuthResultDO(){

    }
    public DataAuthResultDO(String data, Boolean flag) {
        this.data = data;
        this.flag = flag;
    }

    public DataAuthResultDO(String data, Boolean flag, Integer status, String errorCode, String params, String errorMsg) {
        this.data = data;
        this.flag = flag;
        this.status = status;
        this.errorCode = errorCode;
        this.params = params;
        this.errorMsg = errorMsg;
    }

    public Integer getStatus() {
        return status;
    }

    public DataAuthResultDO setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public DataAuthResultDO setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getData() {
        return data;
    }

    public DataAuthResultDO setData(String data) {
        this.data = data;
        return this;
    }

    public Boolean getFlag() {
        return flag;
    }

    public DataAuthResultDO setFlag(Boolean flag) {
        this.flag = flag;
        return this;
    }

    public String getParams() {
        return params;
    }

    public DataAuthResultDO setParams(String params) {
        this.params = params;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public DataAuthResultDO setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(" data=").append(data);
        sb.append(", flag=").append(flag);
        sb.append(", params=").append(params);
        sb.append(", status=").append(status);
        sb.append(", errorCode").append(errorCode);
        sb.append(", errorMsg").append(errorMsg);
        sb.append("]");
        return sb.toString();
    }
}
