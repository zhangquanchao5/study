package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.PrefixCode;
import com.study.common.StudyLogger;
import com.study.common.oss.DESUtils;
import com.study.common.oss.LoginOutResponse;
import com.study.common.util.PropertiesUtil;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.service.IRedisService;
import com.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huichao on 2015/7/9.
 */
@Controller
@RequestMapping("/ssoAuth")
public class SSOAuthController extends BaseController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRedisService iRedisService;

    @RequestMapping(value ="")
    public void authTicket(@RequestParam String action,@RequestParam String cookieName,HttpServletResponse response,HttpServletRequest request) {

        LoginOutResponse loginOutResponse=new LoginOutResponse();

        try {
            StudyLogger.recBusinessLog("authTicket:" + cookieName+"---action:"+action+"------------reqId:"+request.getParameter("reqId")==null?"":request.getParameter("reqId"));
            if(cookieName==null||"".equals(cookieName)){
                loginOutResponse.setError(true);
                loginOutResponse.setErrorInfo("Ticket can not be empty!");
            } else {
                String decodedTicket = DESUtils.decrypt(cookieName, PropertiesUtil.getString("sso.secretKey"));
                StudyLogger.recBusinessLog("authTicket ticket:"+decodedTicket);
                if(action.equals(PrefixCode.API_ACTION_LOGINOUT)){
                    iRedisService.deleteOneKey(PrefixCode.API_COOKIE_PRE + decodedTicket);
                    loginOutResponse.setError(false);
                    StudyLogger.recBusinessLog("loginOut ok:" + cookieName);
                }else if(action.equals(PrefixCode.API_ACTION_AUTH)){
                    Object obj=iRedisService.getObject(PrefixCode.API_COOKIE_PRE + decodedTicket);
                    if(obj==null){
                        StudyLogger.recBusinessLog("authTicket logout "+decodedTicket);
                        loginOutResponse.setError(true);
                    }else{
                        StudyLogger.recBusinessLog("authTicket ok " + decodedTicket);
                        loginOutResponse.setError(false);
                        loginOutResponse.setErrorInfo("");
                        loginOutResponse.setData(obj);
                    }
                }else{
                    loginOutResponse.setError(true);
                    loginOutResponse.setErrorInfo("ERROR ACTION "+action);
                }
            }
        } catch (Exception e) {
            loginOutResponse.setError(true);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(loginOutResponse).toString());
    }

}
