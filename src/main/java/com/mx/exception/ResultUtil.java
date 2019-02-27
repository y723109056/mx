package com.mx.exception;

/**
 * @author 小米线儿
 * @time 2019/2/13 0013
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class ResultUtil {

    public static <T>Result success(T data){
        return getResult(0,"success",data);
    }

    public static Result success(){
        return success(null);
    }


    public static <T> Result<T> fail(ExceptionEnum e,T data){
        return getResult(e.getStatus(),e.getMsg(),data);
    }

    public static Result fail(ExceptionEnum e){
        return fail(e,null);
    }

    public static Result fail(Integer status,String msg){
        return getResult(status,msg,null);
    }


    public static Result error(){
        return fail(ExceptionEnum.SYSTEM_ERROR,null);
    }

    public static <T> Result<T> getResult(Integer status,String msg,T data){
        return new Result<>(status,msg,data);
    }


}
