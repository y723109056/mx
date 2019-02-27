package com.mx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * 页面的字符串转为日期类型，因为改用spring mvc所以，这个类可能会被废弃
 */
public class DateTypeConverter/* extends StrutsTypeConverter */{

    private static final Logger log = LoggerFactory.getLogger(DateTypeConverter.class);
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final DateFormat[] ACCEPT_DATE_FORMATS = {
            new SimpleDateFormat(DEFAULT_DATE_FORMAT),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
            new SimpleDateFormat("yyyy/MM/dd"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm")};

    /**  
     *   
     */
    public DateTypeConverter() {
    }

    /**
     * 这个类写的有问题
     * 
     * @see
     * org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util
     * .Map, String[], Class)
     */
    @SuppressWarnings("rawtypes")
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values[0] == null || values[0].trim().equals(""))
            return null;
        for (DateFormat format : ACCEPT_DATE_FORMATS) {
            try {
                return format.parse(values[0]);
            } catch (ParseException e) {
                continue;
            } catch (RuntimeException e) {
                continue;
            }
        }
        log.debug("can not format date string:" + values[0]);
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util
     * .Map, java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    public String convertToString(Map context, Object o) {
        if (o instanceof Date) {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            try {
                return format.format((Date) o);
            } catch (RuntimeException e) {
                return "";
            }
        }
        return "";
    }

}
