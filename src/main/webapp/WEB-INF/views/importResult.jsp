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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login/login.css">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>

<body style="background:#f8f8f8">
<div class="home_top">
    <jsp:include page="head.jsp"/>
</div>
<div style="clear: both;height: 0px;font-size: 0"></div>
<div class="h_t_search">
    <jsp:include page="menu.jsp"/>
</div>

<div class="login_height" style="font-size: 16px;">

    <div class="login_top">
        <p>用户导入结果</p>
    </div>

    <div>
        <div>文件信息：${error}</div>
        <div>总计：${count}</div>
        <div>
            <c:if test="${existsList!=null && existsList.size()>0}">
                已经存在账号:
                <table>
                <c:forEach items="${existsList}" var="item">
                   <tr><td>${item}</td></tr>
                </c:forEach>
                </table>
            </c:if>
        </div>

        <div>
            <c:if test="${dbErrorList!=null && dbErrorList.size()>0}">
                数据库发生错误:
                <table>
                    <c:forEach items="${dbErrorList}" var="item">
                        <tr><td>${item}</td></tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>

        <div>
            <c:if test="${ruleErrorList!=null && ruleErrorList.size()>0}">
                验证错误列表:
                <table>
                    <c:forEach items="${ruleErrorList}" var="item">
                        <tr><td>${item}</td></tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>

        <div>
            <c:if test="${successList!=null && successList.size()>0}">
               成功列表:${successList.size()}
                <table>
                    <c:forEach items="${successList}" var="item">
                        <tr><td>${item}</td></tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </div>


    </div>
</div>

<div class="bottom">
    <jsp:include page="footer.jsp"/>
</div>
<div class="clear"></div>
<jsp:include page="common/commonJS.jsp"/>

</body>
</html>