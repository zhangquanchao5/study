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
    <jsp:include page="common/commonCss.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login/login.css">
</head>

<body style="background:#f8f8f8">
<div class="home_top">
    <jsp:include page="head.jsp"/>
</div>
<div style="clear: both;height: 0px;font-size: 0"></div>
<div class="h_t_search">
    <jsp:include page="menu.jsp"/>
</div>

<div class="login_height">

    <div class="login_top">
        <p>用户登录</p>
    </div>

    <div class="login_left">
        <img src="${pageContext.request.contextPath}/resources/images/longin_1.jpg" style="margin:103px 0 0 40px;">
    </div>

    <div class="login_right">
        <div class="login_right-top">
            <a href="login.jsp" class="login_1">登录&nbsp; |&nbsp;</a> <a href="${pageContext.request.contextPath}/user/register" class="login_2">注册</a>
        </div>
        <ul>
            <li>
                <div class="login_right_in1">用 户 名：</div>
                <div class="login_right_in2"><input name="" type="text"></div>
            </li>
            <li style="margin-top:20px">
                <div class="login_right_in1">密码：</div>
                <div class="login_right_in2"><input name="" type="text"></div>
            </li>
            <li style="margin-top:20px">
                <div><input name="" type="checkbox" value="" style="margin-left:80px"></div>
                <div style="margin-left:5px; line-height:36px">自动登录</div>
            </li>
            <li style="margin-top:10px">
                <div><a href="#" style="margin-left:80px"><img
                        src="${pageContext.request.contextPath}/resources/images/button.jpg"></a></div>
                <div style="margin-left:25px; line-height:36px"><a href="#" class="login_3">忘记密码？</a></div>
            </li>

        </ul>
    </div>
</div>

<div class="bottom">
    <jsp:include page="footer.jsp"/>
</div>

<jsp:include page="common/commonJS.jsp"/>
</body>
</html>