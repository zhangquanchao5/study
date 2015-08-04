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
        <h3 class="p_p_title borderBottom">添加安保问题完成</h3>
        <p class="s_i_mainTitle">安全保护问题将作为重要的身份验证，请确认</p>
        <form>
            <table width="92%" align="center" border="1" cellpadding="0" cellspacing="0" class="s_i_table">
                <tbody>
                    <tr>
                        <td>我的生日是？</td>
                        <td>1990年09月18日</td>
                    </tr>
                    <tr>
                        <td>我的最爱的是？</td>
                        <td>吃，喝，玩，乐</td>
                    </tr>
                </tbody>
            </table>
            <p class="s_i_bottom_btn"><input type="submit" value="提交" class="resetInput p_p_subBtn"/><input type="button" value="返回修改" class="resetInput s_i_subBtn"/></p>
        </form>
    </div>
</div>
<div class="bottom">
    <jsp:include page="../footer.jsp"/>
</div>
<div class="clear"></div>
</body>
</html>