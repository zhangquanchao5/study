package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.EntityCode;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.code.SplitCode;
import com.study.common.StringUtil;
import com.study.common.apibean.response.CommonResponse;
import com.study.common.csv.CSVUtils;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;
import com.study.service.IUserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huichao on 2015/9/1.
 */
@Controller
@RequestMapping("/csv")
public class CSVController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String up(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response, Model uiModel) {
        //机构名称,登录名,手机号---------导入的都是机构，手机号不做必要校验
        try {
            if (!file.isEmpty()) {
                 if(!CSVUtils.codeString(file.getInputStream()).equalsIgnoreCase("utf-8")){
                     uiModel.addAttribute("error", "文件不是UTF8编码");
                 }else{
                     if (file.getOriginalFilename().split(SplitCode.SPLIT_DIAN)[file.getOriginalFilename().split(SplitCode.SPLIT_DIAN).length - 1].equalsIgnoreCase("csv")) {
                         List<String> list = CSVUtils.importCsv(file.getInputStream());
                         List<String> ruleErrorList = new ArrayList<String>();
                         List<String> successList = new ArrayList<String>();
                         List<String> existsList = new ArrayList<String>();
                         List<String> dbErrorList = new ArrayList<String>();
                         if (list != null && list.size() > 0) {
                             uiModel.addAttribute("count", list.size());
                             for (String org : list) {
                                 String[] info = org.split(SplitCode.SPLIT_DOUHAO);
                                 if (info.length == 3 || info.length == 2) {
                                     UserInfo userInfo = null;
                                     if (!StringUtil.isEmpty(info[1].trim())) {
                                         userInfo = iUserService.findLoad(info[1].trim(),null);
                                     }
                                     if (userInfo != null) {
                                         existsList.add(org + "[登录名存在]");
                                     } else {
                                         if (info.length == 3 && !StringUtil.isEmpty(info[2].trim())) {
                                             userInfo = iUserService.findLoad(info[2].trim(),null);
                                         }
                                         if (userInfo != null) {
                                             existsList.add(org + "[手机号存在]");
                                         } else {
                                             //添加到数据库
                                             userInfo = new UserInfo();
                                             userInfo.setUserName(info[1].trim());
                                             userInfo.setName(info[0].trim());
                                             if (info.length == 3 && !StringUtil.isEmpty(info[2].trim())) {
                                                 userInfo.setMobile(info[2].trim());
                                             }
                                             userInfo.setSource((byte) 1);
                                             userInfo.setPassword(StringUtil.getMD5Str("000000"));
                                             userInfo.setCreateTime(new Date());
                                             userInfo.setStatus(EntityCode.USER_VALIDATE);

                                             UserInfoFrom userInfoFrom = new UserInfoFrom();
                                             userInfoFrom.setFrom(EntityCode.USER_FROM_MOBILE);
                                             try {
                                                 iUserService.saveUserInfo(userInfo, userInfoFrom);
                                                 successList.add(org);
                                             } catch (Exception e) {
                                                 dbErrorList.add(org);
                                             }

                                         }
                                     }

                                 } else {
                                     ruleErrorList.add(org);
                                 }
                             }
                             uiModel.addAttribute("error", "文件正常");
                             uiModel.addAttribute("dbErrorList", dbErrorList);
                             uiModel.addAttribute("existsList", existsList);
                             uiModel.addAttribute("ruleErrorList", ruleErrorList);
                             uiModel.addAttribute("successList", successList);
                         }
                     }  else {
                         uiModel.addAttribute("error", "文件类型错误");
                     }
                 }

            } else {
                uiModel.addAttribute("error", "文件内容为空");
            }
        } catch (Exception e) {
            uiModel.addAttribute("error", "出现系统错误");
        }
        return "importResult";
    }

}
