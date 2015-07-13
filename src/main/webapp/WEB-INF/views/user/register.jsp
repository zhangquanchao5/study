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
    <jsp:include page="../common/commonCss.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login/login.css">
</head>

<body style="background:#f8f8f8">
<div class="home_top">
    <jsp:include page="../head.jsp"/>
</div>
<div style="clear: both;height: 0px;font-size: 0"></div>
<div class="h_t_search">
    <jsp:include page="../menu.jsp"/>
</div>
<div class="login_height">

    <div class="login_top">
        <p>用户登录</p>
    </div>

    <div class="login_left">
        <img src="${pageContext.request.contextPath}/resources/images/longin_1.jpg" style="margin:212px 0 0 40px;">
    </div>

    <div class="login_right">
        <div class="login_right-top">
            <a href="${pageContext.request.contextPath}/login" class="login_2">登录</a> <a href="${pageContext.request.contextPath}/user/register" class="login_1">&nbsp; |&nbsp;注册</a>
        </div>
        <form id="registerForm" method="post" autocomplete="off" data-validator-option="{theme:'yellow_top'}">
        <ul>
            <li>
                <div class="login_right_in1">用 户 名：</div>
                <div class="login_right_in2"><input type="text" name="userName" id="userName" placeholder="输入用户名" data-rule="required;length[3~15]" data-msg-length="用户名3到15位"></div>
            </li>
            <li style="margin-top:10px">
                <div class="login_right_in1">用户类型：</div>
                <div style="font-size: small">
                    <fieldset>
                    <input type="radio" name="source" value="1" data-rule="checked"  data-msg-checked="请选择注册用户类型">&nbsp;系统&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="source" value="0" >&nbsp;平台
                    </fieldset>
                </div>
            </li>
            <li style="margin-top:10px">
                <div class="login_right_in1">手机号码：</div>
                <div class="login_right_in2"><input type="text" name="mobile" id="mobile" placeholder="输入手机号码" data-rule="手机号码:required;mobile"></div>
            </li>
            <li style="margin-top:10px">
                <div class="login_right_in1">密码：</div>
                <div class="login_right_in2"><input type="text" name="password" id="password" placeholder="输入密码" data-rule="密码:required;length[6~15]" data-msg-length="密码6到15位"></div>
            </li>
            <li style="margin-top:10px">
                <div class="login_right_in1">确认密码：</div>
                <div class="login_right_in2"><input type="text"  name="againPwd" id="againPwd"  placeholder="输入确认密码" data-rule="确认密码:required;length[6~15];match(password)" data-msg-length="确认密码6到15位"></div>
            </li>
            <li style="margin-top:10px">
                <div class="login_right_in1">验证码：</div>
                <div class="login_right_in2"><input type="text"  name="valCode" id="valCode" data-rule="验证码:required;length[6];"  data-msg-length="验证码6位" placeholder="输入验证码" style="width:200px"></div>
                <div><input type="button" style="margin-left:20px; width:80px; height:30px" onclick="getCode()" value="获取验证码">
                </div>
            </li>
            <li style="margin-top:10px">
                <div><input name="agree" type="checkbox" value="" data-rule="checked" style="margin-left:80px"  data-msg-checked="请同意服务协议"></div>
                <div style="margin-left:5px; line-height:36px">我已阅读并同意遵守 《有你学用户服务协议》</div>
            </li>
            <li style="margin-top:10px;margin-left:80px">
                <div>
                    <div style="width: 317px;height: 40px"><button id="registerUp" type="button" class="btn btn-success btn-block" >注册</button></div>
                </div>
            </li>

        </ul>
        </form>
    </div>

</div>

<div class="bottom">
    <jsp:include page="../footer.jsp"/>
</div>

<jsp:include page="../common/commonJS.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/user/register.js"></script>

</body>
</html>