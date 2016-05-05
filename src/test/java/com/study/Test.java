package com.study;

import com.study.code.SplitCode;
import com.study.common.StringUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by huichao on 2015/7/14.
 */
public class Test {
    public static void main(String []args) throws UnsupportedEncodingException {
         System.out.println(StringUtil.getBASE64("1000673=MTAwMDY3Mz13ZWJ8MTAwMDY3M3wxNDU5MjMyMDEwMDM1"));
        System.out.println(StringUtil.getFromBASE64("MTAwMDY3Mz13ZWJ8MTAwMDY3M3wxNDU5MjIyNTUyNjQ5"));
        System.out.println(System.currentTimeMillis()-Long.parseLong("1459221317477"));
        String bank="1234444";
        System.out.println(StringUtil.formatBankNo(bank));
//        String codes="1000039=MTAwMDAzOT1hbmRyb2lkfDEwMDAwMzl8MTQ0Mzc5NzUxMjM5NA==";
//        System.out.println("qqq:"+codes.substring("1000039".length()+1,codes.length()));
//        System.out.println("MTAwMDAyMj1NVEF3TURBeU1qMW9OWHd4TURBd01ESXlmRFkwT0RBd2ZERTBNemd5TlRNeE9Ea3hOekU9");
//        System.out.println("1000022|64800|1438077580622".split("\\|")[1]);

//        System.out.println(StringUtil.getMD5Str("111111"));
    }
}
