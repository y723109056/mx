package com.mx.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author 小米线儿
 * @time 2019/2/12 0012
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class FileCodeUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static synchronized String createFileCode() {
        String result = sdf.format(Calendar.getInstance().getTime());
        return result;
    }
}
