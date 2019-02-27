package com.mx.entity;

import java.util.Date;

public class SqlLog {
    private Integer id;

    private String mapperId;

    private String actionTime;

    private Date createTime;

    private Integer createUser;

    public SqlLog(Integer id, String mapperId, String actionTime, Date createTime, Integer createUser) {
        this.id = id;
        this.mapperId = mapperId;
        this.actionTime = actionTime;
        this.createTime = createTime;
        this.createUser = createUser;
    }


    public SqlLog(String mapperId, String actionTime, Integer createUser) {
        this.mapperId = mapperId;
        this.actionTime = actionTime;
        this.createUser = createUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMapperId() {
        return mapperId;
    }

    public void setMapperId(String mapperId) {
        this.mapperId = mapperId;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
}