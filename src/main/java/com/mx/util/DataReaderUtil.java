package com.mx.util;


import com.mx.collection.DataRow;
import com.mx.reader.Reader;

/**
 * Created by Administrator on 2017/6/12.
 */
public class DataReaderUtil {


    /**
     *
     * @param data
     * @return
     */
    public static Object getValue(Object data,String columnName){
        if(data!=null){
            if(data instanceof DataRow){
                Object val = Reader.objectReader().getValue(data, columnName);
                return val;
            }else{
                String propertyName = getPropertyNameByColumnName(columnName);
                Object val = Reader.objectReader().getValue(data, propertyName);
                return val;
            }
        }else{
            return null;
        }
    }

    /**
     *
     * @param columnName
     * @return
     */
    private static String getPropertyNameByColumnName(String columnName){
        String[] fs = columnName.split("\\_");
        String str="";
        for(int i=0;i<fs.length;i++){
            if(i==0)str+=fs[i].toLowerCase();
            else str+=fs[i].substring(0,1).toUpperCase()+fs[i].substring(1).toLowerCase();
        }
        return str;
    }
}
