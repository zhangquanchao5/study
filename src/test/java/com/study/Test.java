package com.study;

import com.study.code.SplitCode;
import com.study.common.StringUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by huichao on 2015/7/14.
 */
public class Test {
    public static void main(String []args) throws UnsupportedEncodingException {
        System.out.println(StringUtil.getBASE64("1000015=MTAwMDAxNT1pb3N8MTAwMDAxNXwxNDQzODU1NzQyNTI1"));
        System.out.println(StringUtil.getFromBASE64("MTAwMDAzOT1hbmRyb2lkfDEwMDAwMzl8MTQ0Mzc5NzUxMjM5NA=="));
        String codes="1000039=MTAwMDAzOT1hbmRyb2lkfDEwMDAwMzl8MTQ0Mzc5NzUxMjM5NA==";
        System.out.println("qqq:"+codes.substring("1000039".length()+1,codes.length()));
        System.out.println("MTAwMDAyMj1NVEF3TURBeU1qMW9OWHd4TURBd01ESXlmRFkwT0RBd2ZERTBNemd5TlRNeE9Ea3hOekU9");
        System.out.println("1000022|64800|1438077580622".split("\\|")[1]);

        System.out.println(StringUtil.getMD5Str("111111"));
    }
}
