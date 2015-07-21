package com.study.api.web;

import com.alibaba.fastjson.JSON;
import com.study.api.bean.AccountInfoReq;
import com.study.api.bean.AccountInfoResp;
import com.study.api.bean.DepositAndWithdrawReq;
import com.study.api.bean.PayPasswordReq;
import com.study.api.exception.BalanceNotEnoughException;
import com.study.api.exception.ParameterNotEnoughException;
import com.study.api.exception.ProcessFailureException;
import com.study.api.exception.UserNotExitsException;
import com.study.service.IRedisService;
import com.study.service.impl.api.ApiAccountService;
import com.study.code.ErrorCode;
import com.study.common.StudyLogger;
import com.study.common.apibean.ApiResponseMessage;
import com.study.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Star on 2015/7/9.
 */
@Controller
@RequestMapping("/api")
public class ApiAccountController extends BaseController {

    @Autowired
    private IRedisService iRedisService;
    @Autowired
    private ApiAccountService apiAccountService;

    @RequestMapping(value = "/user/accountinfo", method = RequestMethod.POST)
    private
    @ResponseBody
    ApiResponseMessage getAccountSnapshoot(@RequestBody AccountInfoReq req) {
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("userid:" + req.getId());
        try {
            AccountInfoResp resp = apiAccountService.getAccountInfo(req.getId());
            message.setCode(ErrorCode.PROCESS_SUCC);
            message.setMsg(messageUtil.getMessage("msg.process.succ"));
            message.setData(resp);
        } catch (ParameterNotEnoughException e) {
            message.setCode(e.getCode());
            message.setMsg(e.getMessage());
            StudyLogger.recSysLog(e);
        } catch (UserNotExitsException e) {
            message.setCode(e.getCode());
            message.setMsg(e.getMessage());
            StudyLogger.recSysLog(e);
        } catch (ProcessFailureException e) {
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

    @RequestMapping(value = "/coupon/receiveCash", method = RequestMethod.POST, headers = "Accept=application/json")
    private
    @ResponseBody
    ApiResponseMessage deposit(@RequestBody String strJson, HttpServletRequest request) {
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("account deposit:" + strJson);
        DepositAndWithdrawReq depositAndWithdrawReq = JSON.parseObject(strJson, DepositAndWithdrawReq.class);
        try {
            if (isAuthToken(iRedisService, request)) {
                apiAccountService.saveForDeposit(getAuthHeader().getUserId(), depositAndWithdrawReq);
                message.setCode(ErrorCode.PROCESS_SUCC);
                message.setMsg(messageUtil.getMessage("msg.process.succ"));
            } else {
                message.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                message.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
            }
        } catch (ParameterNotEnoughException e) {
            message.setCode(e.getCode());
            message.setMsg(e.getMessage());
            StudyLogger.recSysLog(e);
        } catch (UserNotExitsException e) {
            message.setCode(e.getCode());
            message.setMsg(e.getMessage());
            StudyLogger.recSysLog(e);
        } catch (ProcessFailureException e) {
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

    @RequestMapping(value = "/account/withdraw", method = RequestMethod.POST, headers = "Accept=application/json")
    private
    @ResponseBody
    ApiResponseMessage withdraw(@RequestBody String strJson, HttpServletRequest request) {
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("account deposit:" + strJson);
        DepositAndWithdrawReq depositAndWithdrawReq = JSON.parseObject(strJson, DepositAndWithdrawReq.class);
        try {
            if (isAuthToken(iRedisService, request)) {
                apiAccountService.saveForWithdraw(depositAndWithdrawReq);
                message.setCode(ErrorCode.PROCESS_SUCC);
                message.setMsg(messageUtil.getMessage("msg.process.succ"));
            } else {
                message.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                message.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
            }
        } catch (ParameterNotEnoughException e) {
            message.setCode(e.getCode());
            message.setMsg(e.getMessage());
            StudyLogger.recSysLog(e);
        } catch (UserNotExitsException e) {
            message.setCode(e.getCode());
            message.setMsg(e.getMessage());
            StudyLogger.recSysLog(e);
        } catch (BalanceNotEnoughException e) {
            message.setCode(e.getCode());
            message.setMsg(e.getMessage());
            StudyLogger.recSysLog(e);
        } catch (ProcessFailureException e) {
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

    @RequestMapping(value = "/account/paypwd", method = RequestMethod.POST, headers = "Accept=application/json")
    private
    @ResponseBody
    ApiResponseMessage update(@RequestBody String strJson, HttpServletRequest request) {
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("account paypwd:" + strJson);
        PayPasswordReq payPasswordReq = JSON.parseObject(strJson, PayPasswordReq.class);
        try {
            if (isAuthToken(iRedisService, request)) {
                apiAccountService.updatePayPassword(getAuthHeader(request).getUserId(), payPasswordReq.getNewPasswd());
                message.setCode(ErrorCode.PROCESS_SUCC);
                message.setMsg(messageUtil.getMessage("msg.process.succ"));
            } else {
                message.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                message.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
            }
        } catch (ParameterNotEnoughException e) {
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
}
