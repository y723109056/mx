package com.mx.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;


public class ParseXmlUtil {
    /**
     * 从Map到XML
     * @param headMap
     * @param bodyMap
     * @param row
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static String maptoXml(Map headMap, Map bodyMap, List<Map<String, Object>> row) {
        return maptoXml(headMap,bodyMap,row,"gbk");
    }
    
    @SuppressWarnings("rawtypes")
	public static String maptoXml(Map headMap, Map bodyMap, List<Map<String, Object>> row,String encoding){
        Document document = DocumentHelper.createDocument();
        Element nodeElement = document.addElement("message");
        Element hElement = nodeElement.addElement("head");
        for (Object obj : headMap.keySet()) {
            Element keyElement = hElement.addElement(String.valueOf(obj));
            keyElement.setText(String.valueOf(headMap.get(obj)));
        }
        if (bodyMap != null){
            Element bElement = nodeElement.addElement("body");
            for (Object obj : bodyMap.keySet()) {
                Element keyElement = bElement.addElement(String.valueOf(obj));

                keyElement.setText(String.valueOf(bodyMap.get(obj)==null?"":bodyMap.get(obj)));
            }
            if (row != null) {
                for (int i = 0; i < row.size(); i++) {
                    Element rElement = bElement.addElement("row");
                    for (Object obj : row.get(i).keySet()) {
                        Element keyElement = rElement.addElement(String.valueOf(obj));
                        keyElement.setText(String.valueOf(row.get(i).get(obj)==null?"":row.get(i).get(obj)));
                    }
                }
            }
        }
        return doc2String(document,encoding);
    }

    /**
     * @param document
     * @return
     */
    public static String doc2String(Document document,String encoding) {
        String s = "";
        try {
            // 使用输出流来进行转化
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用UTF-8编码
            OutputFormat format = new OutputFormat("   ", true, encoding);
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString(encoding);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }
    /**
     * @param document
     * @return
     */
    public static String doc2String(Document document) {
        return doc2String(document,"gbk");
    }

    /**
     * 数字不足位数左补0
     *
     * @param str
     * @param strLength
     * @return
     */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);//左补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }
}
