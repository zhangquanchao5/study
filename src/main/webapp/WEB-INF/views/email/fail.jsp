<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: zqc
  Date: 2015/1/22
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>登录找回密码</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style_new.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/validator/jquery.validator.css">
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css">--%>

    <!-- start: CSS REQUIRED FOR THIS PAGE ONLY -->
    <!-- end: CSS REQUIRED FOR THIS PAGE ONLY -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/css.css">
    <style type="text/css">
        <!--
        .STYLE1 {color: #0593d3}
        -->
         .mycode{
             display: inline-block;
             width: 80px;
             height: 40px;
             vertical-align: middle;
         }

    </style>
</head>

<body style="background:#f8f8f8">
<div class="main" style="padding-bottom:20px">
    <jsp:include page="../head.jsp"/>
</div>

<div id="step4" class="kuang">
    <c:if test="${code==1001}">
        <div class="goxi">邮箱验证参数失败，请重新邮件重置密码！</div>
    </c:if>
    <c:if test="${code==999}">
        <div class="goxi">邮箱重置密码出现系统错误，请重新邮件重置密码！</div>
    </c:if>
</div>

<div class="bottom">
    <jsp:include page="../footer_new.jsp"/>
</div>
<div class="clear"></div>
<jsp:include page="../common/commonJS.jsp"/>



</body>
</html>