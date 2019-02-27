package com.mx.auth;

/**
 * @author 小米线儿
 * @time 2019/2/12 0012
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public enum AuthorityType {

    // 登录和权限都验证 默认
    Validate,

    // 不验证
    NoValidate,

    // 不验证权限
    NoAuthority;
}
