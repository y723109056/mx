package com.mx.entity;

import java.util.Date;

public class ActionLog {
    private Integer id;

    private String methodName;

    private String actionTime;

    private String requestUrl;

    private Date createTime;

    private Integer createUser;

    private String memo;

    public ActionLog(String methodName, String actionTime, String requestUrl, Integer createUser, String memo) {
        this.methodName = methodName;
        this.actionTime = actionTime;
        this.requestUrl = requestUrl;
        this.createUser = createUser;
        this.memo = memo;
    }

    public ActionLog(Integer id, String methodName, String actionTime, String requestUrl, Date createTime, Integer createUser, String memo) {
        this.id = id;
        this.methodName = methodName;
        this.actionTime = actionTime;
        this.requestUrl = requestUrl;
        this.createTime = createTime;
        this.createUser = createUser;
        this.memo = memo;
    }

    public Integer getId() {
        return id;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getActionTime() {
        return actionTime;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public String getMemo() {
        return memo;
    }
}