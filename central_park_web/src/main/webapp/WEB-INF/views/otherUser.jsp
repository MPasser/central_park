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
</head>
<body>
<h3>${userInfo.username}</h3>
<p>id:${userInfo.id}<img style="width: 180px; height: auto;" src="${userInfo.portrait}"/></p>
<p>性别:${userInfo.gender}</p>
<p>邮箱:${userInfo.email}</p>
</body>
</html>
