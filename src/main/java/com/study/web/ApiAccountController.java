package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.SplitCode;
import com.study.common.StringUtil;
import com.study.common.apibean.request.*;
import com.study.common.apibean.response.AccountDetailResp;
import com.study.common.apibean.response.AccountInfoResp;
import com.study.common.apibean.response.CommonResponse;
import com.study.common.util.DESUtils;
import com.study.common.util.EncryptUtil;
import com.study.common.util.ServletResponseHelper;
import com.study.exception.*;
import com.study.model.UserInfo;
import com.study.service.IBankService;
import com.study.service.IRedisService;
import com.study.service.impl.api.ApiAccountService;
import com.study.code.ErrorCode;
import com.study.common.StudyLogger;
import com.study.common.apibean.ApiResponseMessage;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
    @Autowired
    private IBankService bankService;

    /**
     * 账户明细查询
     * @param req
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/detail", method = RequestMethod.POST)
    private
    @ResponseBody
    ApiResponseMessage getAccountHistory(@RequestBody AccountInfoPageReq req, HttpServletRequest request) {
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("userid:" + req.getId());
        try {
            if (isAuthToken(iRedisService, request)) {
                Integer userId = getAuthHeader(request).getUserId();
                if (req.getId().intValue() == userId.intValue()) {
                    AccountDetailResp resp = apiAccountService.getAccountHistory(req);
                    message.setCode(ErrorCode.PROCESS_SUCC);
                    message.setMsg(messageUtil.getMessage("msg.process.succ"));
                    message.setData(resp);
                } else {
                    message.setCode(ErrorCode.PROCESS_FAIL);
                    message.setMsg(messageUtil.getMessage("msg.process.fail"));
                }
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
        } catch (AccountNotExitsException e) {
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

    @RequestMapping(value = "/user/accountinfo", method = RequestMethod.POST)
    private
    @ResponseBody
    ApiResponseMessage getAccountSnapshoot(@RequestBody AccountInfoReq req, HttpServletRequest request) {
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("userid:" + req.getId());
        try {
            if (isAuthToken(iRedisService, request)) {
                Integer userId = getAuthHeader(request).getUserId();
                if (req.getId().intValue() == userId.intValue()) {
                    AccountInfoResp resp = apiAccountService.getAccountInfo(req.getId());
                    message.setCode(ErrorCode.PROCESS_SUCC);
                    message.setMsg(messageUtil.getMessage("msg.process.succ"));
                    message.setData(resp);
                } else {
                    message.setCode(ErrorCode.PROCESS_FAIL);
                    message.setMsg(messageUtil.getMessage("msg.process.fail"));
                }
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

    @RequestMapping(value = "/coupon/receiveCash", method = RequestMethod.POST)
    private
    @ResponseBody
    ApiResponseMessage deposit(@RequestBody String strJson, HttpServletRequest request) {
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("account deposit:" + strJson);
        DepositAndWithdrawReq depositAndWithdrawReq = JSON.parseObject(strJson, DepositAndWithdrawReq.class);
        try {
            if (isAuthToken(iRedisService, request)) {
                Integer userId = getAuthHeader(request).getUserId();
                apiAccountService.saveForDeposit(userId, depositAndWithdrawReq);
                AccountInfoResp resp = apiAccountService.getAccountInfo(userId);
                message.setData(resp);
                message.setCode(ErrorCode.PROCESS_SUCC);
                message.setMsg(messageUtil.getMessage("msg.process.succ"));
            } else {
                message.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                message.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
                StudyLogger.recBusinessLog(Level.ERROR, "request-userId is not equal to header-userId");
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
        } catch (RepeatDepositException e) {
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

    @RequestMapping(value = "/account/accountpay", method = RequestMethod.POST)
    private
    @ResponseBody
    ApiResponseMessage withdraw(@RequestBody String strJson, HttpServletRequest request) {
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("account deposit:" + strJson);
        DepositAndWithdrawReq depositAndWithdrawReq = JSON.parseObject(strJson, DepositAndWithdrawReq.class);
        try {
            if (isAuthToken(iRedisService, request)) {
                Integer userId = getAuthHeader(request).getUserId();
                apiAccountService.saveForWithdraw(userId, depositAndWithdrawReq);
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
        } catch (RepeatWithdrawException e) {
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

    @RequestMapping(value = "/user/changePwd", method = RequestMethod.POST)
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

    /**
     * 给分享着用户充值
     */
    @RequestMapping(value = "/account/recharge")
    public void recharge(HttpServletRequest request, HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        try {

            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/account/recharge:" + json);

            RechargeReq rechargeReq = JSON.parseObject(json, RechargeReq.class);
            commonResponse.setCode(ErrorCode.SUCCESS);

            commonResponse=apiAccountService.saveRedRecharge(rechargeReq,commonResponse) ;
            if(commonResponse.getCode().equals(ErrorCode.SUCCESS)){
                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
            }
        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }

    /**
     * 给组织机构用户充值
     */
    @RequestMapping(value = "/account/orgRecharge")
    public void orgRecharge(HttpServletRequest request, HttpServletResponse response) {

        CommonResponse message = new CommonResponse();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/account/orgRecharge:" + json);
            OrgRechargeReq orgRechargeReq = JSON.parseObject(json, OrgRechargeReq.class);
            String orgId= DESUtils.decrypt(orgRechargeReq.getOrgId(), DESUtils.secretKey);
            String money =DESUtils.decrypt(orgRechargeReq.getMoney().toString(), DESUtils.secretKey);

            if(!EncryptUtil.encrypt(orgId +""+money, EncryptUtil.MD5).equals(orgRechargeReq.getAuthKey())){
                message.setCode(ErrorCode.RED_RECHARGE_CODE_ERROR);
            }else{
                DepositAndWithdrawReq depositAndWithdrawReq = new DepositAndWithdrawReq();
                depositAndWithdrawReq.setAccountBIllType(orgRechargeReq.getAccountBIllType());
                depositAndWithdrawReq.setAmount(Integer.parseInt(money));
                depositAndWithdrawReq.setTradeNO(UUID.randomUUID().toString());

                apiAccountService.saveForDeposit(Integer.parseInt(orgId), depositAndWithdrawReq);
                message.setCode(ErrorCode.PROCESS_SUCC);
                message.setMsg(messageUtil.getMessage("msg.process.succ"));
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
        } catch (RepeatDepositException e) {
            message.setCode(e.getCode());
            message.setMsg(e.getMessage());
            StudyLogger.recSysLog(e);
        } catch (Exception e) {
            message.setCode(ErrorCode.PROCESS_FAIL);
            message.setMsg(messageUtil.getMessage("msg.process.fail"));
            StudyLogger.recSysLog(e);
        }

        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    @RequestMapping(value = "/user/prewithdraw", method = RequestMethod.POST)
    private
    @ResponseBody
    ApiResponseMessage prewithdraw(@RequestBody BankWithdrawReq req, HttpServletRequest request) {
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("api/user/prewithdraw:" + JSON.toJSONString(req));
        try {
            if (isAuthToken(iRedisService, request)) {
                req.setUserId(getAuthHeader(request).getUserId());
                message=bankService.findPageWithDraw(req,message);
                message.setCode(ErrorCode.SUCCESS);
            } else {
                message.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                message.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
            }
        }  catch (Exception e) {
            message.setCode(ErrorCode.PROCESS_FAIL);
            message.setMsg(messageUtil.getMessage("msg.process.fail"));
            StudyLogger.recSysLog(e);
        }

        return message;
    }

    @RequestMapping(value = "/user/accounts/query", method = RequestMethod.POST)
    private
    @ResponseBody
    ApiResponseMessage accountsQuery(@RequestBody AccountInfoPageReq req, HttpServletRequest request) {
        ApiResponseMessage message = new ApiResponseMessage();
        StudyLogger.recBusinessLog("api/user/accounts/query:" + JSON.toJSONString(req));
        try {
            if (isAuthToken(iRedisService, request)) {
                req.setId(getAuthHeader(request).getUserId());
                message=bankService.findPageAccountQuery(req, message);
                message.setCode(ErrorCode.SUCCESS);
            } else {
                message.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                message.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
            }
        }  catch (Exception e) {
            message.setCode(ErrorCode.PROCESS_FAIL);
            message.setMsg(messageUtil.getMessage("msg.process.fail"));
            StudyLogger.recSysLog(e);
        }

        return message;
    }
}
