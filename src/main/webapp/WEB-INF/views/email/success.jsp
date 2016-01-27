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




<div id="step3" class="kuang">
    <form   id="pwdForm" method="post" autocomplete="off" data-validator-option="{theme:'yellow_top'}">
        <input type="hidden" name="mobile" id="mobile" value="${mobile}">
    <div class="kuang-top">
        <div class="kuang-top_text">登录密码重置</div>
    </div>
    <div class="kuang-tex4"><span class="kuandu">密码:</span><spaN><input type="password" name="password" id="password" placeholder="输入密码"  data-tip="密码6到15位" data-rule="密码:required;length[6~15]" data-msg-length="密码6到15位" /></span>
    </div>

    <div class="kuang-tex3" style="float:left"><span class="kuandu">确认密码:</span><spaN><input type="password"  name="againPwd" id="againPwd"  placeholder="输入确认密码"   data-tip="和密码一样" data-rule="确认密码:required;length[6~15];match(password)" data-msg-length="确认密码6到15位"/></span></div>
    <div class="kuang-btn1" ><a id="nextStep3" href="#"><img src="${pageContext.request.contextPath}/resources/images/bunttom.png" /></a></div>
  </form>
</div>


<div id="step4" class="kuang">
    <div class="goxi">恭喜您，修改密码成功，请登录！</div>
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

<script >
    $(document).ready(function() {
        $("#step4").css('display', 'none');
        $("#nextStep3").click(function () {
            if ($("#password").val() == null || $("#password").val() == "") {
                alert("密码不能为空!");
                return;
            }
            if ($("#password").val() != $("#againPwd").val()) {
                alert("密码和确认密码必须一样!");
                return;
            }
            $.ajax({
                url: $contentPath + "/user/updatepwd",
                type: "POST",
                data: {"mobile": $("#mobile").val(), "password": $("#password").val()},
                timeout: 10000,
                dataType: "json",
                success: function (json) {
                    if (json.success) {

                        $("#step3").css('display', 'none');
                        $("#step4").css('display', 'block');
                    } else {
                        alert("系统异常错误!");
                    }
                }
            });
        });

    });
</script>


</body>
</html>