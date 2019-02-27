package com.mx.enums;

import org.apache.commons.lang.CharEncoding;

/**
 * @author 小米线儿
 * @time 2019/2/25 0025
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public enum Charset {

    UTF8(CharEncoding.UTF_8),
    GBK("GBK");

    public String name;

    Charset(String name) {
        this.name = name;
    }
}
