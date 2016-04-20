package com.study.code;

/**
 * Created by huichao on 2015/7/8.
 */
public class EntityCode {

    public static final byte USER_VALIDATE=0;
    public static final byte USER_NO_VALIDATE=1;
    public static final byte USER_DELETE=2;
    public static final byte USER_NEED_ACTIVE=3;

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
    public static final Integer MOBILE_LOGIN_CODE=6;


    public static final Integer EMAIL_PWD=1;
    public static final Integer EMAIL_ACTIVE=2;

    public static final String BILLTYPE_CODE_CASH="cash";
    public static final String BILLTYPE_CODE_GIFT="gift";
    public static final String BILLTYPE_CODE_RED="red";

    public static final Integer BILLTYPE_CODE_CASH_ID=1;
    public static final Integer BILLTYPE_CODE_GIFT_ID=2;
    public static final Integer BILLTYPE_CODE_RED_ID=3;


    public static final String BILLTYPE_CODE_1="1";//现金充值
    public static final String BILLTYPE_CODE_2="2";//红包充值
    public static final String BILLTYPE_CODE_3="3";//代金券充值值
    public static final String BILLTYPE_CODE_4="4";//购买课程充值
    public static final String BILLTYPE_CODE_5="5";//现金扣款
    public static final String BILLTYPE_CODE_6="6";//红包扣款
    public static final String BILLTYPE_CODE_7="7";//代金券扣款
    public static final String BILLTYPE_CODE_8="8";//提现

    public static final byte BANK_VALID=1; //正常
    public static final byte BANK_INVALID=0; //删除

    public static final int WITHDRAW_STATUS_APPLY =1;//申请中
    public static final int WITHDRAW_STATUS_SUCC=2; //已打款
    public static final int WITHDRAW_STATUS_REFUSE =3; //拒绝提现
}
