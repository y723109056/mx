package com.mx.mobile.response;

public enum CodeEnum {

	CODE_ERROR(999, "自定义错误提示"),
	CODE_97(97,"登入用户为空"),
	CODE_99(99,"没有权限"),
	CODE_100(100,"成功"),
	CODE_101(101,"系统异常"),
	CODE_102(102,"参数为空"),
	CODE_103(103,"账户不存在"),
	CODE_104(104,"密码不正确"),
	CODE_105(105,"json解析出错"),
	CODE_106(106,"您的帐号已被禁用，请联系管理员！"),
	CODE_107(107,"用户名或密码不正确，请重新输入！"),
	CODE_108(108,"该客户已添加过对当前产品的意向！"),
	CODE_109(109,"证件号码已存在！"),
	CODE_110(110,"json数据格式不正确")
	;

	//响应码
	private final Integer  code;
     
	//响应消息
    private final String msg;
    
    
	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	private CodeEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static CodeEnum valueOf(Integer code){
		for(CodeEnum codeEnum : CodeEnum.values()){
			if(codeEnum.code.equals(code)){
				return codeEnum;
			}
		}
		return null;
	}
	
}
