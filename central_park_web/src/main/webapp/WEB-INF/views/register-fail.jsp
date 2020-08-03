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
            <h3>注册失败</h3>
        </div>
        <div class="panel-body">
            <p>原因：${message}</p>
        </div>
    </div>
</div>
</body>
</html>
