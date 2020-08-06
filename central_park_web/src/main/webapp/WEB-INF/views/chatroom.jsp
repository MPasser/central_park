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

    <script type="text/javascript">

        let websocket = null;
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://localhost:8080/chatroomWebSocket");
        } else {
            alert('当前浏览器不支持 websocket ，聊天室无法正常工作')
        }

        //连接发生错误的回调方法
        websocket.onerror = function () {
            showMessage("与服务器的连接发生错误", "#e4b9b9");
        };

        //连接成功建立的回调方法
        websocket.onopen = function () {
            showMessage("服务器连接成功", "#67b168");
        }

        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            if ("USERINFO=" === event.data.substring(0, 9)) {
                let otherUsers = JSON.parse(event.data.substring(9));
                $('#other-users').empty();
                otherUsers.forEach(showOtherUser);
            } else {
                showMessage(event.data);
            }

        }

        // 将其他用户显示在网页上
        function showOtherUser(user) {
            let container = $('#other-users');

            let div = $("<div>");
            div.attr('id', user.id);
            div.attr('class', 'user-info-item');

            let img = $('<img>');
            img.attr('src', user.portrait);

            let p = $('<p>');
            p.text(user.username);

            div.append(img).append(p);
            container.append(div);

        }

        // 将消息显示在网页上，需要更改
        function showMessage(message, color) {
            let container = $('#message-body-container');

            let div = $('<div>');
            div.attr('class', 'panel-body message-item');
            // div.attr('id','messageId');

            if (color) {
                div.attr('style', 'background-color :' + color);
            }

            let p = $('<p>');
            p.text(message);

            div.append(p);

            container.append(div);
        }

        //连接关闭的回调方法
        websocket.onclose = function () {
            showMessage("与服务器的连接断开", "#e4b9b9");
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closeWebSocket();
        }

        //关闭WebSocket连接
        function closeWebSocket() {
            websocket.close();
        }

        //发送消息
        function send() {
            let message = $('#messageTextarea').val();
            if (null === message || "" === (message)){
                return;
            }
            websocket.send(message);
        }


        // 发送消息，此处主要是记录到数据库中
        function sendTextMessage() {
            let chatTextMessage = $('#messageTextarea').val();
            $('#messageTextarea').val('');
            $.post(
                "/sendTextMessage", {
                    'chatTextMessage': chatTextMessage
                }
            )
        }
    </script>

</head>
<body>

<!-- main container start -->
<div class="container text-center chatroom-container">
    <!-- user info start -->
    <div class="panel panel-primary col-sm-3 user-info-container">
        <div class="panel-heading user-info-container-heading">
            <div class="user-info-item">
                <img src="${selfUser.portrait}">
                <p>${selfUser.username}</p>
            </div>

        </div>
        <div class="panel-body" id="other-users">
            <!-- 示例代码 -->
            <%--            <div class="user-info-item" id='userId'>--%>
            <%--                <img src="../images/default-portrait.jpg">--%>
            <%--                <p>username</p>--%>
            <%--            </div>--%>
        </div>
    </div>
    <!-- user info end -->


    <!-- message info start -->
    <div class="panel panel-primary col-sm-9 message-info-container">
        <div class="panel-heading message-info-container-heading">
            <h3>中央公园聊天室</h3>
        </div>


        <div class="panel-body text-left message-info-container-body" id="message-body-container">
            <!-- 示例代码 -->
            <%--            <div class="panel-body message-item" id="messageId">--%>
            <%--                <p>message content</p>--%>
            <%--            </div>--%>
        </div>


    </div>
    <!-- message info end -->

</div>
<!-- main container end -->


<div class="container textarea-container">
    <div class="textarea-container-toolbar text-right">
        <a>消息记录</a>
    </div>
    <br>
    <div>
        <form class="form-horizontal" action="">
            <div class="col-xs-12 form-group">
                <textarea class="form-control" id="messageTextarea"></textarea>
            </div>
            <div class="form-group">
                <button class="btn btn-primary pull-right" type="button" onclick="send();sendTextMessage();">发&nbsp;&nbsp;送</button>
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
