<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 宅大颠
  Date: 2020/8/1
  Time: 23:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Central Park - Register</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/css/user-config.css">
    <script type="text/javascript" src="${ctx}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/js/user-config.js"></script>
</head>
<body>

<!-- main container start -->
<div class="container text-center main-container">
    <div class="panel panel-info">
        <div class="panel-heading">
            <h3>注册</h3>
            <span>sign up for the central park</span>
        </div>


        <div class="panel-body">

            <!-- login form start -->
            <form class="form-horizontal" action="${ctx}/regist" method="post">

                <div class="form-group">
                    <label for="username" class="control-label col-xs-3">用户名:</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control " id="username" placeholder="请输入用户名">
                    </div>
                </div>



                <div class="form-group">
                    <label for="password" class="control-label col-xs-3">密码:</label>
                    <div class="col-xs-6">
                        <input type="password" class="form-control " id="password" placeholder="请输入密码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="re-password" class="control-label col-xs-3">重复密码:</label>
                    <div class="col-xs-6">
                        <input type="password" class="form-control " id="re-password" placeholder="请输入密码">
                    </div>
                </div>


                <div class="form-group">
                    <label for="email" class="control-label col-xs-3">邮箱:</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control " id="email" placeholder="请输入邮箱">
                    </div>
                </div>

                <div class="form-group ">
                    <label for="portrait" class="control-label col-xs-3">头像:</label>
                    <div class="col-xs-2">
                        <input type="file" class="form-control " id="portrait">
                    </div>
                    <label for="portrait-preview" class="control-label col-xs-2">头像预览:</label>

                    <div class="col-xs-2">
                        <img src="${ctx}/images/default-portrait.jpg" id="portrait-preview">
                    </div>
                </div>

                <br>
                <br>

                <div class="form-group">
                    <div class="col-sm-6">
                        <!-- 占位 -->
                    </div>

                    <div class="col-sm-3">
                        <button type="button" class="btn btn-primary btn-block col-sm-4" id="btnSignUp">注&nbsp;&nbsp;册</button>
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
