package com.mx.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import com.mx.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KeyWorkFilter {
    protected static String[] filterStrs = null;

    public static String[] getFilterStrings() {
        if (filterStrs == null) {
            Properties prop = new Properties();
            InputStream inputStream = null;
            try {
                inputStream = KeyWorkFilter.class.getClassLoader().getResourceAsStream("unsafe.properties");
                prop.load(inputStream);
                String strs = prop.getProperty("filter_key_words");
                if (StringUtil.isNotEmpty(strs)) {
                    filterStrs = strs.split("\\|");
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    if (null != inputStream) {
                        inputStream.close();
                    }
                } catch (IOException er) {

                }
            }

            if (filterStrs == null) {
                filterStrs = ("grant|exec|execute|create|drop|alert"
                        + "|insert|update|delete|script").split("\\|");
            }
        }
        return filterStrs;
    }

    public static boolean hasFilterWords(String[] words) {
        if (words != null && words.length > 0) {
            for (String word : words) {
                if (validate(word)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void getAllParms(JSONObject obj, List<String> list) {
        Iterator iterator = obj.keys();
        while (iterator.hasNext()) {
            //判断是否是数组
            String str = obj.get(iterator.next()).toString();
            if (str.startsWith("{")) {
                //是JSONObject
                try {
                    JSONObject jsonObject = JSONObject.fromObject(str);
                    getAllParms(jsonObject, list);
                } catch (Exception e) {
                }
            } else if (str.startsWith("[")) {
                try {
                    //是JSONArray
                    JSONArray jsonArray = JSONArray.fromObject(str);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        getAllParms(jsonArray.getJSONObject(i), list);
                    }
                } catch (Exception e) {
                }
            } else {
                list.add(str);
            }
        }
    }

    private static boolean validate(String str) {
        //判断str是否是一个json 格式
        List<String> list = new ArrayList<String>();
        //判断满足json条件不 包含{ }
        if (str.contains("{") && str.contains("}")) {
            if (str.startsWith("{")) {
                //是JSONObject
                try {
                    JSONObject jsonObject = JSONObject.fromObject(str);
                    getAllParms(jsonObject, list);
                } catch (Exception e) {
                }
            } else if (str.startsWith("[")) {
                //是JSONArray
                try {
                    JSONArray jsonArray = JSONArray.fromObject(str);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        getAllParms(jsonArray.getJSONObject(i), list);
                    }
                } catch (Exception e) {
                }
            } else {
                list.add(str);
            }
        } else {
            list.add(str);
        }

        for (String str1 : list) {
            if (StringUtil.isNotEmpty(str1)) {
                str1 = str1.toLowerCase();//统一转为小写
                String[] badStrs = getFilterStrings();
                for (int i = 0; i < badStrs.length; i++) {
                    if (str1.contains(badStrs[i])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
