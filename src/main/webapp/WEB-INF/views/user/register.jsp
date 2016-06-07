<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
    <title>有你学</title>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="" name="description" />
    <meta content="" name="author" />
    <!-- end: META -->
    <!-- start: MAIN CSS -->

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style_new.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/validator/jquery.validator.css">
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css">--%>

    <!-- start: CSS REQUIRED FOR THIS PAGE ONLY -->
    <!-- end: CSS REQUIRED FOR THIS PAGE ONLY -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.ico" />
</head>

<body style="background:#fff">
<div class="main" style="padding-bottom:20px">
    <jsp:include page="../head.jsp"/>
    <div class="logo_center">
        <div class="logo_center_left lfloat" >
            <a href="${basePath}" class="logo_center_left_1"><img src="${pageContext.request.contextPath}/resources/images/logo.png" /></a><span class="logo_center_left_sp"><img src="${pageContext.request.contextPath}/resources/images/shu.png" /></span>
            <a href="${pageContext.request.contextPath}/user/register" class="logo_center_left_3">个人注册</a>
            <span class="lfloat logo_center_left_2">|</span>
            <a href="${pageContext.request.contextPath}/user/registerOrg" class="logo_center_left_2">机构入驻</a>
        </div>
    </div>
</div>
<%--<div style="clear: both;height: 0px;font-size: 0"></div>--%>
<%--<div class="h_t_search">--%>
    <%--<jsp:include page="../menu.jsp"/>--%>
<%--</div>--%>
<%--<div class="login_height">--%>

    <%--<div class="login_top">--%>
        <%--<p>用户登录</p>--%>
    <%--</div>--%>

    <%--<div class="login_left">--%>
        <%--<img src="${pageContext.request.contextPath}/resources/images/longin_1.jpg" style="margin:212px 0 0 40px;">--%>
    <%--</div>--%>

    <%--<div class="login_right">--%>
        <%--<div class="login_right-top">--%>
            <%--<a href="${pageContext.request.contextPath}/login" class="login_2">登录</a> <a href="${pageContext.request.contextPath}/user/register" class="login_1">&nbsp; |&nbsp;注册</a>--%>
        <%--</div>--%>
        <%--<form id="registerForm" method="post" autocomplete="off" data-validator-option="{theme:'yellow_top'}">--%>
        <%--<ul>--%>
            <%--<li>--%>
                <%--<div class="login_right_in1">用 户 名：</div>--%>
                <%--<div class="login_right_in2"><input type="text" name="userName" id="userName" placeholder="输入用户名" data-rule="required;length[3~15];remote[${pageContext.request.contextPath}/user/registerValidate]" data-tip="用户名3到15位" data-msg-length="用户名3到15位"></div>--%>
    <%--</li>--%>
            <%--<li style="margin-top:10px">--%>
                <%--<div class="login_right_in1">用户类型：</div>--%>
                <%--<div style="font-size: small">--%>
                    <%--<fieldset>--%>
                    <%--<input type="radio" name="source" value="1" data-rule="checked"  checked data-msg-checked="请选择注册用户类型">&nbsp;机构&nbsp;&nbsp;&nbsp;&nbsp;--%>
                    <%--<input type="radio" name="source" value="0" >&nbsp;用户--%>
                    <%--</fieldset>--%>
                <%--</div>--%>
            <%--</li>--%>
            <%--<li style="margin-top:10px">--%>
                <%--<div class="login_right_in1">手机号码：</div>--%>
                <%--<div class="login_right_in2"><input type="text" name="mobile" id="mobile" placeholder="输入手机号码"   data-tip="手机号码必需输入" data-rule="手机号码:required;mobile;remote[${pageContext.request.contextPath}/user/registerValidate]" onblur="mobileAjax();"></div>--%>
            <%--</li>--%>
            <%--<li style="margin-top:10px">--%>
                <%--<div class="login_right_in1">密码：</div>--%>
                <%--<div class="login_right_in2"><input type="password" name="password" id="password" placeholder="输入密码"  data-tip="密码6到15位" data-rule="密码:required;length[6~15]" data-msg-length="密码6到15位"></div>--%>
            <%--</li>--%>
            <%--<li style="margin-top:10px">--%>
                <%--<div class="login_right_in1">确认密码：</div>--%>
                <%--<div class="login_right_in2"><input type="password"  name="againPwd" id="againPwd"  placeholder="输入确认密码"   data-tip="和密码一样" data-rule="确认密码:required;length[6~15];match(password)" data-msg-length="确认密码6到15位"></div>--%>
            <%--</li>--%>
            <%--<li style="margin-top:10px">--%>
                <%--<div class="login_right_in1">验证码：</div>--%>
                <%--<div class="login_right_in2"><input type="text"  name="valCode" id="valCode" data-rule="验证码:required;length[6];" maxlength="6" data-tip="输入六位验证码" data-msg-length="验证码6位" placeholder="输入验证码" style="width:200px"></div>--%>
                <%--<div><button id="sendMS" type="button" style="margin-left:20px; width:80px; height:30px" disabled>获取验证码</button>--%>
                <%--</div>--%>
            <%--</li>--%>
            <%--<li style="margin-top:10px">--%>
                <%--<div><input name="agree" type="checkbox" value="" data-rule="checked" style="margin-left:80px"  data-msg-checked="请同意服务协议"></div>--%>
                <%--<div style="margin-left:5px; line-height:36px"><a href="${pageContext.request.contextPath}/user/introduce" target="_blank">我已阅读并同意遵守 《有你学用户服务协议》</a></div>--%>
            <%--</li>--%>
            <%--<li style="margin-top:10px;margin-left:80px">--%>
                <%--<div>--%>
                    <%--<div style="width: 317px;height: 40px"><button id="registerUp" type="button" class="btn btn-success btn-block" >注册</button></div>--%>
                <%--</div>--%>
            <%--</li>--%>

        <%--</ul>--%>
        <%--</form>--%>
    <%--</div>--%>

