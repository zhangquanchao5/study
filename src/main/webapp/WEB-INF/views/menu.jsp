<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="h_t_searchCont">
    <ul>
        <li class="logo"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/logo.png"/></a></li>
        <li><a href="${pageContext.request.contextPath}/account/accountManagement">首页</a></li>
        <li><a href="${pageContext.request.contextPath}/account/accountManagement">免费体验课</a></li>
        <li class="hotIcon"><a href="#">奖学金<img src="${pageContext.request.contextPath}/resources/images/newIcon.png" class="hotIconImg"/></a></li>
        <li class="homeSearchWraper">
            <input type="search" placeholder="搜索机构或课程名称" class="homeSearch"/>
            <img src="${pageContext.request.contextPath}/resources/images/searchIcon.png" class="homeSearchIcon"/>
        </li>
    </ul>
</div>