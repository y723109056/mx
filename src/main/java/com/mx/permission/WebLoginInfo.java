package com.mx.permission;

import java.io.Serializable;
import java.util.List;

public class WebLoginInfo implements ILoginInfo,Serializable {
	private static final long serialVersionUID = -3104497334502106207L;
	private Integer userId;
	private String userName;
	private String account;
	private String roleIds;
	private List<String> roleNames;
	private String ip;
	private String dataPermitCodes;


	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getRoleIds() {
		return roleIds;
	}
	
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
	public List<String> getRoleNames() {
		return roleNames;
	}
	
	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDataPermitCodes() {
		return dataPermitCodes;
	}

	public void setDataPermitCodes(String dataPermitCodes) {
		this.dataPermitCodes = dataPermitCodes;
	}



}
