package com.mx.sql.command;

import java.lang.annotation.*;

/**
 * 自定义注解，事务
 * Created by zhusw on 2017/12/19.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SqlTransaction {

}
