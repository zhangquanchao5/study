package com.study.api.web;

import com.alibaba.fastjson.JSON;
import com.study.api.bean.AccountInfoReq;
import com.study.api.bean.AccountInfoResp;
import com.study.api.bean.DepositAndWithdrawReq;
import com.study.api.exception.BalanceNotEnoughException;
import com.study.api.exception.ParameterNotEnoughException;
import com.study.api.exception.ProcessFailureException;
import com.study.api.exception.UserNotExitsException;
import com.study.api.service.ApiAccountService;
import com.study.code.ErrorCode;
import com.study.common.StudyLogger;
import com.study.common.apibean.ApiResponseMessage;
import com.study.common.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Star on 2015/7/9.
 */
@Controller
public class ApiController {

    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private ApiAccountService apiAccountService;

    @RequestMapping(value = "/accountinfo", method = RequestMethod.POST)
    private @ResponseBody
    ApiResponseMessage getAccountSnapshoot(@RequestBody AccountInfoReq req){
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

    @RequestMapping(value = "/account/deposit", method = RequestMethod.POST, headers = "Accept=application/json")
    private @ResponseBody ApiResponseMessage deposit(@RequestBody String strJson){
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("account deposit:" + strJson);
        DepositAndWithdrawReq depositAndWithdrawReq = JSON.parseObject(strJson, DepositAndWithdrawReq.class);
        try {
            apiAccountService.saveForDeposit(depositAndWithdrawReq);
            message.setCode(ErrorCode.PROCESS_SUCC);
            message.setMsg(messageUtil.getMessage("msg.process.succ"));
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
    private @ResponseBody ApiResponseMessage withdraw(@RequestBody String strJson){
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("account deposit:" + strJson);
        DepositAndWithdrawReq depositAndWithdrawReq = JSON.parseObject(strJson, DepositAndWithdrawReq.class);
        try {
            apiAccountService.saveForWithdraw(depositAndWithdrawReq);
            message.setCode(ErrorCode.PROCESS_SUCC);
            message.setMsg(messageUtil.getMessage("msg.process.succ"));
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
}
