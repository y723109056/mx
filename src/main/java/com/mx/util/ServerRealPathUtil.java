package com.mx.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 小米线儿
 * @time 2019/2/12 0012
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class ServerRealPathUtil {
    private static Log logger = LogFactory.getLog(ServerRealPathUtil.class);

    public static String getServerRealPath() {
        String result = "";
        String classPath = "";
        try {
            classPath = java.net.URLDecoder.decode(ServerRealPathUtil.class.getResource("")
                .toString(), "utf-8");
        } catch (Exception e) {
            logger.error("getServerRealPath ...", e);
        }
        if (SystemUtil.isWindows()) {
            if (classPath.length() >= 10) {
                result = classPath.substring(10, classPath.lastIndexOf("WEB-INF"));
            }
        } else {
            int first = classPath.indexOf("/");
            if (first > 0) {
                result = classPath.substring(first, classPath.lastIndexOf("WEB-INF"));
            }
        }

        return result;
    }
}
