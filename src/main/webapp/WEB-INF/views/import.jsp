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

    <div class="login_top">
        <p>用户导入</p>
    </div>

    <div>
      <form  method="post" action="${pageContext.request.contextPath}/csv/import" enctype="multipart/form-data">

              <%--<label for="inputPassword2" class="sr-only">文件:</label>--%>
            <div>
                 <input type="file"  name="file" id="file"  style="margin-top: 50px;margin-left: 200px">

                  <button  style="margin-top: 20px;margin-left: 200px" type="submit" class="btn btn-default">导入提交</button>
            </div>

          <%--<div style="margin-top: 50px;margin-left: 200px">--%>
              <%--<input type="file" name="file" id="file"/>   <input style="margin-left: 100px" type="submit"  class="btn btn-success"  name="upload" value="导入提交"/>--%>
          <%--</div>--%>
          <%--<div style="margin-top: 50px;margin-left: 200px">--%>
              <%--<input type="submit"  class="btn btn-success"  name="upload" value="导入提交">--%>
          <%--</div>--%>

      </form>
    </div>


    </div>
</div>

<div class="bottom">
    <jsp:include page="footer.jsp"/>
</div>
<div class="clear"></div>
<jsp:include page="common/commonJS.jsp"/>
<script src="${pageContext.request.contextPath}/resources/js/user/login.js"></script>
<script>
    function redirectQq(){
       // var redirect="http://cas.unixue.com/study/user/qqloginUp";
        window.location.href="${pageContext.request.contextPath}/user/qqlogin";
    }
</script>
</body>
</html>