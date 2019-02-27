package com.mx.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private static final long serialVersionUID = 724678893659044179L;
    private Integer userId;

    private String userName;

    private String password;

    private String sex;

    private Integer age;

    private String nickName;

    private String phone;

    private String address;

    private String email;

    private String realName;

    private String idCard;

    private String qq;

    private String description;

    private String hobby;

    private String position;

    private String industry;

    private String region;

    private Integer roleId;

    private Integer isActive;

    private Date updateTime;

    private Integer updateUser;

    private Integer createUser;

    private Date createTime;

    public User(){}

    public User(Integer userId, String userName, String password, String sex, Integer age, String nickName, String phone, String address, String email, String realName, String idCard, String qq, String description, String hobby, String position, String industry, String region, Integer roleId, Integer isActive, Date updateTime, Integer updateUser, Integer createUser, Date createTime) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.nickName = nickName;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.realName = realName;
        this.idCard = idCard;
        this.qq = qq;
        this.description = description;
        this.hobby = hobby;
        this.position = position;
        this.industry = industry;
        this.region = region;
        this.roleId = roleId;
        this.isActive = isActive;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.createUser = createUser;
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public Integer getAge() {
        return age;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getRealName() {
        return realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getQq() {
        return qq;
    }

    public String getDescription() {
        return description;
    }

    public String getHobby() {
        return hobby;
    }

    public String getPosition() {
        return position;
    }

    public String getIndustry() {
        return industry;
    }

    public String getRegion() {
        return region;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", realName='" + realName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", qq='" + qq + '\'' +
                ", description='" + description + '\'' +
                ", hobby='" + hobby + '\'' +
                ", position='" + position + '\'' +
                ", industry='" + industry + '\'' +
                ", region='" + region + '\'' +
                ", roleId=" + roleId +
                ", isActive=" + isActive +
                ", updateTime=" + updateTime +
                ", updateUser=" + updateUser +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                '}';
    }
}