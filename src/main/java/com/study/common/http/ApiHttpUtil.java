package com.study.common.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.common.bean.LightDetailVo;
import com.study.common.util.PropertiesUtil;

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
}
