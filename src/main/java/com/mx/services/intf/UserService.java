package com.mx.services.intf;

//import com.mx.entity.User;
import com.mx.dao.UserMapper;
import com.mx.entity.User;
import com.mx.services.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 小米线儿
 * @time 2019/2/22 0022
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

@Service
public class UserService implements IUserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int deleteUserByUserId(Integer userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public User selectUserByUserId(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    @Override
    public boolean checkUser(User user) {
        return userMapper.selectUsersByCondition(user) != null ? false : true;
    }

    @Override
    public User doLogin(User user) {
        return userMapper.selectUsersByCondition(user);
    }

}
