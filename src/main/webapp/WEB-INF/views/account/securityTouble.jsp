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
        <h3 class="p_p_title borderBottom">添加安保问题</h3>
        <form>
            <ul class="s_r_form">
                <li>
                    <select name="">
                        <option value="">你猜我是谁？</option>
                        <option value="">你猜我是谁？</option>
                        <option value="">你猜我是谁？</option>
                    </select>
                    <label>问题一：</label>
                </li>
                <li><input type="text"/></li>
                <li>
                    <select name="">
                        <option value="">你猜我是谁？</option>
                        <option value="">你猜我是谁？</option>
                        <option value="">你猜我是谁？</option>
                    </select>
                    <label>问题二：</label>
                </li>
                <li><input type="text"/></li>
            </ul>
            <input type="submit" value="提交" class="resetInput p_p_subBtn"/>
        </form>
    </div>
</div>
<div class="bottom">
    <jsp:include page="../footer.jsp"/>
</div>
<div class="clear"></div>
</body>
</html>