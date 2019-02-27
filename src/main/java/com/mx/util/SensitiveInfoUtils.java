package com.mx.util;

import org.apache.commons.lang.StringUtils;

  
/** 
 * @Title: SensitiveInfoUtils.java 
 * @Copyright: Copyright (c) 2011 
 * @Description: <br> 
 * 敏感信息屏蔽工具<br> 
 */  
public final class SensitiveInfoUtils {  
  
  
    /** 
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**> 
     *  
     * @param name 
     * @return 
     */  
    public static String chineseName(String fullName) {  
        if (StringUtils.isBlank(fullName)) {  
            return "";  
        }  
        String name = StringUtils.left(fullName, 1);  
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");  
    }  
  
    /** 
     * [中文姓名] 只显示第一个汉字，其他隐藏为2个星号<例子：李**> 
     *  
     * @param familyName 
     * @param givenName 
     * @return 
     */  
    public static String chineseName(String familyName, String givenName) {  
        if (StringUtils.isBlank(familyName) || StringUtils.isBlank(givenName)) {  
            return "";  
        }  
        return chineseName(familyName + givenName);  
    }  
  
    /** 
     * [身份证号] 显示前面6位，后面4位，其他隐藏。共计18位或者15位。<例子：330681********1534> 
     *  
     * @param id 
     * @return 
     */  
    public static String idCardNum(String id) {  
        if (StringUtils.isBlank(id)) {  
            return "";  
        }
        if(id.length()==15 || id.length()==18){
        	return StringUtils.left(id, 6).concat(StringUtils.
            		removeStart(StringUtils.leftPad(StringUtils.right(id, 4), 
            				StringUtils.length(id), "*"), "***")); 
        	  
        } 
        //return StringUtils.leftPad(num, StringUtils.length(id), "*");  
        return id;
    }  
  
    /** 
     * [固定电话] 后四位，其他隐藏<例子：****1234> 
     *  
     * @param num 
     * @return 
     */  
    public static String fixedPhone(String num) {  
        if (StringUtils.isBlank(num)) {  
            return "";  
        }  
        return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*");  
    }  
  
    /** 
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234> 
     *  
     * @param num 
     * @return 
     */  
    public static String mobilePhone(String num) {  
        if (StringUtils.isBlank(num)) {  
            return "";  
        }  
        return StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"), "***"));  
    }
    
    public static String fixedCustomerPhone(String num){
    	if (StringUtils.isBlank(num)) {  
            return "";  
        }  
    	int length=num.length();
		/*if(length==11){//手机号码
			return mobilePhone(num);
		}*/
		if(length<=4){
			return num;
		}
		return fixedPhone(num);
    }
  
    /** 
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****> 
     *  
     * @param address 
     * @param sensitiveSize 
     *            敏感信息长度 
     * @return 
     */  
    public static String address(String address, int sensitiveSize) {  
        if (StringUtils.isBlank(address)) {  
            return "";  
        }  
        int length = StringUtils.length(address);  
        return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, "*");  
    }  
  
    /** 
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com> 
     *  
     * @param email 
     * @return 
     */  
    public static String email(String email) {  
        if (StringUtils.isBlank(email)) {  
            return "";  
        }  
        int index = StringUtils.indexOf(email, "@");  
        if (index <= 1)  
            return email;  
        else  
            return StringUtils.rightPad(StringUtils.left(email, 1), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));  
    }  
  
    /** 
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234> 
     *  
     * @param cardNum 
     * @return 
     */  
    public static String bankCard(String cardNum) {  
        if (StringUtils.isBlank(cardNum)) {  
            return "";  
        }  
        return StringUtils.left(cardNum, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"), "******"));  
    }  
  
    /** 
     * [公司开户银行联号] 公司开户银行联行号,显示前两位，其他用星号隐藏，每位1个星号<例子:12********> 
     *  
     * @param code 
     * @return 
     */  
    public static String cnapsCode(String code) {  
        if (StringUtils.isBlank(code)) {  
            return "";  
        }  
        return StringUtils.rightPad(StringUtils.left(code, 2), StringUtils.length(code), "*");  
    }  
  
    /*public static void main(String[] args) {
		System.out.println(idCardNum("150203199512020472"));
		System.out.println(idCardNum("511598106104173"));
		System.out.println(idCardNum("450698609"));
		System.out.println(idCardNum("450601198609245198345342342"));
	}*/
    
}  