<%--</div>--%>
<div class="register">
    <div class="register_center">
        <form id="registerForm" method="post" autocomplete="off"   data-validator-option="{theme:'yellow_top'}">
            <input type="hidden" name="source" value="0"/>
            <input type="hidden" id="domain" name="domain" value="">

            <ul style="margin-top: 15px">
            <li>
                <label class="lfloat">手机号</label>
                 <input type="text" name="mobile" id="mobile" placeholder="输入手机号码"   data-tip="手机号码必需输入" data-rule="手机号码:required;mobile;remote[${pageContext.request.contextPath}/user/registerValidate]" onblur="mobileAjax();">
                 <a href="#"  id="sendMS" class="lfloat" ><img src="${pageContext.request.contextPath}/resources/images/pic-4.png" /></a>
                <span id="sendMSText" style="margin-left: 120px">获取验证码60秒后没有收到短信可以重新获取</span>
            </li>



            <li>
                <label class="lfloat">验证码</label>
                <input type="text"  name="valCode" id="valCode" data-rule="验证码:required;length[6];" maxlength="6" data-tip="输入六位验证码" data-msg-length="验证码6位" placeholder="输入验证码" >
                <%--<div class="lfloat"><img src="${pageContext.request.contextPath}/resources/images/icon_13.png" /></div>--%>
            </li>
            <li>
                <label class="lfloat">用户名</label>
                <input type="text" name="userName" id="userName" placeholder="输入用户名" data-rule="required;length[3~15];remote[${pageContext.request.contextPath}/user/registerValidate]" data-tip="用户名3到15位" data-msg-length="用户名3到15位">

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
            <a href="#" id="registerUp"><img src="${pageContext.request.contextPath}/resources/images/pic-5.png" /></a>
        </div>

        </form>
    </div>

</div>

<div style="clear: both;"></div>
<div class="login_bottom">
    <jsp:include page="../footer_new.jsp"/>
</div>

<jsp:include page="../common/commonJS.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/user/register.js"></script>

</body>
</html>