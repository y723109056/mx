
package com.mx.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class ImportUtil {

	/**
	 * 判断key是否在数组存在
	 * 
	 * @param InCharge
	 * @param key
	 * @return
	 */
	public static Boolean selectArr(Integer[] InCharge, Integer key)
	{
		for (int i = 0; i < InCharge.length; i++)
		{
			if (InCharge[i].equals(key))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断日期是否大于今天
	 * 
	 * @return
	 */
	public static boolean isDateLargeToday(Date date)
	{
		try
		{
			Date today = Calendar.getInstance().getTime();
			if (date.before(today))
				return false;
			else
				return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * 判断性别是否是男和女
	 * 
	 * @param sex
	 * @return
	 */
	public static boolean isSex(String sex)
	{
		if (sex.equals(""))
		{
			return false;
		} else
		{
			if (sex.equals("男") || sex.equals("女"))
				return true;
			else
				return false;
		}
	}

	/**
	 * 去除重复项
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String delString(String str)
	{
		String[] s = str.split(",");
		List list = Arrays.asList(s);
		Set set = new HashSet(list);
		String[] rid = (String[]) set.toArray(new String[0]);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rid.length; i++)
		{
			sb.append(rid[i] + ",");
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 取出单元格里面的值
	 * 
	 * @param hssfCell
	 * @return
	 */
	@SuppressWarnings({ "static-access" })
	public static String getValue(HSSFCell hssfCell)
	{
		DecimalFormat df = new DecimalFormat("#");
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN)
		{
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC)
		{
			return String.valueOf(df.format(hssfCell.getNumericCellValue()));
		} else
		{
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	/**
	 * 取出单元格里面的值
	 * 
	 * @param hssfCell
	 * @return
	 */
	@SuppressWarnings({ "static-access" })
	public static String getValue(Cell hssfCell)
	{
		DecimalFormat df = new DecimalFormat("#");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN)
		{
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC)
		{
			// 判断是否日期格式
			if (HSSFDateUtil.isCellDateFormatted(hssfCell))
			{
				return String.valueOf(sf.format(HSSFDateUtil
                        .getJavaDate(hssfCell.getNumericCellValue())));
			}
			// 判断是否自定义的日期格式
			if (hssfCell.getCellStyle().getDataFormat() == 58)
			{
				return String.valueOf(sf.format(HSSFDateUtil
                        .getJavaDate(hssfCell.getNumericCellValue())));
			}
			// 纯数字直接输出
			return String.valueOf(df.format(hssfCell.getNumericCellValue()));
		} else
		{
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	/**
	 * 根据生日计算年龄
	 * 
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static int getAge(Date birthDay)
	{
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay))
		{
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth)
		{
			if (monthNow == monthBirth)
			{
				if (dayOfMonthNow < dayOfMonthBirth)
				{
					age--;
				} else
				{
				}
			} else
			{
				age--;
			}
		} else
		{
		}
		return age;
	}

	/**
	 * 判断是否是float型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str)
	{
		try
		{
			new Float(str);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * 判断字符在字符串中是否出现
	 * 
	 * @param str
	 * @param s
	 * @return
	 */
	public static boolean DelRepetStr(String str, String s)
	{
		String[] list = str.split(",");
		for (int i = 0; i < list.length; i++)
		{
			if (s.equals(list[i]))
				return true;
			if (s.equals("," + list[i]))
				return true;
		}
		return false;
	}

	/**
	 * 判断是否是空行
	 * 
	 * @param row
	 * @return
	 */
	public static boolean isBlankRow(HSSFRow row)
	{
		if (row == null)
			return true;
		boolean result = true;
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
		{
			HSSFCell cell = row.getCell(i);
			String value = "";
			if (cell != null)
			{
				switch (cell.getCellType())
				{
				case Cell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					value = String.valueOf((int) cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					value = String.valueOf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					value = String.valueOf(cell.getCellFormula());
					break;
				// case Cell.CELL_TYPE_BLANK:
				// break;
				default:
					break;
				}

				if (!value.trim().equals(""))
				{
					result = false;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 判断是否是空行
	 * 
	 * @param row
	 * @return
	 */
	public static boolean isBlankRow(Row row)
	{
		if (row == null)
			return true;
		boolean result = true;
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
		{
			Cell cell = row.getCell(i);
			String value = "";
			if (cell != null)
			{
				switch (cell.getCellType())
				{
				case Cell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					value = String.valueOf((int) cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					value = String.valueOf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					value = String.valueOf(cell.getCellFormula());
					break;
				// case Cell.CELL_TYPE_BLANK:
				// break;
				default:
					break;
				}

				if (!value.trim().equals(""))
				{
					result = false;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 判断是手机还是固话
	 * 
	 * @param str
	 * @return
	 */
	public static Integer isPhoneOrMobilePhone(String str)
	{
		str = str.trim();
		if (isMobileNO(str))
		{
			return 1;
		} else if (isPhoneNO(str))
		{
			return 2;
		} else
			return 3;
	}

	/**
	 * 判断是否是手机
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles)
	{
		// Pattern p =
		// Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		// Matcher m = p.matcher(mobiles);
		// return m.matches();
		if (isNumeric(mobiles))
		{
			if (mobiles.substring(0, 1).equals("1"))
			{
				if (mobiles.length() == 11)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否是固话
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhoneNO(String phone)
	{
		// Pattern p =
		// Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		// Matcher m = p.matcher(phone);
		// return m.matches();
		phone = phone.replaceAll("-", "").replaceAll("－", "");
		if (isNumeric(phone))
		{
			if (phone.length() <= 20)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str)
	{
		for (int i = str.length(); --i >= 0;)
		{
			if (!Character.isDigit(str.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否是double型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str)
	{
		try
		{
			new Double(str);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * 判断字符串是否为邮箱格式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email)
	{
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseAllStringToDate(String date)
	{
		String dateStr = date.trim();
		if (dateStr == null)
			return null;
		Date result = null;
		String parse = dateStr;
		if (dateStr.equals(""))
		{
			return null;
		}
		parse = parse.replaceFirst("^[0-9]{4}[0-9]{2}[0-9]{2}", "yyyyMMdd");
		parse = parse.replaceFirst("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}",
				"yyyy-MM-dd");
		parse = parse.replaceFirst("^[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}",
				"yyyy/MM/dd");
		parse = parse.replaceFirst("^[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日",
				"yyyy年MM月dd日");
		parse = parse.replaceFirst("^[0-9]{1,2}-[0-9]{1,2}-[0-9]{4}",
				"MM-dd-yyyy");
		parse = parse.replaceFirst("^[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}",
				"MM/dd/yyyy");
		parse = parse.replaceFirst("^[0-9]{1,2}月[0-9]{1,2}日[0-9]{4}年",
				"MM月dd日yyyy年");
		Integer year = 0, month = 0, day = 0;
		Integer monthIndex, dayIndex, yearIndex;
		if (parse.equalsIgnoreCase("yyyyMMdd"))
		{
			year = Integer.parseInt(dateStr.substring(0, 4));
			month = Integer.parseInt(dateStr.substring(4, 6));
			day = Integer.parseInt(dateStr.substring(6, 8));
		} else if (parse.equalsIgnoreCase("yyyy-MM-dd"))
		{
			monthIndex = dateStr.indexOf("-");
			dayIndex = dateStr.indexOf("-", monthIndex + 1);
			if (monthIndex != -1 && dayIndex != -1)
			{
				year = Integer.parseInt(dateStr.substring(0, monthIndex));
				month = Integer.parseInt(dateStr.substring(monthIndex + 1,
                        dayIndex));
				day = Integer.parseInt(dateStr.substring(dayIndex + 1,
                        dateStr.length()));
			}
		} else if (parse.equalsIgnoreCase("yyyy/MM/dd"))
		{
			monthIndex = dateStr.indexOf("/");
			dayIndex = dateStr.indexOf("/", monthIndex + 1);
			if (monthIndex != -1 && dayIndex != -1)
			{
				year = Integer.parseInt(dateStr.substring(0, monthIndex));
				month = Integer.parseInt(dateStr.substring(monthIndex + 1,
                        dayIndex));
				day = Integer.parseInt(dateStr.substring(dayIndex + 1,
                        dateStr.length()));
			}
		} else if (parse.equalsIgnoreCase("yyyy年MM月dd日"))
		{
			monthIndex = dateStr.indexOf("年");
			dayIndex = dateStr.indexOf("月", monthIndex + 1);
			if (monthIndex != -1 && dayIndex != -1)
			{
				year = Integer.parseInt(dateStr.substring(0, monthIndex));
				month = Integer.parseInt(dateStr.substring(monthIndex + 1,
                        dayIndex));
				day = Integer.parseInt(dateStr.substring(dayIndex + 1,
                        dateStr.length() - 1));
			}
		} else if (parse.equalsIgnoreCase("MM-dd-yyyy"))
		{
			dayIndex = dateStr.indexOf("-");
			yearIndex = dateStr.indexOf("-", dayIndex + 1);
			if (yearIndex != -1 && dayIndex != -1)
			{
				month = Integer.parseInt(dateStr.substring(0, dayIndex));
				day = Integer.parseInt(dateStr.substring(dayIndex + 1,
                        yearIndex));
				year = Integer.parseInt(dateStr.substring(yearIndex + 1,
                        dateStr.length()));
			}
		} else if (parse.equalsIgnoreCase("MM/dd/yyyy"))
		{
			dayIndex = dateStr.indexOf("/");
			yearIndex = dateStr.indexOf("/", dayIndex + 1);
			if (yearIndex != -1 && dayIndex != -1)
			{
				month = Integer.parseInt(dateStr.substring(0, dayIndex));
				day = Integer.parseInt(dateStr.substring(dayIndex + 1,
                        yearIndex));
				year = Integer.parseInt(dateStr.substring(yearIndex + 1,
                        dateStr.length()));
			}
		} else if (parse.equalsIgnoreCase("MM月dd日yyyy年"))
		{
			dayIndex = dateStr.indexOf("月");
			yearIndex = dateStr.indexOf("日", dayIndex + 1);
			if (yearIndex != -1 && dayIndex != -1)
			{
				month = Integer.parseInt(dateStr.substring(0, dayIndex));
				day = Integer.parseInt(dateStr.substring(dayIndex + 1,
                        yearIndex));
				year = Integer.parseInt(dateStr.substring(yearIndex + 1,
                        dateStr.length() - 1));
			}
		} else
		{
			return null;
		}

		if (year.intValue() <= 0 || month.intValue() > 12
				|| month.intValue() < 1 || day.intValue() < 1
				|| day.intValue() > 31)
		{
			return null;
		}
		try
		{
			SimpleDateFormat format = new SimpleDateFormat(parse);
			result = format.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(result);
			if (cal.get(Calendar.YEAR) < 0)
				return null;
			if (day != null && cal.get(Calendar.DATE) != day.intValue())
				return null;
		} catch (Exception e)
		{
			return null;
		}
		return result;
	}

	/**
	 * 计算导入批次
	 * 
	 * @param sumCount
	 *            总记录数
	 * @return
	 */
	public static Integer getBatchSize(Integer sumCount)
	{
		double defaultMin = 10.0;
		double defaultMax = 1000.0;
		if (sumCount < defaultMin)
			return 1;
		else if (Math.ceil((sumCount / defaultMin)) > defaultMax)
			return (int) defaultMax;
		else
			return (int) Math.floor(sumCount / defaultMin);

	}

	/**
	 * 总运行次数
	 * 
	 * @param sumCount
	 *            总记录数
	 * @param batchSzie
	 *            批次大小
	 * @return
	 */
	public static Integer getBathchCount(Integer sumCount, Integer batchSzie)
	{
		return (int) Math.ceil(sumCount / (double) batchSzie);
	}

	@SuppressWarnings({ "static-access", "unused" })
	private static String getNumbericValue(Cell cell)
	{
		DecimalFormat df = new DecimalFormat("#");
		DecimalFormat dfDu = new DecimalFormat("#.#");
		if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN)
		{
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC)
		{
			if (((int) cell.getNumericCellValue()) != cell
					.getNumericCellValue())
			{
				return String.valueOf(dfDu.format(cell.getNumericCellValue()));
			}
			return String.valueOf(df.format(cell.getNumericCellValue()));
		} else
		{
			return String.valueOf(cell.getStringCellValue());
		}

	}



	/**
	 * 判断是否正整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDoubleNumber(String str)
	{
		try
		{
			if (isDouble(str))
			{
				return !(new Double(str) > 0 && new Double(str) % 1 == 0);
			} else
			{
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 根据身份证号生成出生日期
	 * 
	 * @param cardID 15位或18位的身份证号
	 * @return 出生日期
	 * 
	 */
	public static Date getBirthday(String cardID)
	{
		Date returnDate = null;
		StringBuffer tempStr = null;
		if (cardID != null && cardID.trim().length() > 0)
		{
			if (cardID.trim().length() == 15)
			{
				tempStr = new StringBuffer(cardID.substring(6, 12));
				tempStr.insert(4, '-');
				tempStr.insert(2, '-');
				tempStr.insert(0, "19");
			} else if (cardID.trim().length() == 18)
			{
				tempStr = new StringBuffer(cardID.substring(6, 14));
				tempStr.insert(6, '-');
				tempStr.insert(4, '-');
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (tempStr != null && tempStr.toString().trim().length() > 0)
		{
			try
			{
				returnDate = df.parse(tempStr.toString());
			} catch (Exception e)
			{
				System.out.println("输入的身份证错误，不能转换为相应的出生日期");
			}
		}
		return returnDate;
	}

	/**
	 * 转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseStringToDateBeforeToday(String date)
	{
		Date result = parseAllStringToDate(date);
		if (isDateLargeToday(result))
		{
			return null;
		}
		return result;
	}

	/**
	 * 判断长度
	 * 
	 * @param value
	 * @param length
	 * @return
	 */
	public static boolean checkLength(String value, int length)
	{
		boolean flag = false;
		if (value.length() > length)
		{
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断是Integer型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str)
	{
		try
		{
			new Integer(str);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

}
