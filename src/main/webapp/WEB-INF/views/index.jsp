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
</head>

<body style="background:#f8f8f8">
<div class="home_top">
    <jsp:include page="head_old.jsp"/>
</div>
<div style="clear: both;height: 0px;font-size: 0"></div>
<div class="h_t_search">
    <jsp:include page="menu.jsp"/>
</div>

<div class="login_height">

  欢迎界面,登陆成功
</div>

<div class="bottom">
    <jsp:include page="footer.jsp"/>
</div>

<jsp:include page="common/commonJS.jsp"/>
</body>
</html>