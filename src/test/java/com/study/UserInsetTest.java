package com.study;

import com.study.code.EntityCode;
import com.study.common.StringUtil;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;
import com.study.service.IUserService;
import org.junit.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huichao on 2015/8/10.
 */
public class UserInsetTest  extends BaseTest {
    @Autowired
    private IUserService iUserService;

    @Test
    @Rollback(false)
    public void testInsert(){
        Map<String,String> orgs=new HashMap<String,String>();
       // orgs.put("jingwuyongchun","江南精武咏春拳馆");
//        orgs.put("bangbangbeibei","棒棒贝贝");
//        orgs.put("gelinxier","格林希尔");
//        orgs.put("zhide","至德教育");
//        orgs.put("jinbaobei","金宝贝");
//        orgs.put("meijimu","美吉姆");
//        orgs.put("ailun","艾伦英语");
//        orgs.put("dianjing","点睛教育");
//        orgs.put("yanguangjiaoyu","西安阳光教育集团");
//        orgs.put("yingtesi","英特思");
      //  orgs.put("jingwuyongchun","江南精武咏春拳馆");
         orgs.put("xicishuhua","西祠少儿书画教育");
        orgs.put("fengbinweiqi","凤宾围棋俱乐部");
        orgs.put("jinmiaozaojiao","金苗早教");
        orgs.put("fanrongjianzhulu","繁荣琴行（建筑路）");
        orgs.put("fanrongxueqianjie","繁荣琴行（学前街）");
        orgs.put("woerde","沃尔得语言培训中心");
        orgs.put("tianyiweiqi","天一围棋馆");
        for (Map.Entry<String, String> entry : orgs.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            if(iUserService.findByUserName(entry.getKey())==null){
                UserInfo userInfoModel=new UserInfo();
                userInfoModel.setUserName(entry.getKey());
                userInfoModel.setName(entry.getValue());
                userInfoModel.setSource((byte)1);
                userInfoModel.setPassword(StringUtil.getMD5Str("000000"));
                userInfoModel.setCreateTime(new Date());
                userInfoModel.setStatus(EntityCode.USER_VALIDATE);

                UserInfoFrom userInfoFrom = new UserInfoFrom();
                userInfoFrom.setFrom(EntityCode.USER_FROM_MOBILE);

                iUserService.saveUserInfo(userInfoModel, userInfoFrom);
            }else{
                System.out.println("user name has:"+entry.getKey());
            }

        }
    }
}
