package com.mx.services;

import com.mx.permission.ILoginInfo;

/**
 * @author 小米线儿
 * @time 2019/2/26 0026
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public interface ILoginService {
    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    String login(String account,String password);


    /**
     * 退出
     * @param loginInfo
     * @return
     */
    String logout(ILoginInfo loginInfo);


}
