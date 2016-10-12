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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/password.css">
</head>

<body style="background:#f8f8f8">
<div class="home_top">
    <jsp:include page="../head_old.jsp"/>
</div>
<div style="clear: both;height: 0px;font-size: 0"></div>
<div class="h_t_search">
    <jsp:include page="../menu.jsp"/>
</div>
<div class="login_height">
    <form id="forgetForm" method="post" autocomplete="off" data-validator-option="{theme:'yellow_top'}">
    <div id="step1" class="kuang" >
        <div class="kuang-top">
            <div class="kuang-top_text">登录找回密码</div>
        </div>
        <div class="kuang-top_text1">请输入您需要找回登录密码的帐户名</div>
        <div class="kuang-tex"><span>用户名:</span><spaN><input name="userName" id="userName" type="text" placeholder="输入账户名" data-rule="required;length[3~15];remote[${pageContext.request.contextPath}/user/forgetValidate]" data-tip="用户名3到15位" data-msg-length="用户名3到15位"/></span></div>
        <div class="kuang-btn" style="width: 317px;height: 40px"><button id="stepUp1" type="button" class="btn btn-success btn-block" >提交</button></div>

    </div>

    <div id="step2" class="kuang">
        <div class="kuang-top">
            <div class="kuang-top_text">登录找回密码</div>
        </div>
        <div class="kuang-tex1"><span class="kuandu">手机号码:</span><spaN><input name="mobile" id="mobile" type="text" /></span><input id="sendMS" type="button" style="margin:4px 0 0 20px; width:100px; height:26px;" value="获取验证码"/>
        </div>

        <div class="kuang-tex2"><span class="kuandu">验证码:</span>
            <span><input  name="valCode" id="valCode" type="text" data-rule="验证码:required;length[6];" maxlength="6" data-tip="输入六位验证码" data-msg-length="验证码6位" placeholder="输入验证码"/></span>
        </div>
        <div class="kuang-btn1"><button  style="width: 317px;height: 40px" id="stepUp2" type="button" class="btn btn-success btn-block" >下一步</button></div>
    </div>

    <div id="step3" class="kuang">
        <div class="kuang-top">
            <div class="kuang-top_text">登录密码重置</div>
        </div>
        <div class="kuang-tex4"><span class="kuandu">密码:</span><spaN><input type="password" name="password" id="password" placeholder="输入密码"  data-tip="密码6到15位" data-rule="密码:required;length[6~15]" data-msg-length="密码6到15位" /></span>
        </div>

        <div class="kuang-tex3" style="float:left"><span class="kuandu">确认密码:</span><spaN><input type="password"  name="againPwd" id="againPwd"  placeholder="输入确认密码"   data-tip="和密码一样" data-rule="确认密码:required;length[6~15];match(password)" data-msg-length="确认密码6到15位"/></span></div>
        <div class="kuang-btn1" ><button id="stepUp3" style="width: 317px;height: 40px" type="button" class="btn btn-success btn-block" >提交</button></div>
    </div>

    <div id="step4" class="kuang">
        <div class="goxi">恭喜您，修改密码成功！</div>
    </div>
    </form>
</div>

<div class="bottom">
    <jsp:include page="../footer.jsp"/>
</div>
<div class="clear"></div>
<jsp:include page="../common/commonJS.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/user/forget.js"></script>

</body>
</html>