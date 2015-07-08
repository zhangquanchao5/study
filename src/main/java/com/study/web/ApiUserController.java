package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.ErrorCode;
import com.study.common.apibean.ApiResponseMessage;
import com.study.common.apibean.ApiUserBean;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.service.IApIUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by huichao on 2015/7/7.
 */
@Controller
@RequestMapping("/api/user")
public class ApiUserController extends BaseController {

    @Autowired
    private IApIUserService iApIUserService;
    /**
     * Register save.
     */
    @RequestMapping(method = RequestMethod.POST)
    public void registerSave(@RequestBody ApiUserBean apiUserBean, HttpServletResponse response) {

        ApiResponseMessage message = new ApiResponseMessage();
        try {

            UserInfo userInfo = iApIUserService.findByMobile(apiUserBean.getMobile());
            if (userInfo != null) {
                message.setCode(ErrorCode.ERROR);
                message.setMsg(ErrorCode.USER_MOBILE_EXITS);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
                return;
            }
            iApIUserService.saveUser(apiUserBean);

            message.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            message.setCode(ErrorCode.ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }
}
