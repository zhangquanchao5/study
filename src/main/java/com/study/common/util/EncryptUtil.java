package com.study.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xingz on 15-1-24.
 */
public final class EncryptUtil {

    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     *
     * @param strSrc 要加密的字符串
     * @param encName 加密类型
     * @return
     */
    public static final String MD5 = "MD5";
    /**
     * The constant SHA1.
     */
    public static final String SHA1 = "SHA-1";
    /**
     * The constant SHA256.
     */
    public static final String SHA256 = "SHA-256";

    private EncryptUtil() {
    }

    /**
     * encrypt string.
     *
     * @param strSrc the str src
     * @param encName the enc name
     * @return the string
     */
    public static String encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;
        try {
            byte[] bt = strSrc.getBytes("UTF-8");
            String _enc = encName;
            if (_enc == null || "".equals(_enc)) {
                _enc = "SHA-256";
            }
            md = MessageDigest.getInstance(_enc);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }catch (UnsupportedEncodingException ex){
            return null;
        }
        return strDes;
    }

    private static String bytes2Hex(byte[] bts) {
        StringBuffer buf = new StringBuffer("");
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                buf.append("0");
               // des =des+ "0";
            }
            buf.append(tmp);
          //  des =des+ tmp;
        }
        return buf.toString();
    }


//    public static void main(String args[]) {
//        long now = System.currentTimeMillis();
//        for (int i = 0; i < 1000; i++) {
//            UUID uuid = UUID.randomUUID();
//            System.out.println("a = " + uuid);
//            String s = EncryptUtil.encrypt(String.valueOf(uuid), "");
//            System.out.println(s);
//        }
//        System.out.println("abc" + String.valueOf(System.currentTimeMillis() - now));

//    }

}
