<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 宅大颠
  Date: 2020/8/10
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Central Park - User Info</title>

    <link rel="stylesheet" href="${ctx}/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/css/user-config.css">
    <script type="text/javascript" src="${ctx}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/md5.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.js"></script>
    <script type="text/javascript" src="${ctx}/js/user-config.js"></script>
</head>
<body>
<div class="container">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="text-center">修改信息</h3>
        </div>
        <div class="panel-body">
            <!--
                username
                gender
                email
             -->
            <form class="form-horizontal" action="${ctx}/modifyBasicInfo" onsubmit="return checkModifyForm()"
                  method="post">


                <div class="form-group">
                    <label for="user-id" class="control-label col-xs-3">id:</label>
                    <div class="col-xs-6 ">
                        <input type="text" class="form-control" id="user-id"
                               value="${userInfo.id}" disabled/>
                    </div>
                    <div class="col-xs-3 form-group-info" id="user-id-form-info">
                    </div>
                </div>

                <div class="form-group">
                    <label for="username" class="control-label col-xs-3">用户名:</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control " id="username" name="username"
                               placeholder="请输入新用户名" value="${userInfo.username}"
                               onblur="checkUsername();checkUsernameExists();"/>
                    </div>
                    <div class="col-xs-3 form-group-info" id="username-form-info">
                    </div>
                </div>


                <div class="form-group">
                    <label for="btn-modify-password" class="control-label col-xs-3">修改密码:</label>
                    <div class="col-xs-2">
                        <input type="button" class="form-control btn btn-warning" id="btn-modify-password" value="修改密码"
                               data-toggle="modal" data-target="#modify-password">
                    </div>
                    <div class="col-xs-7 form-group-info">
                    </div>
                </div>


                <div class="form-group">
                    <label class="control-label col-xs-3">性别:</label>
                    <div class="col-xs-6 text-left">
                        <label>
                            <c:if test="${userInfo.gender}">
                                男<input type="radio" name="gender" value="true" checked/>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                女<input type="radio" name="gender" value="false"/>
                            </c:if>
                            <c:if test="${!userInfo.gender}">
                                男<input type="radio" name="gender" value="true" />
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                女<input type="radio" name="gender" value="false" checked/>
                            </c:if>

                        </label>

                    </div>
                    <div class="col-xs-3 form-group-info" id="gender-form-info">
                        <!-- 暂时用不上 -->
                    </div>
                </div>


                <div class="form-group">
                    <label for="email" class="control-label col-xs-3">邮箱:</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control " id="email" name="email" value="${userInfo.email}"
                               onblur="checkEmail()">
                    </div>
                    <div class="col-xs-3 form-group-info" id="email-form-info">
                    </div>
                </div>

                <div class="form-group ">
                    <div class="col-xs-2">
                        <!-- 占位 -->
                    </div>
                    <label class="control-label col-xs-1">头像:</label>

                    <div class="col-xs-2">
                        <input type="button" class="form-control btn btn-warning" id="btn-modify-portrait" value="更换头像"
                               data-toggle="modal" data-target="#modify-portrait">
                    </div>

                    <div class="col-xs-2">
                        <img src="${userInfo.portrait}" class="img-preview">
                    </div>

                    <div class="col-xs-2">
                        <!-- 占位 -->
                    </div>
                </div>


                <br>
                <br>

                <div class="form-group">
                    <div class="col-xs-6"></div>


                    <div class="col-sm-3">
                        <button type="submit" class="btn btn-primary btn-block col-sm-4" id="btnSignUp">修&nbsp;&nbsp;改
                        </button>
                    </div>

                </div>


            </form>
        </div>
    </div>
</div>


<!-- modify password start -->
<div class="modal fade" tabindex="-1" id="modify-password">
    <!-- 窗口声明 -->
    <div class="modal-dialog modal-lg">
        <!-- 内容声明 -->
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">修改密码</h4>
            </div>

            <div class="modal-body">
                <form class="form-horizontal" id="form-modify-password" action="/modifyPassword" onsubmit="return checkModPwdForm();">
                    <div class="form-group">
                        <label for="old-password" class="control-label col-xs-3">旧密码:</label>
                        <div class="col-xs-6">
                            <input type="password" class="form-control " id="old-password" name="oldPassword"
                                   placeholder="请输入旧密码" onblur="checkLoginPassword();">
                        </div>
                        <div class="col-xs-3 form-group-info" id="old-password-form-info">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="control-label col-xs-3">新密码:</label>
                        <div class="col-xs-6">
                            <input type="password" class="form-control " id="password" name="password"
                                   placeholder="请输入新密码 (6-20个字符)"
                                   onblur="checkPassword(this.value,$('#password-form-info'));checkRepassword();">
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
                </form>
            </div>


            <div class="modal-footer">
                <button class="btn btn-danger" data-dismiss="modal"
                        onclick="submitForm($('#form-modify-password'),'post');">
                    修改
                </button>
                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
            </div>

        </div>

    </div>
</div>
<!-- modify password end -->


<!-- modify portrait start -->
<div class="modal fade" tabindex="-1" id="modify-portrait">
    <!-- 窗口声明 -->
    <div class="modal-dialog modal-lg">
        <!-- 内容声明 -->
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">修改头像</h4>
            </div>

            <div class="modal-body">
                <form class="form-horizontal" id="form-modify-portrait">
                    <div class="form-group ">
                        <div class="col-xs-2">
                            <!-- 占位 -->
                        </div>
                        <label for="portrait" class="control-label col-xs-1">头像（不选即使用原头像）:</label>
                        <div class="col-xs-2">
                            <input type="file" class="form-control " id="portrait" name="portrait"
                                   onchange="refreshPortraitPreview()"/>
                        </div>
                        <div class="col-sm-1">
                            <button type="button" class="btn btn-warning"
                                    onclick="resetPortrait($('#oldPortraitSrc').val())">重置头像
                            </button>
                        </div>
                        <label for="portrait-preview" class="control-label col-xs-1">头像预览:</label>

                        <div class="col-xs-2">
                            <img src="${userInfo.portrait}" id="portrait-preview">
                            <input value="${userInfo.portrait}" id="oldPortraitSrc" style="display: none"/>
                        </div>
                    </div>
                </form>
            </div>


            <div class="modal-footer">
                <button class="btn btn-danger updateProductType" data-dismiss="modal"
                        onclick="submitForm($('#form-modify-portrait'));">
                    修改
                </button>
                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
            </div>

        </div>

    </div>
</div>
<!-- modify portrait end -->
</body>
</html>
