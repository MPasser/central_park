<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 宅大颠
  Date: 2020/8/3
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Central Park - Login</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/chatroom-config.css">
    <script type="text/javascript" src="${ctx}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/js/user-config.js"></script>

</head>
<body>

<!-- main container start -->
<div class="container text-center chatroom-container">
    <!-- user info start -->
    <div class="panel panel-primary col-sm-3 user-info-container">
        <div class="panel-heading user-info-container-heading" >
            <div class="user-info-item">
                <img src="${user.portrait}">
                <p>${user.username}</p>
            </div>

        </div>
        <div class="panel-body" >
            <c:forEach items="${otherUsers}" var="otherUser">

                <div class="user-info-item" id="${otherUser.id}">
                    <img src="${otherUser.portrait}">
                    <p>username</p>
                </div>

            </c:forEach>

            <div class="user-info-item">
                <img src="../images/default-portrait.jpg">
                <p>username</p>
            </div>
            <div class="user-info-item">
                <img src="../images/default-portrait.jpg">
                <p>username</p>
            </div>
        </div>
    </div>
    <!-- user info end -->


    <!-- message info start -->
    <div class="panel panel-primary col-sm-9 message-info-container">
        <div class="panel-heading message-info-container-heading" >
            <h3>中央公园聊天室</h3>
        </div>


        <div class="panel-body text-left message-info-container-body" >
            <div class="panel-body message-item">
                <p>kjasdkjhadfskjhasdfkjl</p>
            </div>
            <div class="panel-body message-item">
                <p>kjasdkjhadfskjhasdfkjl</p>
            </div>
            <div class="panel-body message-item">
                <p>kjasdkjhadfskjhasdfkjl</p>
            </div>
        </div>


    </div>
    <!-- message info end -->

</div>
<!-- main container end -->


<div class="container textarea-container">
    <div class="textarea-container-toolbar text-right">
        <a>消息记录</a>
    </div>
    <div>
        <form class="form-horizontal">
            <div class="form-group">
                <textarea class="form-control"></textarea>
            </div>
            <div class="form-group">
                <button class="btn btn-primary pull-right" type="submit">发&nbsp;&nbsp;送</button>
            </div>
        </form>
    </div>
</div>




<!-- footer container start -->
<div class="container">
    <footer class="text-muted">
        <p>Copyright &copy; 2020 MPasser</p>
    </footer>
</div>
<!-- footer container end -->


</body>
</html>
