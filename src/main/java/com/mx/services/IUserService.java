package com.mx.services;

import com.mx.entity.User;

/**
 * @author 小米线儿
 * @time 2019/2/22 0022
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

public interface IUserService {

    public int addUser(User user);

    public int updateUser(User user);

    public int deleteUserByUserId(Integer userId);

    public User selectUserByUserId(Integer userId);

    public boolean checkUser(User user);

    public User doLogin(User user);

//    public List<>
}
