# Central Park 开发日志

## 20200731
- 创建空项目

## 20200801
- 配置工程与各模块
    - 配置github
    - 配置jar包依赖
    - 配置spring
        - spring-dao.xml
        - spring-mvc.xml
        - spring-service.xml
- 编写前端静态页面
    - 完成login.jsp
    - 完成register.jsp
    
## 20200802
- 完成页面register.jsp的表单验证代码
    - 使用js正则字符串规则验证
    - 使用数据库的用户名查重验证
    
## 20200803
- 完成用户注册功能
- 编写完成前端页面chatroom.jsp

## 20200804
- 完成用户登录功能

## 20200805
- 完成实时显示在线用户的功能
- 可以实时收发简单的文字消息

## 20200806
- 文字消息现在可携带发送者名称
- 文字消息现在可录入数据库
- 修补
    - 新增限制文件上传大小与格式功能

## 20200807
- 修补
    - 重复登录时将前一个已登录的session销毁
    - 修改文本消息展示，现在可携带用户信息、时间信息和消息主体
    - 修复多空格与回车在消息展示时合并显示的问题
    - 新增文本消息长度大小的限制
    - chatroom.jsp消息显示框元素添加了垂直滚动轴，但仍需美化

## 20200808
- 完成文件上传和下载的功能，文件上传消息即时展示

## 20200809
- 完成查看其他用户信息的功能

## 20200810
- 完成修改用户信息的功能
    - 单独修改密码
    - 单独修改头像
    - 单独修改基本信息：用户名、性别、邮箱

## 20200811

- 项目上线，进行调整

## 20200812

- 修补
  - 限制聊天文件大小
  - 添加服务器端空消息的验证等