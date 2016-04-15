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
    <!-- start: CSS REQUIRED FOR THIS PAGE ONLY -->
    <!-- end: CSS REQUIRED FOR THIS PAGE ONLY -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.ico" />
    <meta property="qc:admins" content="353226116365610556375734541" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login/login.css">
</head>

<body >


<div class="login">
    <div class="login_main">
        <a href="http://www.unixue.com><img src="${pageContext.request.contextPath}/resources/images/logo.png" /></a>
    </div>
</div>
<div class="login_bg">
    <form   id="loginForm" method="post" autocomplete="off" data-validator-option="{theme:'yellow_top'}">
        <input type="hidden" name="gotoURL" value="${param.gotoURL}" />
        <div class="login_bg1">
        <div class="login_1 rfloat">
            <div class="login_text">机构/用户登录</div>
            <div class="login_text1 login_mag">
                <div class="login_kuang lfloat"><img src="${pageContext.request.contextPath}/resources/images/icon_10.png" /></div>
                 <input name="userName"  id="userName"  placeholder="输入用户名\手机号码\邮箱" type="text" data-rule="required;" >
            </div>
            <div class="login_text1 login_mag1">
                <div class="login_kuang lfloat"><img src="${pageContext.request.contextPath}/resources/images/icon_11.png" /></div>
                <input name="password" id="password"  placeholder="输入密码" type="password" data-rule="required;length[6~15]"   data-msg-length="密码是6到15位">
            </div>
            <div class="wangji login_mag">
                <a  href="${pageContext.request.contextPath}/user/forget" class="lfloat login_col">忘记密码？</a>
                <a  href="${pageContext.request.contextPath}/user/registerOrg" class="rfloat login_co2">立即注册> </a>
            </div>
            <div style="clear: both;"></div>
            <div class="login_mag1"   ><a href="#" id="loginUp" ><img  src="${pageContext.request.contextPath}/resources/images/pic-3.png" /></a></div>
            <div class="login_mag2"><span class="lfloat login_left">快捷登录：</span>
                <a href="#" onclick="redirectQq()" class="lfloat login_left"><img src="${pageContext.request.contextPath}/resources/images/icon_12.png" /></a>
                <span class="lfloat login_left">qq登录</span></div>
        </div>
    </div>
    </form>
</div>

<div style="clear: both;"></div>
<div class="login_bottom">
    <jsp:include page="footer_new.jsp"/>
</div>

<jsp:include page="common/commonJS.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/user/login.js"></script>
<script>
    function redirectQq(){
       // var redirect="http://cas.unixue.com/study/user/qqloginUp";
        window.location.href="${pageContext.request.contextPath}/user/qqlogin";
    }
</script>
</body>
</html>