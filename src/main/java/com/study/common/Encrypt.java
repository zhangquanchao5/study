package com.study.common;

import java.util.Random;


/**
 * The type encrypt.
 */
public final class Encrypt {
    /**
     * The constant pass1.
     */
    public static final int pass1 = 10;
    /**
     * The constant pass2.
     */
    public static final int pass2 = 1;
    /**
     * The constant RANDOM.
     */
    public static final Random RANDOM = new Random();

    private Encrypt() {
    }

    /**
     * Do encrypt.
     *
     * @param str the str
     * @return String 返回类型
     * @Title: doEncrypt
     * @Description: 对密码进行加密的方法
     * @throws
     */
    public static String doEncrypt(String str) {
        StringBuffer enStrBuff = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            int tmpch = str.charAt(i);
            tmpch ^= 1;
            tmpch ^= 0xa;
            enStrBuff.append(Integer.toHexString(tmpch));
        }
        return enStrBuff.toString().toUpperCase();
    }

    /**
     * Do decrypt.
     *
     * @param str the str
     * @return String 返回类型
     * @Title: doDecrypt
     * @Description: 对密码进行解密的方法
     * @throws
     */
    public static String doDecrypt(String str) {
        String deStr = str.toLowerCase();
        StringBuffer deStrBuff = new StringBuffer();
        for (int i = 0; i < deStr.length(); i += 2) {
            String subStr = deStr.substring(i, i + 2);
            int tmpch = Integer.parseInt(subStr, 16);
            tmpch ^= 1;
            tmpch ^= 0xa;
            deStrBuff.append((char) tmpch);
        }
        return deStrBuff.toString();
    }


//    public static void main(String args[]) {
//        String source = "123456";
//        String s = doEncrypt(source);
//        System.out.println("en=" + s);
//        source = doDecrypt(s);
//        System.out.println("de=" + source);
//        for(int i=0;i<10;i++){
//            System.out.println(random(6));
//        }

//    }

    /**
     * Random string.
     *
     * @param range the range
     * @return the string
     */
    public static String random(int range){
        StringBuffer buf = new StringBuffer("");
       // String random = "";
        for(int i=0;i<range;i++){
            buf.append(String.valueOf(RANDOM.nextInt(9)));
            //random=random+String.valueOf(RANDOM.nextInt(9));
        }
        return buf.toString();
    }
}
