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

    <form class="login_right"  id="loginForm" method="post" autocomplete="off" data-validator-option="{theme:'yellow_top'}">
        <div class="login_right-top">
            <a href="login.jsp" class="login_1">登录&nbsp; |&nbsp;</a> <a href="${pageContext.request.contextPath}/user/register" class="login_2">注册</a>
        </div>
        <ul>
            <li>
                <div class="login_right_in1">用 户 名：</div>
                <div class="login_right_in2"><input name="userName"  id="userName"  placeholder="输入用户名" type="text" data-rule="required;" ></div>
            </li>
            <li style="margin-top:20px">
                <div class="login_right_in1">密码：</div>
                <div class="login_right_in2"><input name="password" id="password"  placeholder="输入密码" type="text" data-rule="required;length[6~15]"   data-msg-length="密码是6到15位"></div>
            </li>
            <%--<li style="margin-top:20px">--%>
                <%--<div><input name="" type="checkbox" value="" style="margin-left:80px"></div>--%>
                <%--<div style="margin-left:5px; line-height:36px">自动登录</div>--%>
            <%--</li>--%>
            <li style="margin-top:25px;margin-left:80px">
                <div style="width: 317px;height: 40px"><button id="loginUp" type="button" class="btn btn-success btn-block" >登录</button></div>
                <div style="margin-left:25px; line-height:36px"><a href="${pageContext.request.contextPath}/user/forget" class="login_3">忘记密码？</a></div>
            </li>

        </ul>
        </form>
    </div>
</div>

<div class="bottom">
    <jsp:include page="footer.jsp"/>
</div>

<jsp:include page="common/commonJS.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/user/login.js"></script>

</body>
</html>