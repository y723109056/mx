package com.mx.util;

/**
 * @author 小米线儿
 * @time 2019/2/12 0012
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class SystemUtil {
    public static boolean isWindows() {
        boolean result = false;
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            result = true;
        }
        return result;
    }
}
