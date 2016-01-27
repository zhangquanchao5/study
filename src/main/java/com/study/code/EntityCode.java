package com.study.code;

/**
 * Created by huichao on 2015/7/8.
 */
public class EntityCode {

    public static final byte USER_VALIDATE=0;
    public static final byte USER_NO_VALIDATE=1;
    public static final byte USER_DELETE=2;

    public static final byte USER_FROM_MOBILE=0;
    public static final byte USER_FROM_QQ=1;
    public static final byte USER_FROM_WEIXIN=2;
    public static final byte USER_FROM_WEIBO=3;

    public static final byte USER_SOURCE_APP=0;
    public static final byte USER_SOURCE_SYS=1;

    public static final Integer MOBILE_REGESITER=1;
    public static final Integer MOBILE_GET_PASSWORD=2;
    public static final Integer MOBILE_BIND_UPDATE=3;
    public static final Integer MOBILE_YU_YUE=4;
    public static final Integer MOBILE_RESET_PWD=5;


    public static final Integer EMAIL_PWD=1;
    public static final Integer EMAIL_ACTIVE=2;

    public static final String BILLTYPE_CODE_CASH="cash";
    public static final String BILLTYPE_CODE_GIFT="gift";
}
