<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="top">
    <div class="top_main">
        <div class="lfloat top_left">
   <%--<c:if test="${sessionScope.user_session_info!=null}">--%>
       <%--<a href="${pageContext.request.contextPath}/account/accountManagement" target="_blank">${sessionScope.user_session_info.userInfo.userName}</a>--%>
    <%--</c:if>--%>
            <c:if test="${sessionScope.user_session_info==null}">
                <a href="${pageContext.request.contextPath}/orgLogin">请登录</a>
            </c:if>
           <span>
               <%--|</span> <span><img src="${pageContext.request.contextPath}/resources/images/icon_1.png" /></span><a href="#">消息</a> <span><img src="${pageContext.request.contextPath}/resources/images/icon_2.png" /></span><a href="#">通知</a>--%>
        </div>
        <div class="rfloat top_right">
            <ul>
                <li><a href="${basePath}">有你学首页</a></li>
     <c:if test="${sessionScope.user_session_info!=null}">
         <li><a  href="${basePath}/#/myyouni.html">我的有你学</a><!-- <img src="images/bottom.png" /> -->
             <%--<ul>--%>
                 <%--<li><a href="#"style="color: #ED7171;" class="lfloat">我的有你学<img src="${pageContext.request.contextPath}/resources/images/icon_31.png"  style="margin-left:5px"/></a></li>--%>
                 <%--<li><a href="#">我的课程</a></li>--%>
                 <%--<li><a href="#">我的关注</a></li>--%>
                 <%--<li><a href="#">我的钱包</a></li>--%>
                 <%--<li><a href="#">订单管理</a></li>--%>
                 <%--<li><a href="#">我的评价</a></li>--%>
                 <%--<li><a href="#">我要赚钱</a></li>--%>
             <%--</ul>--%>
         </li>
         <li><a href="${basePath}/myyouni.html?showListType=collectionCourses">我的收藏</a></li>

    </c:if>
                <li><a href="#">机构中心</a><!-- <img src="images/bottom.png" /> -->
                    <ul>
                        <li><a href="${basePath}/manage" style="color: #ED7171;" class="lfloat">机构登录<img src="${pageContext.request.contextPath}/resources/images/icon_31.png"  style="margin-left:5px"/></a></li>
                        <li><a href="${pageContext.request.contextPath}/user/registerOrg">机构入驻</a></li>
                        <li><a href="${basePath}/xin.html" target="_blank">入驻流程</a></li>
                    </ul>
                </li>

            </ul>
        </div>
    </div>
</div>

<%--<script>--%>
    <%--var name = escape("SSOID");--%>
    <%--//读cookie属性，这将返回文档的所有cookie--%>
    <%--var allcookies = document.cookie;--%>
    <%--//查找名为name的cookie的开始位置--%>
    <%--name += "=";--%>
    <%--console.log("-----------"+allcookies+":"+name);--%>
    <%--var pos = allcookies.indexOf(name);--%>
    <%--console.log("-----------"+pos);--%>
    <%--if(pos>-1){--%>
          <%--document.getElementById("userLogin").style.display="block";--%>
          <%--document.getElementById("userNoLogin").style.display="none";--%>
    <%--}else{--%>
          <%--document.getElementById("userNoLogin").style.display="block";--%>
           <%--document.getElementById("userLogin").style.display="none";--%>
    <%--}--%>
<%--</script>--%>