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
    <jsp:include page="../head.jsp"/>
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
        <h3 class="p_p_title borderBottom">修改绑定手机</h3>
        <forme>
            <ul class="p_p_form">
                <li><input type="text"/><span class="redColor m_p_n_error">原手机号码错误</span><label><span class="redColor">*</span>新手机：</label></li>
                <li><input type="text"/><span class="redColor m_p_n_error">请输入正确的手机号码</span><label><span class="redColor">*</span>旧手机：</label></li>
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