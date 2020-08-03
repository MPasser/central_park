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
            <form class="form-horizontal" action="${ctx}/registNewUser" method="post" enctype="multipart/form-data">

                <div class="form-group">
                    <label for="username" class="control-label col-xs-3">用户名:</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control " id="username" name="username" placeholder="请输入用户名"
                               onblur="checkUsername()">
                    </div>
                    <div class="col-xs-3 form-group-info" id="username-form-info">
                    </div>
                </div>


                <div class="form-group">
                    <label for="password" class="control-label col-xs-3">密码:</label>
                    <div class="col-xs-6">
                        <input type="password" class="form-control " id="password" name="password" placeholder="请输入密码 (6-20个字符)"
                               onblur="checkPassword()">
                    </div>
                    <div class="col-xs-3 form-group-info" id="password-form-info">
                    </div>
                </div>
                <div class="form-group">
                    <label for="repassword" class="control-label col-xs-3">重复密码:</label>
                    <div class="col-xs-6">
                        <input type="password" class="form-control " id="repassword" placeholder="请重复密码"
                               onblur="checkRepassword()">
                    </div>
                    <div class="col-xs-3 form-group-info" id="repassword-form-info">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-xs-3">性别:</label>
                    <div class="col-xs-6 text-left">
                        <label>
                            男<input type="radio" name="gender" value="true"/>
                        </label>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <label>
                            女<input type="radio" name="gender" value="false"/>
                        </label>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <label>
                            保密<input type="radio" name="gender" value=""/>
                        </label>

                    </div>
                    <div class="col-xs-3 form-group-info" id="gender-form-info">
                        <!-- 暂时用不上 -->
                    </div>
                </div>


                <div class="form-group">
                    <label for="email" class="control-label col-xs-3">邮箱:</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control " id="email" name="email" placeholder="请输入邮箱 (可选填)"
                               onblur="checkEmail()">
                    </div>
                    <div class="col-xs-3 form-group-info" id="email-form-info">
                    </div>
                </div>

                <div class="form-group ">
                    <div class="col-xs-2">
                        <!-- 占位 -->
                    </div>
                    <label for="portrait" class="control-label col-xs-1">（不选即使用默认）头像:</label>
                    <div class="col-xs-2">
                        <input type="file" class="form-control " id="portrait" name="portrait" />
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
                        <button type="submit" class="btn btn-primary btn-block col-sm-4" id="btnSignUp">注&nbsp;&nbsp;册
                        </button>
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
