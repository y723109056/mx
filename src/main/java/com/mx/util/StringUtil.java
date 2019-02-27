package com.mx.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;


/**
 * @author 小米线儿
 * @time 2019/2/12 0012
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

/**
 * 字符串操作封装
 */
public class StringUtil {

	/**
	 * 移动字符串开头的空格
	 * @param str
	 * @return
	 */
	public static String trimStart(String str) {
		return trim(str, null, -1);
	} 
	
	/**
	 * 移除字符串结尾的空格
	 * @param str
	 * @return
	 */
	public static String trimEnd(String str) {
		return trim(str, null, 1);
	}
	
	/**
	 * 判断字符串是空或空字符串
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str)
	{
		return str==null || str.equals("");
	}
	
	/**
	 * 判断字符串为非空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		return str!=null && str.length()>0;
	}
	
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		for(char ch : str.toCharArray()){
			if(!Character.isDigit(ch))return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串是否为日期
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str){
		return dateP.matcher(str).matches() || timeP.matcher(str).matches();
	}
	
	/**
	 * 字符串左边填补字符
	 * @param str
	 * @param totalLength 字符串长度
	 * @param c 填补的字符
	 * @return
	 */
	public static String padLeft(String str,int totalLength,char c)
	{
		String str1 = str;
		int bit=totalLength;
        int n = str1.length();
        if (n < bit)
        {
            int m = bit - n;
            for (int i = 0; i < m; i++)
            {
                str1 =  Convert.toString(c)+ str1;
            }
            return str1;
        }
        else return str;
	}
	
	/**
	 * 字符串右边填补字符
	 * @param str
	 * @param totalLength 字符串长度
	 * @param c 填补的字符
	 * @return
	 */
	public static String padRight(String str,int totalLength,char c)
	{
		String str1 = str;
		int bit=totalLength;
        int n = str1.length();
        if (n < bit)
        {
            int m = bit - n;
            for (int i = 0; i < m; i++)
            {
                str1 = str1+Convert.toString(c);
            }
            return str1;
        }
        else return str;
	}
	
	/**
	 * 构造一个字符串
	 * @param c
	 * @param count
	 * @return
	 */
	public static String getString(char c,int count)
	{
		char[] cs=new char[count];
		for(int i=0;i<count;i++)
		{
			cs[i]=c;
		}
		return String.copyValueOf(cs);
	}
	
	/**
	 * 移除字符串左右两边的字符
	 * @param str
	 * @return
	 */
	public static String trim(String str){
		if(str!=null && str!="")
		{
			return str.trim();
		}
		return str;
	}
	
	private static String trim(String str, String stripChars, int mode)
	{
		if (str == null) {
			return null;
		}

		int length = str.length();
		int start = 0;
		int end = length;

		// 扫描字符串头部
		if (mode <= 0) {
			if (stripChars == null) {
				while ((start < end)
				&& (Character.isWhitespace(str.charAt(start)))) {
				start++;
				}
			}
			else if (stripChars.length() == 0) {
				return str;
			} 
			else {
				while ((start < end)
				&& (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
				}
			}
		}

		// 扫描字符串尾部
		if (mode >= 0) {
			if (stripChars == null)
			{
				while ((start < end) && (Character.isWhitespace(str.charAt(end - 1)))) 
				{
					end--;
				}
			}
			else if (stripChars.length() == 0) 
			{
				return str;
			}else {
				while ((start < end) && (stripChars.indexOf(str.charAt(end - 1)) != -1))
				{
					end--;
				}
			}
		}

		if ((start > 0) || (end < length)) {
			return str.substring(start, end);
		}

		return str;
	}
	
	public static String format(String str,Object ... args)
	{
		return format(str, Pattern.compile("\\{(\\d+)\\}"),args);
	}

	/**
	 * 字符串参数格式化
	 * @param str
	 * @param args
	 * @return
	 */
	public static String format(final String str,Pattern pattern,Object ... args){
		// 这里用于验证数据有效性
		if (str == null || "".equals(str))
			return "";
		if (args.length == 0) {
			return str;
		}

		String result = str;

		// 这里的作用是只匹配{}里面是数字的子字符串
		Pattern p = pattern;
		java.util.regex.Matcher m = p.matcher(str);

		while (m.find()) {
			// 获取{}里面的数字作为匹配组的下标取值
			int index = Integer.parseInt(m.group(1));
			// 这里得考虑数组越界问题，{1000}也能取到值么？？
			if (index < args.length && args[index]!=null) {
				// 替换，以{}数字为下标，在参数数组中取值
				result = result.replace(m.group(), args[index].toString());
			} else {
				result = result.replace(m.group(), "");
			}
		}
		return result;
	}
	
	/**
	 * 字符串转小写
	 * @param str
	 * @param start
	 * @param len
	 * @return
	 */
	public static String toLower(String str,int start, int len)
    {
        if (start+len > str.length()) throw new RuntimeException(format("字符串转小写时，超出长度 Str:{0} Len{1} Start{2}",str,start,len));

        String lStr = str.substring(start, len+start);
        String sStr = str.substring(0, start);
        String eStr = str.substring(start + len, str.length());
        return sStr + lStr.toLowerCase() + eStr;
    }

	/**
	 * 字符串转大写
	 * @param str
	 * @param start
	 * @param len
	 * @return
	 */
    public static String toUpper(String str, int start, int len)
    {
        if (start + len > str.length()) throw new RuntimeException(format("字符串转大写时，超出长度 Str:{0} Len{1} Start{2}", str, start, len));

        String uStr = str.substring(start, len);
        String sStr = str.substring(0, start);
        String eStr = str.substring(start + len, str.length() - start - len);

        return sStr + uStr.toUpperCase() + eStr;
    }
    
    /**
	 * 过滤SQL字符。
	 * 
	 * @param str要过滤SQL字符的字符串
	 *            。
	 * @return 已过滤掉SQL字符的字符串。
	 */
	public static String ReplaceSQLChar(String Str) {
		if (StringUtil.isNullOrEmpty(Str))
			return "";
		Str = StringEscapeUtils.escapeSql(Str);
		return Str;
	}
	
	static class Convert {
		public static String toString(Object obj)
		{
			if(obj==null)return null;
			else if(obj instanceof String)return (String)obj;
			else
			{
				return obj.toString();
			}
		}
		
		public static String toString(char c)
		{
			return String.valueOf(c);
		}
		
		public static String toString(int i)
		{
			return String.valueOf(i);
		}
	}
	
	/**
	 * 去除字符串中头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
	 * @param s
	 * @return
	 */
	public static String trimEx(String s){
		String result = "";
		if(null!=s && !"".equals(s)){
			result = s.replaceAll("^[　*| *|&nbsp;*|//s*]*", "").replaceAll("[　*| *|&nbsp;*|//s*]*$", "");
		}
		return result;
	}
	
	private static final Pattern dateP = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\-\\s]?((((0?" +"[13578])|(1[02]))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))" +"|(((0?[469])|(11))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|" +"(0?2[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12" +"35679])|([13579][01345789]))[\\-\\-\\s]?((((0?[13578])|(1[02]))" +"[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))" +"[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\-\\s]?((0?[" +"1-9])|(1[0-9])|(2[0-8]))))))"); 
	private static final Pattern timeP = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");

	/**
	 * 例如：HELLO_WORLD->HelloWorld
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static  String camelName(String name) {
		StringBuilder result =  new  StringBuilder();
		if(!isNotEmpty(name)) {
			// 没必要转换
			return "";
		}else if(!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0,1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for(String camel :  camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if(camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if(result.length() ==  0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			}else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0,1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}
}
