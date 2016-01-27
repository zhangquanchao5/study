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
</head>

<body>
<div class="home_top">
    <jsp:include page="../head_old.jsp"/>
</div>
<div style="clear: both;height: 0px;font-size: 0"></div>
<div class="h_t_search">
    <jsp:include page="../menu.jsp"/>
</div>
<div class="wraper">
    <ul class="c_m_title border">
        <li class="hover">账户管理</li>
        <li>消息管理</li>
    </ul>
    <div class="p_p_main border">
        <h3 class="p_p_title borderBottom">修改登录密码-验证身份</h3>
        <p class="m_p_verificationphone">以验证手机 <span>138****09823</span> </p>
        <forme>
            <ul class="m_p_form">
                <li><input type="text" class="m_p_formInp"/><input type="submit" value="获取短信验证码" class="resetInput m_p_msgVerCoad"/><label>请填写手机验证码：</label></li>
                <li><input type="text" class="m_p_formInp"/><span class="m_p_verificationimgs"><img src="${pageContext.request.contextPath}/resources/images/"></span><a href="">换一张</a><label>验证码：</label></li>
            </ul>
            <input type="submit" value="提交" class="resetInput p_p_subBtn"/>
        </forme>
    </div>
</div>
<div class="bottom">
    <jsp:include page="../footer.jsp"/>
</div>
<div class="clear"></div>
</body>
</html>