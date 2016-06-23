<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: zqc
  Date: 2015/1/22
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <title>轻校网-注册</title>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="" name="description" />
    <meta content="" name="author" />
    <!-- end: META -->
    <!-- start: MAIN CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style_new.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/validator/jquery.validator.css">
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css">--%>

    <!-- start: CSS REQUIRED FOR THIS PAGE ONLY -->
    <!-- end: CSS REQUIRED FOR THIS PAGE ONLY -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.ico" />
</head>

<body style="background:#fff">
<div class="main" style="padding-bottom:20px">
    <jsp:include page="head.jsp"/>
    <div class="logo_center">
        <div class="logo_center_left lfloat" >
            <c:if test="${!empty light.logo}">
            <a href="http://${domain}${shortPath}" class="logo_center_left_1"><img width="123px" height="52px" src="${light.logo}" /></a><span class="logo_center_left_sp"><img src="${pageContext.request.contextPath}/resources/images/shu.png" /></span>
           </c:if>
            <a href="" class="logo_center_left_3"  style="color:${light.color}" >轻校网用户注册</a>

        </div>
    </div>
</div>

<div class="register" style="border-top: 2px solid ${light.color}">
    <div class="register_center">
        <form id="registerForm" method="post" autocomplete="off"   data-validator-option="{theme:'yellow_top'}">
            <input type="hidden" name="source" value="0"/>
            <input type="hidden" id="domain" name="domain" value="${domain}">
            <ul style="margin-top: 15px">
            <li>
                <label class="lfloat">手机号</label>
                 <input type="text" name="mobile" id="mobile" placeholder="输入手机号码"   data-tip="手机号码必需输入" data-rule="手机号码:required;mobile;remote[${pageContext.request.contextPath}/user/registerValidate?domain=${domain}]" onblur="mobileAjax();">
                 <a href="javascript:"  class="btn btn-default" id="sendMS" class="lfloat" style="width: 137px;height: 42px;font-size: 19px;background-color: ${light.color}" >
                     获取验证码
                     <%--<img src="${pageContext.request.contextPath}/resources/images/pic-4.png" />--%>
                 </a>
                <span id="sendMSText" style="margin-left: 120px">获取验证码60秒后没有收到短信可以重新获取</span>
            </li>



            <li>
                <label class="lfloat">验证码</label>
                <input type="text"  name="valCode" id="valCode" data-rule="验证码:required;length[6];" maxlength="6" data-tip="输入六位验证码" data-msg-length="验证码6位" placeholder="输入验证码" >
            </li>

            <li>
                <label class="lfloat">密码</label>
                <input type="password" name="password" id="password" placeholder="输入密码"  data-tip="密码6到15位" data-rule="密码:required;length[6~15]" data-msg-length="密码6到15位">
                <%--<div class="lfloat"><img src="${pageContext.request.contextPath}/resources/images/icon_13.png" /></div>--%>
            </li>
            <li>
                <label class="lfloat">确认密码</label>
                <input type="password"  name="againPwd" id="againPwd"  placeholder="输入确认密码"   data-tip="和密码一样" data-rule="确认密码:required;length[6~15];match(password)" data-msg-length="确认密码6到15位">
                <%--<div class="lfloat"><img src="${pageContext.request.contextPath}/resources/images/icon_13.png" /></div>--%>
            </li>
        </ul>
        <div style="clear: both;"></div>
        <div class="register_xieyi">
            <input name="agree" type="checkbox" value="" data-rule="checked" data-msg-checked="请同意服务协议">我已阅读并同意<a href="${pageContext.request.contextPath}/user/introduce" target="_blank">《有你学用户服务协议》</a>
        </div>
        <div style="clear: both;"></div>
        <div  class="reguster_button">
            <a href="javascript:"  class="btn btn-default" id="registerUp" role="button" style="width: 204px;height: 42px;font-size: 19px;background-color: ${light.color}">
                立即注册
                <%--<img src="${pageContext.request.contextPath}/resources/images/pic-5.png" />--%>
            </a>
        </div>

        </form>
    </div>

</div>

<div style="clear: both;"></div>
<div class="login_bottom">
    <%--<jsp:include page="../footer_new.jsp"/>--%>
</div>

<jsp:include page="../common/commonJS.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/user/register.js"></script>

</body>
</html>