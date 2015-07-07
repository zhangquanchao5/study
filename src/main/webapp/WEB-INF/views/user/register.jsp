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
        <ul>
            <li>
                <div class="login_right_in1">用 户 名：</div>
                <div class="login_right_in2"><input type="text" value="请输入用户名，2-18位中英文、数字或下划线！"></div>
            </li>
            <li style="margin-top:10px">
                <div class="login_right_in1">用户类型：</div>
                <div class="login_right_in2"><select>
                    <option>我是用户</option>
                    <option>我是机构</option>
                </select></div>
            </li>
            <li style="margin-top:10px">
                <div class="login_right_in1">手机号码：</div>
                <div class="login_right_in2"><input type="text" value="请输入正确格式的手机号码"></div>
            </li>
            <li style="margin-top:10px">
                <div class="login_right_in1">密码：</div>
                <div class="login_right_in2"><input type="text" value="请输入6-16位密码，区分大小写，不能使用空格"></div>
            </li>
            <li style="margin-top:10px">
                <div class="login_right_in1">确认密码：</div>
                <div class="login_right_in2"><input type="text" value="请重复输入密码"></div>
            </li>
            <li style="margin-top:10px">
                <div class="login_right_in1">验证码：</div>
                <div class="login_right_in2"><input type="text" value="" style="width:200px"></div>
                <div><input type="button" style="margin-left:20px; width:80px; height:30px" value="获取验证码">
                </div>
            </li>
            <li style="margin-top:10px">
                <div><input name="" type="checkbox" value="" style="margin-left:80px"></div>
                <div style="margin-left:5px; line-height:36px">我已阅读并同意遵守 《有你学用户服务协议》</div>
            </li>
            <li style="margin-top:10px">
                <div><a href="#" style="margin-left:80px"><img
                        src="${pageContext.request.contextPath}/resources/images/button.jpg"></a></div>
            </li>

        </ul>

    </div>

</div>

<div class="bottom">
    <jsp:include page="../footer.jsp"/>
</div>

<jsp:include page="../common/commonJS.jsp"/>
</body>
</html>