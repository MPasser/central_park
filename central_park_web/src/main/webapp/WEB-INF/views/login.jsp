<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 宅大颠
  Date: 2020/8/1
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Central Park - Login</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/css/user-config.css">
    <script type="text/javascript" src="${ctx}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/md5.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/js/user-config.js"></script>

</head>
<body>
<!-- main container start -->
<div class="container text-center main-container">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3>登录至中央公园</h3>
            <span>a simple chatroom</span>
        </div>


        <div class="panel-body">

            <!-- login form start -->
            <form class="form-horizontal" action="${ctx}/loginUser" method="post" onsubmit="return checkLoginForm()">

                <div class="form-group">
                    <label for="username" class="control-label col-xs-3">用户名:</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control " id="username" name="username" placeholder="请输入用户名" onblur="checkLoginUsername()">
                    </div>
                    <div class="col-xs-3 form-group-info" id="username-form-info">
                    </div>
                </div>


                <div class="form-group">
                    <label for="password" class="control-label col-xs-3">用户名:</label>
                    <div class="col-xs-6">
                        <input type="password" class="form-control " id="password" name="password" placeholder="请输入密码" onblur="checkLoginPassword()">
                    </div>
                    <div class="col-xs-3 form-group-info" id="password-form-info">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-3">
                        <!-- 占位 -->
                    </div>
                    <div class="col-sm-3">
                        <button type="button" class="btn btn-info btn-block col-sm-4" id="btnSignUp" onclick="jumpToRegister()">注&nbsp;&nbsp;册
                        </button>
                    </div>
                    <div class="col-sm-3">
                        <button type="submit" class="btn btn-primary btn-block col-sm-4" onclick="">登&nbsp;&nbsp;录</button>
                    </div>


                </div>


            </form>
            <!-- login form end -->
        </div>
    </div>
</div>
<!-- main container end -->


<!-- footer container start -->
<div class="container">
    <footer class="text-muted">
        <p>Copyright &copy; 2020 MPasser</p>
    </footer>
</div>
<!-- footer container end -->
</body>
</html>
