package com.mx.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PYUtil {

	/**
	 * 中文转拼音
	 * 
	 * @param str
	 * @return
	 */
	public static String getPingYing(String str) {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			String[] pinyin;
			try {
				pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				throw new RuntimeException(e.getMessage());
			}
			if (pinyin.length > 0) {
				if (i > 0)
					sb.append("_");
				String regEx = "[^A-Z0-9_]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(pinyin[0]);
				sb.append(m.replaceAll("").trim());
			}
		}
		return sb.toString();
	}

	public static final boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// GENERAL_PUNCTUATION 判断中文的“号
	// CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
	// HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
	private static final boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String str1 = PYUtil.getPingYing("缴费类型");
		System.out.println(str1);

		String str2 = PYUtil.getPingYing("主附险属性");
		System.out.println(str2);

		System.out.println(PYUtil.isChinese("哈哈"));

	}

}
