# Central Park

[首页](https://132.232.213.145:8080) 基于SMM与WebSocket的多人聊天室

## 功能描述
- 用户注册
- 用户登录
- 查看用户资料（待办）
- 修改用户资料（待办）
- 实时显示在线成员
- 发送/接受即时文字消息
- 发送/接受即时文件消息（待办）
- 查看聊天记录（待办）
- 查看聊天文件列表（待办）
- 定时删除聊天记录（待办）


## 项目结构
```markdown
|- com
	|- beta
		|- demo
			|- constant		// 自定义常量
			|- controller	// Controller类
			|- dao			// DAO类
			|- exception	// 自定义异常类
			|- mapper		// Mybatis Mapper文件
			|- pojo			// 实体类
			|- service		// Service接口类
				|- impl		// service实现类
			|- util			// 工具类
			|- vo			// 值对象
```


## 模块结构
```markdown
central_park.git
|- README.md
|- pom.xml
|- central_park_common/
|- central_park_pojo/
|- central_park_dao/
|- central_park_service/
|- central_park_web/
|- .gitignore
```