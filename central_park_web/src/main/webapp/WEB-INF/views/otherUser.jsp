<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 宅大颠
  Date: 2020/8/10
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Central Park - User Info</title>

    <link rel="stylesheet" href="${ctx}/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/css/user-config.css">
    <script type="text/javascript" src="${ctx}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>


</head>
<body>

<div class="container text-center">

    <div class="panel panel-info">
        <div class="panel-heading">
            <h3>用户信息</h3>
        </div>
        <div class="panel-body">
            <form class="form-horizontal">

                <div class="form-group">
                    <label for="user-id" class="control-label col-xs-4">id:</label>
                    <div class="col-xs-6 text-left">
                        <p id="user-id">${userInfo.id}</p>
                    </div>
                </div>
                <br>


                <div class="form-group">
                    <label for="username" class="control-label col-xs-4">用户名:</label>
                    <div class="col-xs-6 text-left">
                        <p id="username">${userInfo.username}</p>
                    </div>
                </div>
                <br>



                <div class="form-group">
                    <label class="control-label col-xs-4">性别:</label>
                    <div class="col-xs-6 text-left">
                        <c:if test="${userInfo.gender}"><p>男</p></c:if>
                        <c:if test="${!userInfo.gender}"><p>女</p></c:if>
                        <c:if test="${empty userInfo.gender}"><p>保密</p></c:if>
                    </div>
                </div>
                <br>



                <div class="form-group">
                    <label for="email" class="control-label col-xs-4">邮箱:</label>
                    <div class="col-xs-6 text-left">
                        <p id="email">${userInfo.email}</p>
                    </div>
                </div>
                <br>
                

                <div class="form-group ">

                    <label for="portrait-preview" class="control-label col-xs-4">头像:</label>
                    <div class="col-xs-2">
                        <img src="${userInfo.portrait}" id="portrait-preview">
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
