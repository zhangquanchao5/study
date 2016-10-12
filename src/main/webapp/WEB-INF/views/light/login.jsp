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
    <title>轻网校-登录</title>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="" name="description" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta content="" name="author" />
    <!-- end: META -->
    <!-- start: MAIN CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style_new.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/validator/jquery.validator.css">
    <!-- start: CSS REQUIRED FOR THIS PAGE ONLY -->
    <!-- end: CSS REQUIRED FOR THIS PAGE ONLY -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login/login.css">

</head>

<body >


<div class="login">
    <div class="login_main">
        <c:if test="${!empty light.logo}">
            <a href="http://${domain}${shortPath}"><img width="123px" height="52px" src="${light.logo}" /></a>
        </c:if>
    </div>
</div>
<div class="login2_bg">
    <form   id="loginForm" method="post" autocomplete="off" data-validator-option="{theme:'yellow_top'}">
        <input type="hidden" name="gotoURL" value="${param.gotoURL}" />
        <input type="hidden" name="domain" value="${domain}">
        <div class="login_bg2">
        <div class="login_1 rfloat">
            <div class="login_text" style="color:${light.color}">用户登录/教师登录</div>
            <div class="login_text1 login_mag">
                <div class="login_kuang lfloat"><img src="${pageContext.request.contextPath}/resources/images/icon_10.png" /></div>
                 <input name="userName"  id="userName"  placeholder="手机号码" type="text" data-rule="required;" >
            </div>
            <div class="login_text1 login_mag1">
                <div class="login_kuang lfloat"><img src="${pageContext.request.contextPath}/resources/images/icon_11.png" /></div>
                <input name="password" id="password"  placeholder="输入密码" type="password" data-rule="required;length[6~15]"   data-msg-length="密码是6到15位">
            </div>
            <div class="wangji login_mag">
                <a  href="${pageContext.request.contextPath}/user/${domain}/forget" class="lfloat login_col">忘记密码？</a>
                <a  href="${pageContext.request.contextPath}/user/${domain}/register" class="rfloat login_co2" style="color:${light.color}">立即注册> </a>
            </div>
            <div style="clear: both;"></div>
            <div class="login_mag1"   >
                <a href="#" class="btn btn-default" href="javascript:" role="button" id="loginUp" style="width: 303px;height: 42px;font-size: 19px;background-color: ${light.color}">
                <%--<img  src="${pageContext.request.contextPath}/resources/images/pic-3.png" />--%>登&nbsp;&nbsp;录
            </a></div>
        </div>
    </div>
    </form>
</div>

<div style="clear: both;"></div>
<div class="login_bottom">
    <%--<jsp:include page="../footer_new.jsp"/>--%>
</div>

<jsp:include page="../common/commonJS.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/user/login.js"></script>

</body>
</html>