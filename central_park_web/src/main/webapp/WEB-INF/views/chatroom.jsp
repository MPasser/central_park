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
    <title>Central Park - Chatroom</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/chatroom-config.css">
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

        /**
         * 接收到消息的回调方法
         * 消息类型：
         * - USER:JSON_STRING   用户JSON数据，JSON_STRING为消息主体
         * - MESSAGE:TEXT:USERNAME:MESSAGE_PAYLOAD    消息数据，TEXT为文本类型，USER为消息发送者，MESSAGE_PAYLOAD为消息主体
         * - MESSAGE:FILE:NAME:FILEPATH:USERNAME
         *
         */


        websocket.onmessage = function (event) {
            console.log("onmessage方法接收到的原始数据数据：" + event.data);
            let data = event.data.split(":");
            console.log("onmessage方法经过分割的回传数据：" + data);

            if ("USER" === data[0]) {
                let payload = event.data.substring(5);
                let otherUsers = JSON.parse(payload);
                $('#other-users').empty();
                otherUsers.forEach(showOtherUser);
            } else if ("MESSAGE" === data[0]) {
                if ("TEXT" === data[1]) {
                    showTextMessage(data[2], data[3]);
                } else if ("FILE" === data[1]) {
                    showFileMessage(data[2], data[3], data[4]);
                }

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
            p.attr('value', user.username);
            p.attr('onclick', 'checkUserInfo(this);');

            div.append(img).append(p);
            container.append(div);

        }


        function checkUserInfo(pUser) {

            console.log(pUser);


            console.log(pUser.getAttribute('value'));
            console.log("pUser");


            let form = $('<form>');

            form.attr('action', 'userInfo');
            form.attr('target', '_blank');
            form.attr('method', 'get');

            let username = $('<input>');
            username.attr('type', 'text');
            username.attr('name', 'username');
            username.attr('value', pUser.getAttribute('value'));

            form.append(username);

            console.log('其他用户信息-提交查看请求');

            form.appendTo('body');
            form.hide();
            form.submit();

        }




        function showTextMessage(username, message) {
            // |- container
            //    |- div
            //        |- p // 用户名与时间
            //        |- divPanel
            //            |- pMsg // 文本消息内容

            let container = $('#message-body-container');
            let div = $('<div>');
            let p = $('<p>');

            p.text(username + "(" + new Date().toLocaleTimeString() + ")");

            let divPanel = $('<div>');
            divPanel.attr('class', 'panel-body message-item');
            // div.attr('id','messageId');

            let pMsg = $('<p>');
            pMsg.html(message);

            divPanel.append(pMsg);
            div.append(p).append(divPanel);
            container.append(div);

            container[0].scrollTop = container[0].scrollHeight;
        }

        function showFileMessage(filename, filepath, username) {
            // |- container
            //    |- div
            //        |- p // 用户名与时间
            //        |- divPanel
            //            |- pImg // 文件图片
            //            |- aMsg // 文件名
            let container = $('#message-body-container');
            let div = $('<div>');
            let p = $('<p>');

            p.text(username + "(" + new Date().toLocaleTimeString() + ")");

            let divPanel = $('<div>');
            divPanel.attr('class', 'panel-body message-item');
            // div.attr('id','messageId');

            let pImg = $('<img>');
            pImg.attr('src', filepath);
            pImg.attr('style', 'height:100px;width:auto;')

            let aMsg = $('<a>');
            aMsg.attr('href', filepath);
            aMsg.attr('target', '_blank');
            aMsg.html(filename);

            divPanel.append(pImg).append(aMsg);
            div.append(p).append(divPanel);
            container.append(div);

            container[0].scrollTop = container[0].scrollHeight;
        }


        // 将消息显示在网页上，需要更改，应当修改成提示消息的方法
        function showMessage(message, color) {
            let container = $('#message-body-container');

            let div = $('<div>');
            div.attr('class', 'panel-body message-item');
            // div.attr('id','messageId');

            if (color) {
                div.attr('style', 'background-color :' + color);
            }

            let p = $('<p>');
            p.html(message);

            div.append(p);

            container.append(div);

            container[0].scrollTop = container[0].scrollHeight;
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

        //发送消息至websocket
        function send() {

            if (websocket.readyState !== 1) {
                alert("连接已断开，请重新登录");
                jumpToHomePage();
            }

            let message = $('#messageTextarea').val();
            console.log("send()中取到的textarea的value：" + message);
            // 正常显示textarea中的空格与换行符
            message = message.replace(/\s/g, "&nbsp;").replace(/\n/g, "<br/>");
            console.log("send()中经过处理后textarea的value：" + message);
            if (null === message || "" === (message)) {
                return;
            }
            websocket.send(message);
        }


        // 发送消息，此处主要是记录到数据库中
        function sendTextMessage() {
            let textMessage = $('#messageTextarea').val();
            $('#messageTextarea').val('');
            $.post(
                "/sendTextMessage", {
                    'textMessage': textMessage
                }
            )
        }


        // 上传文件至数据库
        function sendFileMessage() {
            if (websocket.readyState !== 1) {
                alert("连接已断开，请重新登录");
                jumpToHomePage();
            }

            // let fileMessage = $('fileMessage');
            let formData = new FormData();
            formData.append('fileMessage', $('#fileMessage')[0].files[0]);
            console.log("upload()中取到的formData：" + formData);
            if (null === $('#fileMessage')[0].value || "" === $('#fileMessage')[0].value) {
                console.log("文件名为空，返回");
                return false;
            }

            $.ajax({
                url: 'sendFileMessage',
                type: 'post',
                data: formData,
                processData: false,
                contentType: false,
                success: function (message) {
                    console.log("文件上传成功");
                    if (websocket.readyState !== 1) {
                        alert("连接已断开，请重新登录");
                        jumpToHomePage();
                    }
                    websocket.send(message);

                    $('#fileMessage').val('');
                    $('#fileMsgLocation').val('');
                },
                error: function (message) {
                    console.log("文件上传失败");
                    $('#fileMessage').val('');
                    $('#fileMsgLocation').val('');
                }
            })

        }


        // 显示聊天记录
        function showMsgLog() {
            let form = $('<form>');

            form.attr('action', 'messageLog');
            form.attr('target', '_blank');
            form.attr('method', 'post');

            // let scope = $('<input>');
            // scope.attr('type','text');
            // scope.attr('name','scope');
            // scope.attr('value','all');
            //
            // form.append(scope);

            console.log('消息记录-提交查看请求');

            form.appendTo('body');
            form.hide();
            form.submit();
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
        <div class="panel-body pre-scrollable user-info-container-body" id="other-users">
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


        <div class="panel-body text-left pre-scrollable message-info-container-body" id="message-body-container">
            <!-- 示例代码 -->

            <!-- 文本消息 -->
            <%--            <div>--%>
            <%--                <p>username(time)</p>--%>
            <%--                <div class="panel-body message-item">--%>
            <%--                    <p>message</p>--%>
            <%--                </div>--%>
            <%--            </div>--%>

            <!-- 文件消息 -->
            <%--            <div>--%>
            <%--                <p>username(time)</p>--%>
            <%--                <div class="panel-body message-item">--%>
            <%--                    <img src=""/>--%>
            <%--                    <a>filepath</a>--%>
            <%--                </div>--%>
            <%--            </div>--%>
        </div>


    </div>
    <!-- message info end -->

</div>
<!-- main container end -->


<div class="container textarea-container">
    <br>
    <div>
        <form class="form-horizontal" id="file-message-form" enctype="multipart/form-data">
            <div class="form-group">
                <div class="col-sm-4 control-label">选择文件：</div>
                <div class="col-sm-6">
                    <div class="input-group">
                        <input id='fileMsgLocation' class="form-control" onclick="$('#fileMessage').click();">
                        <label class="input-group-btn">
                            <input type="button" value="浏览文件" class="btn btn-primary"
                                   onclick="$('#fileMessage').click();">
                        </label>
                        <label class="input-group-btn">
                            <input type="button" value="上传文件" class="btn btn-warning" onclick="sendFileMessage()">
                        </label>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <label class="input-group-btn">
                            <input type="button" value="消息记录" class="btn btn-info" onclick="showMsgLog()">
                        </label>
                    </div>
                </div>
                <input type="file" id='fileMessage' name="fileMessage"
                       onchange="$('#fileMsgLocation').val($('#fileMessage').val());" style="display: none">
            </div>
        </form>


        <form class="form-horizontal">

            <div class="col-xs-12 form-group">
                <textarea class="form-control" id="messageTextarea" maxlength="120"></textarea>
            </div>
            <br>
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
