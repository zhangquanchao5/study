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
<div  id="step1"  class="kuang">
    <div class="kuang-top">
        <div class="kuang-top_text">找回密码</div>
    </div>
    <div class="kuang-tex11"><span class="kuandu">找回方式:</span><spaN><select name="findType" id="findType" class="xl">
        <option value="1">邮箱找回</option>
        <option value="2">手机找回</option>
    </select></span>
    </div>
    <div class="kuang-tex13"><span class="kuandu" style="margin-left: 20px">验证码:</span><span style="margin-left: 20px" >
         <input id="inputCode" type="text"/></span>

        <span class="mycode" id="code" style="margin-left: 20px">
        </span>
        <%--<img src="${pageContext.request.contextPath}/resources/images/yanzhengma.png" /></span>--%>
    </div>
    <div class="kuang-btn1"><a href="#" id="nextStep1"><img src="${pageContext.request.contextPath}/resources/images/bunttom1.png" /></a></div>
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
    <div class="kuang-btn1"><a id="nextStep2" href="#"><img src="${pageContext.request.contextPath}/resources/images/bunttom1.png" /></a></div>
</div>


<div id="step3" class="kuang">
    <form   id="pwdForm" method="post" autocomplete="off" data-validator-option="{theme:'yellow_top'}">
    <div class="kuang-top">
        <div class="kuang-top_text">登录密码重置</div>
    </div>
    <div class="kuang-tex4"><span class="kuandu">密码:</span><spaN><input type="password" name="password" id="password" placeholder="输入密码"  data-tip="密码6到15位" data-rule="密码:required;length[6~15]" data-msg-length="密码6到15位" /></span>
    </div>

    <div class="kuang-tex3" style="float:left"><span class="kuandu">确认密码:</span><spaN><input type="password"  name="againPwd" id="againPwd"  placeholder="输入确认密码"   data-tip="和密码一样" data-rule="确认密码:required;length[6~15];match(password)" data-msg-length="确认密码6到15位"/></span></div>
    <div class="kuang-btn1" ><a id="nextStep3" href="#"><img src="${pageContext.request.contextPath}/resources/images/bunttom.png" /></a></div>
  </form>
</div>

<div  id="step5" class="kuang">
    <form   id="mailForm" method="post" autocomplete="off" data-validator-option="{theme:'yellow_top'}">
    <div class="kuang-top">
        <div class="kuang-top_text">企业邮箱找回</div>
    </div>
    <div class="kuang-tex4"><span class="kuandu">注册邮箱:</span><spaN><input name="userMail" id="userMail" type="text" placeholder="输入企业邮箱"   data-tip="企业邮箱必需输入" data-rule="企业邮箱:required;userMail;remote[${pageContext.request.contextPath}/user/registerValidate]"/></span>
    </div>

    <div class="kuang-btn1"><a href="#" id="nextStep4"><img src="${pageContext.request.contextPath}/resources/images/bunttom1.png" /></a></div>
    </form>
</div>

<div  id="step6"  class="kuang">
    <div class="kuang-top">
        <div class="kuang-top_text">邮箱重置</div>
    </div>
    <div class="x15">
        <p class="x17">密码重设链接邮件已发送到您的邮箱</p><p class="x16"><a href="#">您的邮箱：<span id="emailContent">111</span></a></p><p class="x17">请注意查收，为了保证您的账户安全，请及时修改密码</p></div>
   </div>
</div>


<div id="step4" class="kuang">
    <div class="goxi">恭喜您，修改密码成功！</div>
</div>

<div class="bottom">
    <jsp:include page="../footer_new.jsp"/>
</div>
<div class="clear"></div>
<jsp:include page="../common/commonJS.jsp"/>
<script>
    var baseSrc="${pageContext.request.contextPath}/resources/";
</script>
<script src="${pageContext.request.contextPath}/resources/js/js.KinerCode.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/code.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/user/forgetNew.js"></script>


</body>
</html>