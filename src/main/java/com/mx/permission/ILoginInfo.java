package com.mx.permission;

import java.util.List;

public interface ILoginInfo {
	
	/**
	 * 得到登入用户ID
	 * @return
	 */
	Integer getUserId();
	
	/**
	 * 得到登入用户帐号
	 * @return
	 */
	String getAccount();
	
	/**
	 * 得到登入用户姓名
	 * @return
	 */
	String getUserName();
	
	/**
	 * 得到登入用户IP
	 * @return
	 */
	String getIp();
	
	/**
	 * 得到用户角色名集合
	 * @return
	 */
	List<String> getRoleNames();

	/**
	 * 得到用户数据权限集合
	 * @return
	 */
	String getDataPermitCodes();
	

}
