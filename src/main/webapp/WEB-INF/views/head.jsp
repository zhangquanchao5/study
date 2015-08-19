<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="h_t_cont">
    <ul class="h_t_cont_left">
        <li>城市：<a href="#">北京</a></li>
        <li class="top_left_cityFont"><a href="#">切换城市</a></li>
    </ul>
    <ul class="h_t_cont_right" id="userLogin" style="display: none">
        <c:if test="${sessionScope.user_session_info!=null}">
            <li class="service_phone">400-898-1115</li>
            <li class="top_right_nameColor"><a href="${pageContext.request.contextPath}/account/accountManagement" target="_blank">${sessionScope.user_session_info.userInfo.userName}</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">退出</a></li>
            <li><a href="http://www.unixue.com/#/myyouni.html">我的有你学</a></li>
            <li><a href="#">客户服务</a></li>
            <li><a href="#">我的收藏</a></li>
            <li><a href="http://www.unixue.com/manage">机构入口</a></li>
        </c:if>


    </ul>

    <ul class="h_t_cont_right" id="userNoLogin" style="display: none">
               <li class="service_phone">400-898-1115</li>
             <li><a href="${pageContext.request.contextPath}/login">请登录</a></li>
             <li>|</li>
             <li><a href="${pageContext.request.contextPath}/user/register">免费注册</a></li>

    </ul>
</div>

<script>
    var name = escape("SSOID");
    //读cookie属性，这将返回文档的所有cookie
    var allcookies = document.cookie;
    //查找名为name的cookie的开始位置
    name += "=";
    console.log("-----------"+allcookies+":"+name);
    var pos = allcookies.indexOf(name);
    console.log("-----------"+pos);
    if(pos>-1){
          document.getElementById("userLogin").style.display="block";
          document.getElementById("userNoLogin").style.display="none";
    }else{
          document.getElementById("userNoLogin").style.display="block";
           document.getElementById("userLogin").style.display="none";
    }
</script>