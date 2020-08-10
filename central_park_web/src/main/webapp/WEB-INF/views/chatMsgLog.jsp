<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: 宅大颠
  Date: 2020/8/9
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Central Park - ChatMessageLog</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/chatroom-config.css">
    <script type="text/javascript" src="${ctx}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap-paginator.js"></script>

    <script type="text/javascript">
        $(function () {
            paginatorConfig();
        });

        function paginatorConfig() {
            $('#pagination').bootstrapPaginator({
                bootstrapMajorVersion: 3,  // bootstrap版本
                currentPage:${pageInfo.pageNum}, // 当前页码
                totalPages: ${pageInfo.pages}, // 总页数
                itemTexts: function (type, page, current) { //设置显示的样式，默认是箭头
                    switch (type) {
                        case 'first':
                            return '首页';
                        case 'prev':
                            return '上一页';
                        case 'next':
                            return '下一页';
                        case 'last':
                            return '末页';
                        case 'page':
                            return page;
                    }
                },
                pageUrl: function (type, page, current) {
                    return "${ctx}/messageLog?pageNum=" + page;
                }
            });
        }
    </script>
</head>
<body>

<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h3 class="panel-title">消息记录</h3>
            <ul id="pagination"></ul>
        </div>

        <div class="panel-body">
            <!-- 使用bootstrap paginator -->

            <!-- 示例代码 -->
            <!--
             <div>
                            <p>username(time)</p>
                            <div class="panel-body message-item">
                                <p>message</p>
                            </div>
                        </div>

                        <div>
                            <p>username(time)</p>
                            <div class="panel-body message-item">
                                <img src=""/>
                            </div>
                        </div>
             -->
            <c:forEach items="${pageInfo.getList()}" var="msgItem">
                <div>
                    <p>
                            ${msgItem.user.username} (<fmt:formatDate value="${msgItem.sentTime}"
                                                                      pattern="MM-dd hh:mm"/>)：
                    </p>
                    <div class="panel-body message-item">
                        <c:if test="${'0' == msgItem.messageType}">
                            <p>${msgItem.messagePayload}</p>
                        </c:if>
                        <c:if test="${'1' == msgItem.messageType}">
                            <%--                            <img src="${msgItem.messageRef}"/>--%>
                            <a href="${msgItem.messageRef}">${msgItem.messagePayload}</a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>
