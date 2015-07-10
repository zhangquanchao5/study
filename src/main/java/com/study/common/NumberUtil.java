package com.study.common;



import org.apache.log4j.Level;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * The type Number util.
 * @preserve public
 */
public final class NumberUtil {
	private static final String STRING_FLOAT_ZERO = "0.00";

	private NumberUtil() {
	}

	/**
	 * 格式化尾数   不足补0
	 * @param num the num
	 * @param n the n
	 * @return  string
     */
	public static String pad(String num,int n){
		String value = num;
		int len = num.length();
		while(len<n){
			value = "0" + value;
			len++;
		}
		return value;
	}

	/**
	 * 格式化数字类型为：BigDecimal
	 * @param obj the obj
	 * @return the big decimal
     */
	public static BigDecimal parseBigDecimal(Object obj){
		return new BigDecimal(parseInt(obj));
	}

	/**
	 * 两个double类型相加
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2
	 * @return the double
     */
	public static double addDouble(Object obj1,Object obj2){
		return (new BigDecimal(StringUtil.convertToString(parseDouble(obj1))))
				.add(new BigDecimal(StringUtil.convertToString(parseDouble(obj2))))
				.doubleValue();
	}

	/**
	 * 两个double类型相减
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2
	 * @return the double
     */
	public static double minusDouble(Object obj1,Object obj2){
		return (new BigDecimal(StringUtil.convertToString(parseDouble(obj1))))
				.subtract(new BigDecimal(StringUtil.convertToString(parseDouble(obj2))))
				.doubleValue();
	}

	/**
	 * 两个double类型相乘
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2
	 * @return the double
     */
	public static double multiplyDouble(Object obj1,Object obj2){
		return (new BigDecimal(StringUtil.convertToString(parseDouble(obj1))))
				.multiply(new BigDecimal(StringUtil.convertToString(parseDouble(obj2))))
				.doubleValue();
	}

	/**
	 * 两个double类型相除
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2
	 * @return the double
     */
	public static double divideDouble(Object obj1,Object obj2){
		return (new BigDecimal(StringUtil.convertToString(parseDouble(obj1))))
				.divide(new BigDecimal(StringUtil.convertToString(parseDouble(obj2))))
				.doubleValue();
	}

	/**
	 * 格式化数字类型为：Int
	 * @param obj the obj
	 * @return the int
     */
	public static int parseInt(Object obj){
		int value = 0;
		try{
			if(obj != null && !StringUtil.isEmpty(obj.toString().trim())){
				value = Integer.parseInt(obj.toString().trim());
			}
		}catch(Exception ex){
			StudyLogger.recSysLog(Level.ERROR, ex.getMessage(), ex);
			value = 0;
		}
		return value;
	}

	/**
	 * 格式化数字类型为：long
	 * @param obj the obj
	 * @return the long
     */
	public static long parseLong(Object obj){
		long value = 0L;
		try{
			if(obj != null && !StringUtil.isEmpty(obj.toString().trim())) {
				value = Long.parseLong(obj.toString().trim());
			}
		}catch(Exception ex){
			StudyLogger.recSysLog(Level.ERROR, ex.getMessage(), ex);
			value = 0L;
		}
		return value;
	}

	/**
	 * 格式化数字类型为：double
	 * @param obj the obj
	 * @return the double
     */
	public static double parseDouble(Object obj){
		double value = 0l;
		try{
			if(obj != null && !StringUtil.isEmpty(obj.toString().trim())){
				value = Double.parseDouble(obj.toString().trim());
			}
		}catch(Exception ex){
			StudyLogger.recSysLog(Level.ERROR, ex.getMessage(), ex);
			value = 0l;
		}
		return value;
	}

	/**
	 *  格式化金额 为 0.00
	 * @param p the p
	 * @return the string
     */
	public static String formatPrice(Object p){
		if(p==null){
			return new DecimalFormat(STRING_FLOAT_ZERO).format(0l);
		}
		double d = parseDouble(p);
		if(d==0l){
			return STRING_FLOAT_ZERO;
		}
		return new DecimalFormat(STRING_FLOAT_ZERO).format(d);
	}

	/**
	 * Format price for double.
	 *
	 * @param p the p
	 * @return the double
     */
	public static double formatPriceForDouble(Object p){
		return parseDouble(formatPrice(p));
	}

	/**
	 * 把分的金额格式化为元
	 * @param obj the obj
	 * @return  string
     */
	public static String formatToMoney(Object obj) {
		DecimalFormat df = new DecimalFormat(STRING_FLOAT_ZERO);
	    try {
	    	if(obj==null || "".equals(obj)){
	    		return STRING_FLOAT_ZERO;
	    	}else{
	    		return df.format(divideDouble(obj,100));
	    	}
	    } catch (Exception e) {
	        return STRING_FLOAT_ZERO;
	    }
	}

	/**
	 * 按格式把分的金额格式化为元
	 * @param obj the obj
	 * @param format the format
	 * @return  string
     */
	public static String formatToMoney(Object obj,String format) {
		DecimalFormat df = new DecimalFormat(format);
	    try {
	    	if(obj==null || "".equals(obj)){
	    		return STRING_FLOAT_ZERO;
	    	}else{
	    		return df.format(divideDouble(obj,100));
	    	}
	    } catch (Exception e) {
	        return STRING_FLOAT_ZERO;
	    }
	}

	/**
	 * 把元的金额格式化为分
	 * @param obj the obj
	 * @return  string
     */
	public static String formatMoneyToCent(Object obj){
		try{
			if(obj==null || "".equals(obj)){
				return "0";
			}else{
				
				return new BigDecimal(obj.toString()).multiply(new BigDecimal(100)).toBigInteger().toString();
			}
		}catch(Exception e){
			StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
			return "0";
		}
	}

	/**
	 * 按位数格式化数字,不足前面补0
	 * @param obj the obj
	 * @param digit the digit
	 * @return  string
     */
	public static String formatNumberToDigit(Object obj,int digit) {
		try{
			//String format = "";
            StringBuffer buf = new StringBuffer("");
			if(digit<1){
				//format = "0";
                buf.append("0");
			}
			for(int i=0;i<digit;i++){
                buf.append("0");
				//format =format+ "0";
			}
			DecimalFormat nf = new DecimalFormat(buf.toString());
			if(obj==null || "".equals(obj)){
				return nf.format(0);
			}else{
				return nf.format(parseLong(obj));
			}
		}catch(Exception e){
			StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
			return "0";
		}
	}

	/**
	 * 按格式格式化金额
	 * @param obj the obj
	 * @param format the format
	 * @return  string
     */
	public static String formatMoney(Object obj,String format) {
		DecimalFormat nf = new DecimalFormat(format);
		try {
			if(obj==null || "".equals(obj)){
				return STRING_FLOAT_ZERO;
			}else{
				return nf.format(new BigDecimal(obj.toString()).doubleValue());
			}
		} catch (Exception e) {
			return STRING_FLOAT_ZERO;
		}
	}
}