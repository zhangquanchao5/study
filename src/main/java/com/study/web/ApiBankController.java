package com.study.web;

import com.study.code.ErrorCode;
import com.study.common.StudyLogger;
import com.study.common.apibean.ApiResponseMessage;
import com.study.common.apibean.request.BankBindReq;
import com.study.common.apibean.response.CommonResponse;
import com.study.exception.BankDuplicateBindingException;
import com.study.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by huichao on 2016/4/20.
 */
@Controller
@RequestMapping("/api")
public class ApiBankController extends BaseController {

    @Autowired
    private IBankService iBankService;

    @RequestMapping("/banks/bind")
    public @ResponseBody
    ApiResponseMessage bind(@RequestBody BankBindReq req, HttpServletRequest request){
        ApiResponseMessage message = new ApiResponseMessage();
        try {
            Integer userId = getAuthHeader(request).getUserId();
            StudyLogger.recBusinessLog("userId:" + userId);
            if (null != userId) {
                iBankService.bindBank(userId, req);
            } else {
                message.setCode(ErrorCode.PROCESS_FAIL);
                message.setMsg(messageUtil.getMessage("msg.user.notExits"));
            }
        } catch (BankDuplicateBindingException e) {
            message.setCode(e.getCode());
            message.setMsg(e.getMessage());
            StudyLogger.recSysLog(e);
        } catch (Exception e) {
            message.setCode(ErrorCode.PROCESS_FAIL);
            message.setMsg(messageUtil.getMessage("msg.process.fail"));
            StudyLogger.recSysLog(e);
        }

        return message;
    }

    @RequestMapping("/banks/list")
    public @ResponseBody
    ApiResponseMessage list(HttpServletRequest request){
        ApiResponseMessage message = new ApiResponseMessage();
        try {
            Integer userId = getAuthHeader(request).getUserId();
            StudyLogger.recBusinessLog("userId:" + userId);
            if (null != userId) {
                List list = iBankService.findAllBanks(userId);
                message.setDatas(list);
            } else {
                message.setCode(ErrorCode.PROCESS_FAIL);
                message.setMsg(messageUtil.getMessage("msg.user.notExits"));
            }
        } catch (Exception e) {
            message.setCode(ErrorCode.PROCESS_FAIL);
            message.setMsg(messageUtil.getMessage("msg.process.fail"));
            StudyLogger.recSysLog(e);
        }
        return message;
    }

    @RequestMapping(value = "/banks/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    ApiResponseMessage unbind(@PathVariable("id") Integer id, HttpServletRequest request){
        ApiResponseMessage message = new ApiResponseMessage();
        try {
            StudyLogger.recBusinessLog("unbind bank:" + id);
            iBankService.unbindBank(id);
        } catch (Exception e) {
            message.setCode(ErrorCode.PROCESS_FAIL);
            message.setMsg(messageUtil.getMessage("msg.process.fail"));
            StudyLogger.recSysLog(e);
        }
        return message;
    }
}
