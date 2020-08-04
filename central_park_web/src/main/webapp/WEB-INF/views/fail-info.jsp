<%--
  Created by IntelliJ IDEA.
  User: 宅大颠
  Date: 2020/8/3
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Central Park - Register Fail</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/css/user-config.css">
    <script type="text/javascript" src="${ctx}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/js/user-config.js"></script>

</head>
<body>
<div class="container text-center main-container">
    <div class="panel panel-warning">
        <div class="panel-heading">
            <h3>${failTitle}</h3>
        </div>
        <div class="panel-body">
            <p>${message}</p>
            <!-- TODO : 跳转未测试 -->
            <p>点击<a href="javascript:void(0);" onclick="jumpToHomePage()">这里</a>返回主页</p>
        </div>
    </div>
</div>
</body>
</html>
