package com.study.common;

import org.apache.log4j.Level;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type String util.
 * @preserve public
 */
public final class StringUtil {

	private StringUtil(){}

	private static final String PROTOCOL_SUFFIX = "://";

	/**
	 * 取得下一个编号
	 * @param start 编号的开头 活动start="A"  主题start="T"
	 * @param maxNo 数据库中当天最后的编号
	 * @return string string
     */
	public static String getNextNo(String start,String maxNo){
		String no = "";
		if(StringUtil.isEmpty(maxNo)){
			no = start + DateUtil.getCrruentTime("yyyyMMdd") + "-001";
		}else{
			int n = NumberUtil.parseInt(maxNo.split("-")[1]);
			n = n + 1;
			String p = NumberUtil.pad(StringUtil.convertToString(n), 3);
			no = maxNo.split("-")[0] + "-" + p;
		}
		return no;
	}

	/**
	 * Random aBC.
	 *
	 * @param size the size
	 * @return the string
     */
	public static String randomABC(int size){

        StringBuffer buf = new StringBuffer();
        Random r = new Random() ;
		for (int i=0;i<size;i++){
			   char c='a';
			   c=(char)(c+r.nextInt(26));
               buf.append(c);
			  // str=str+c;
		}
		return buf.toString();
	}

	/**
	 * format string
	 * @param obj the obj
	 * @return the string
     */
	public static String formatStringTrim(Object obj){
		if(obj==null){
			return "";
		}
		String temp = obj.toString().trim();
		if("".equals(temp) || "null".equals(temp)){
			return "";
		}
		return temp;
	}

	/**
	 * format string
	 * @param obj the obj
	 * @return the string
     */
	public static String convertToString(Object obj){
		if(obj==null){
			return "";
		}
		return obj.toString().trim();
	}

	/**
	 * Format string not trim.
	 *
	 * @param obj the obj
	 * @return the string
     */
	public static String formatStringNotTrim(Object obj){
		if(obj==null){
			return "";
		}
		String temp = obj.toString();
		if("".equals(temp) || "null".equals(temp)){
			return "";
		}
		return temp;
	}

	/**
	 * 判断字符串数组 String[]是否为空
	 * @param obj the obj
	 * @return the boolean
     */
	public static boolean isStringArrayEmpty(Object obj){
		boolean value = true;
		try{
			if(obj != null){
				String[] strs = (String[])obj;
				if(strs.length > 0){
					value = false;
				}
			}
		}catch(Exception e){
			StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
			value = true;
		}
		return value;
	}

	/**
	 * Is not string array empty.
	 *
	 * @param obj the obj
	 * @return the boolean
     */
	public static boolean isNotStringArrayEmpty(Object obj){
		return !isStringArrayEmpty(obj);
	}

	/**
	 * 判断两个字符串是否相等
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2
	 * @return boolean boolean
     */
	public static boolean isEquals(Object obj1,Object obj2){
		return convertToString(obj1).equals(convertToString(obj2));
	}

	/**
	 * 判断两个字符串是否不相等
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2
	 * @return boolean boolean
     */
	public static boolean isNotEquals(Object obj1,Object obj2){
		return !isEquals(obj1,obj2);
	}

	/**
	 * 判断是否为空
	 * @param obj the obj
	 * @return the boolean
     */
	public static boolean isEmpty(Object obj){
		if(obj==null){
			return true;
		}
		String temp = obj.toString().trim();
		return ("".equals(temp) || "null".equals(temp));
	}

	/**
	 * 判断是否不为空
	 * @param obj the obj
	 * @return the boolean
     */
	public static boolean isNotEmpty(Object obj){
		return !isEmpty(obj);
	}

	/**
	 * 获取唯一数
	 * @return the string
     */
	public static String  getDateRandow(){
		SimpleDateFormat tempDate = new SimpleDateFormat("yyMMdd" + "" + "hhmmssSS");
		String datetime = tempDate.format(new java.util.Date());    //12位
        Random r = new Random() ;
        int randomInt=r.nextInt(10001);
		datetime =  datetime+randomInt;
		return datetime;
	}

//	public static void main(String[] args) {
//		System.out.println(getMD5Str("123456"));
//		System.out.println(getNextNo("A","A20140919-043"));
//        Random r = new Random() ;
//        System.out.println(r.nextInt(10000));
//        DecimalFormat df1 = new DecimalFormat("#####");
//        System.out.println(df1.format(r));
//	}

	/**
	 * 获取MD5加密串
	 * create by beeyon
	 * 2012-07-31
	 * @param value the value
	 * @return mD 5 str
     */
	public static String getMD5Str(String value) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(value.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			StudyLogger.recSysLog(Level.ERROR, e.getMessage(), e);
		}
		byte[] bytearr = md5.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytearr.length; i++) {
			if (Integer.toHexString(0xFF & bytearr[i]).length() == 1) {
				sb.append("0").append(Integer.toHexString(0xFF & bytearr[i]));
			} else {
				sb.append(Integer.toHexString(0xFF & bytearr[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * 获取客户端Ip
	 * @param request the request
	 * @return ip address
     */
	public static String getIpAddress(HttpServletRequest request) {
		if (request == null){
			return "";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * Get base path.
	 *
	 * @param request the request
	 * @return the string
     */
	public static String getBasePath(HttpServletRequest request){
		String basePath = "";
		if(request==null){
			return basePath;
		}
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int port = request.getServerPort();
		String path = request.getContextPath();
		if(port==80){
			basePath = scheme + PROTOCOL_SUFFIX + serverName + path;
		}else{
			basePath = scheme + PROTOCOL_SUFFIX + serverName + ":" + port + path;
		}
		return basePath;
	}

	/**
	 * Get base path.
	 *
	 * @return the string
     */
	public static String getBasePath(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		String basePath = "";
		if(request==null){
			return basePath;
		}
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int port = request.getServerPort();
		String path = request.getContextPath();
		if(port==80){
			basePath = scheme + PROTOCOL_SUFFIX + serverName + path;
		}else{
			basePath = scheme + PROTOCOL_SUFFIX + serverName + ":" + port + path;
		}
		return basePath;
	}

	/**
	 * 将字符串数组转为字符串
	 * @param statusArray the status array
	 * @return string array to string
     */
	public static String getStringArrayToString(String[] statusArray) {
		String status = "";
        StringBuffer buf = new StringBuffer();
		if(statusArray!=null && statusArray.length>0){
			for(int i=0;i<statusArray.length;i++){
                buf.append("," + statusArray[i]);
				//status =status+ "," + statusArray[i];
			}
		}
		if(isNotEmpty(buf.toString())){
			status = buf.toString().substring(1);
		}
		return status;
	}

	/**
	 * Gets hide email prefix.
	 *
	 * @param email the email
	 * @return the hide email prefix
     */
	public static String getHideEmailPrefix(String email) {
		String prefix = "";
        if (null != email) {
            int index = email.lastIndexOf('@');
            if (index > 0) {
				prefix = repeat("*", index).concat(email.substring(index));
            }
        }
        return prefix;
    }

	/**
	 * Repeat string.
	 *
	 * @param src the src
	 * @param num the num
	 * @return the string
     */
	public static String repeat(String src, int num) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < num; i++){
            s.append(src);
		}
        return s.toString();
    }

	/**
	 * Is mobile nO.
	 *
	 * @param mobiles the mobiles
	 * @return the boolean
     */
	public static boolean isMobileNO(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * Is email.
	 *
	 * @param email the email
	 * @return the boolean
     */
	public static boolean isEmail(String email){
		String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}




}