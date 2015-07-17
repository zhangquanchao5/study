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
<div class="wraper">
    <div class="courseManage">
        <ul class="c_m_title border">
            <li class="hover">账户管理</li>
            <li>消息管理</li>
        </ul>
        <div class="a_m_main clearfix">
            <div class="a_m_main_top border">
                <h3 class="a_m_main_topTitle borderBottom">您的基础信息</h3>
                <div class="a_m_main_topMain">
                    <p class="a_m_main_topMainRight">
                        <a href="#">添加邮箱</a>
                        <a href="#">修改手机</a>
                    </p>
                    <dl class="a_m_main_topMainLeft left">
                        <dt><img src="${pageContext.request.contextPath}/resources/images/imgs07.jpg"/></dt>
                        <dd class="blueColor">${sessionScope.user_session_info.userInfo.userName}</dd>
                        <dd>登录邮箱：您尚未设置登录邮箱</dd>
                        <dd>手机号码： ${sessionScope.user_session_info.userInfo.mobile}</dd>
                        <dd>上次登录： 2015年06月01日 11:52:09<span class="blueColor">(不是您登录的？请点击这里)</span></dd>
                    </dl>
                </div>
            </div>
           <div class="a_m_main_bottom border clearfix">
               <h3 class="a_m_main_topTitle borderBottom height49">您的安全设置</h3>
               <div class="a_m_schedule">
                   <div class="scheduleState">
                       <p class="right">完成 <a class="redColor">密保设置</a> ，提升账户安全。</p>
                       <p class="left">安全等级： <span class="redColor">中</span></p>
                   </div>
                   <div class="a_m_setList">
                       <h3><a href="${pageContext.request.contextPath}/account/loginpassword" target="_blank" class="right blueColor">修改</a><span class="a_m_bg_01">已设置登录密码</span></h3>
                       <p>安全性高的密码可以使账号更安全。建议您定期更换密码，且设置一个包含数字和字母，并长度超过6位以上的密码。</p>
                   </div>
                   <div class="a_m_setList">
                       <h3><a href="#" class="right blueColor">查看</a><span class="a_m_bg_01">已完成身份认证</span></h3>
                       <p>用于提升账号的安全性和信任级别。认证后的有卖家记录的账号不能修改认证信息。</p>
                   </div>
                   <div class="a_m_setList">
                       <h3><a href="${pageContext.request.contextPath}/account/paymentpassword"  target="_blank" class="right blueColor">修改</a><span class="a_m_bg_01">已设置支付密码</span></h3>
                       <p>安全性高的密码可以使账号更安全。建议您定期更换密码，且设置一个包含数字和字母，并长度超过6位以上
                           的密码。</p>
                   </div>
                   <div class="a_m_setList">
                       <h3><a href="${pageContext.request.contextPath}/account/modifyphoneNum"  target="_blank" class="right blueColor">设置</a><span class="a_m_bg_01 a_m_bg_02 redColor">未设置绑定问题</span></h3>
                       <p>是您找回登录密码的方式之一。建议您设置一个容易记住，且最不容易被他人获取的问题及答案，更有效保障
                           您的密码安全</p>
                   </div>
                   <div class="a_m_setList">
                       <h3><a href="${pageContext.request.contextPath}/account/securityTouble"  target="_blank" class="right blueColor">修改</a><span class="a_m_bg_01">已设定绑定手机</span></h3>
                       <p>绑定手机后，您即可享受丰富的手机服务，如手机登录、手机找回密码、开通手机动态密码等。</p>
                   </div>
               </div>
           </div>
        </div>
    </div>
</div>
</body>
</html>