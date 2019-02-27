package com.mx.enums;

/**
 * @author 小米线儿
 * @time 2019/2/27 0027
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public enum ResponseCodeEnum {
    CODE_ERROR(999, "自定义错误提示"),
    CODE_97(97,"登入用户为空"),
    CODE_99(99,"没有权限"),
    CODE_100(100,"成功"),
    CODE_101(101,"系统异常"),
    CODE_102(102,"参数为空"),
    CODE_103(103,"您的帐号已被禁用，请联系管理员！"),
    CODE_104(104,"用户名或密码不正确！"),
    CODE_105(105,"接收参数解析异常!"),
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

    private ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseCodeEnum valueOf(Integer code){
        for(ResponseCodeEnum codeEnum : ResponseCodeEnum.values()){
            if(codeEnum.code.equals(code)){
                return codeEnum;
            }
        }
        return null;
    }

}
