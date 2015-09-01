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
         orgs.put("新巴别塔","xinbabieta");
        orgs.put("麒麟艺术","qilinyishu");
        orgs.put("包头英孚","baotouyingfu");
        orgs.put("伊韵东方舞","yiyunwudao");
//        orgs.put("西安新航道雅思","西安新航道雅思");
//        orgs.put("西安思润培训中心","西安思润培训中心");
//        orgs.put("江南精武咏春拳馆","江南精武咏春拳馆");
//        orgs.put("龙韵文化艺术","龙韵文化艺术");
//        orgs.put("无锡沃尔得语言培训","无锡沃尔得语言培训");
//        orgs.put("易鸿运教育","易鸿运教育");
//        orgs.put("卓悦学科英语","卓悦学科英语");
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
