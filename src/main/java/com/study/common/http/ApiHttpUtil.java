package com.study.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.common.StudyLogger;
import com.study.common.bean.LightDetailVo;
import com.study.common.util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huichao on 2016/5/24.
 */
public class ApiHttpUtil {

    public static LightDetailVo executeLight(String domain) {
        LightDetailVo lightDetailVo=new LightDetailVo();
        HttpSendResult httpSendResult=HttpUtil.executeGet(PropertiesUtil.getString("SCHOOL.SYN.URL") + domain);

        System.out.println("JSON:"+JSON.toJSONString(httpSendResult));
        if(httpSendResult.getStatusCode()==200){
            JSONObject jsonObject= JSON.parseObject(httpSendResult.getResponse());
            String code=jsonObject.getString("code").toString();
            if(code.equals("200")){
                String data=jsonObject.getString("data");
                jsonObject=JSON.parseObject(data);

                lightDetailVo.setColor(jsonObject.getString("colour"));
                lightDetailVo.setLogo(jsonObject.getString("org_logo"));
            }
        }

        return lightDetailVo;
    }

    public static void addLog(String header,Integer userId,String operName,String httpUrl,String json){
        Map<String,String> map=new HashMap<String,String>();
        String body=header+"||"+operName+"|"+httpUrl+"|"+json;
        map.put("body",body);
        HttpSendResult sendResult=HttpUtil.formPostUrl(PropertiesUtil.getString("LOG.SUBMIT.URL"), map);

        StudyLogger.recBusinessLog("addLog req:"+body);
        StudyLogger.recBusinessLog("addLog res:"+JSON.toJSONString(sendResult));
    }
}
