# 不饿了自助点餐微信小程序(暂定)

### 项目介绍

本项目基于springboot，uniapp 前后端分离开发

- 后端主要技术：springboot，mybatis，redis，rabbitmq
  ​	地址：[BuffetOrderSpringboot: 这是一个自助点餐系统的后端 (gitee.com)](https://gitee.com/fchgit/buffet-order-springboot)

- 微信小程序：uniapp
  ​	地址：[BuffetOrderUniApp: 这是一个基于uniapp的微信自助点餐小程序 (gitee.com)](https://gitee.com/fchgit/buffet-order-uni-app)

- 管理员端：vue, element-ui
  ​	地址：[BuffetOrderAdmin: 这是一个自助点餐小程序的管理页面 (gitee.com)](https://gitee.com/fchgit/buffet-order-admin)

#### 效果图: 

##### 	微信小程序:
<div align=center>
    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/wx-1.png" width="300"/>    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/wx-2.png" width="300"/>
</div>

<div align=center>
    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/wx-3.png" width="300"/>    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/wx-4.png" width="300"/>
</div>

##### 	管理员端:

<div align=center>
    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/admin-1.png" width="600"/>
</div>

<div align=center>
    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/admin-2.png" width="600"/>
</div>

### 项目部署

部署项目前请先确保环境中redis与rabbitmq配置正确

1. 克隆或下载本项目
2. Mysql导入根目录下的[buffet_order.sql](https://gitee.com/fchgit/buffet-order-springboot/blob/master/buffet_order.sql)
3. Idea打开根目录下的[pom.xml](https://gitee.com/fchgit/buffet-order-springboot/blob/master/pom.xml)后等待Maven下载环境依赖
4. 配置resources文件下的[application.yml](https://gitee.com/fchgit/buffet-order-springboot/blob/master/src/main/resources/application.yml)配置端口号，数据库，redis，rabbitmq，微信小程序id等
5. 启动项目

### 特性/功能

#### 	小程序登录流程
<div align=center>
    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/login.png" width="500"/>
</div>

session_key默认1小时后过期，过期后response返回状态码为401，小程序接到401后会重新以code请求

- 微信小程序通过请求微信接口获取openId来识别用户
- 订单通过rabbitmq实现延迟队列，默认下完订单后30分钟未付款自动取消
- 管理员通过security实现权限认证和token登录

### 项目结构
``` 
.
├─main
│  ├─java
│  │  └─com.fch.buffetorder
│  │              │  BuffetorderApplication.java
│  │              │  
│  │              ├─api
│  │              │      WebNotify.java // 封装的websocket发送的实体
│  │              │      
│  │              ├─config // 配置
│  │              │      MainConfig.java
│  │              │      MvcConfig.java
│  │              │      RabbitConfig.java
│  │              │      SecurityConfig.java
│  │              │      
│  │              ├─controller
│  │              │      AdminController.java //只有身份是admin才能访问
│  │              │      AssistantController.java //只有身份是assistant/admin才能访问
│  │              │      FoodController.java
│  │              │      LoginController.java
│  │              │      OrderController.java
│  │              │      TestController.java
│  │              │      UserController.java
│  │              │      
│  │              ├─entity
│  │              │  │  Address.java
│  │              │  │  Admin.java
│  │              │  │  Cate.java
│  │              │  │  Detail.java
│  │              │  │  Food.java
│  │              │  │  Order.java
│  │              │  │  OrderJsonInDb.java //订单放的食物细节详细
│  │              │  │  User.java
│  │              │  │  
│  │              │  ├─detail //小程序发送的食物单选/多选
│  │              │  │      M.java
│  │              │  │      MultiDetail.java
│  │              │  │      R.java
│  │              │  │      RadioDetail.java
│  │              │  │      
│  │              │  └─orderbody //小程序发送的食物细节信息
│  │              │          DM.java
│  │              │          DR.java
│  │              │          OrderBody.java
│  │              │          
│  │              ├─filter //Security用到的拦截器
│  │              │      JwtAuthenticationFilter.java //token验证身份
│  │              │      JwtLoginFilter.java //assistant/admin登录进行
│  │              │      
│  │              ├─handler
│  │              │      FailureHandler.java //assistant/admin登录失败进行
│  │              │      SuccessHandler.java //assistant/admin登录成功进行
│  │              │      
│  │              ├─interceptor // 小程序发送请求经过的拦截器
│  │              │      LoginInterceptor.java
│  │              │      
│  │              ├─listener //rabbitmq队列的监听器
│  │              │      DeadListener.java //监听死信队列
│  │              │      
│  │              ├─mapper //mapper
│  │              │      AddressMapper.java
│  │              │      AdminMapper.java
│  │              │      CateMapper.java
│  │              │      FoodMapper.java
│  │              │      OrderMapper.java
│  │              │      UserMapper.java
│  │              │      
│  │              ├─service
│  │              │  │  AdminService.java
│  │              │  │  CateService.java
│  │              │  │  FoodService.java
│  │              │  │  OrderService.java
│  │              │  │  UserService.java
│  │              │  │  
│  │              │  └─impl
│  │              │          AdminServiceImpl.java
│  │              │          CateServiceImpl.java
│  │              │          FoodServiceImpl.java
│  │              │          OrderServiceImpl.java
│  │              │          UserServiceImpl.java
│  │              │          
│  │              ├─util
│  │              │      JsonUtil.java //主要处理食物的详细信息
│  │              │      JwtUtils.java //jwt
│  │              │      OpenIdUtil.java //获取小程序openId
│  │              │      RedisUtil.java //Redis
│  │              │      UploadImgUtil.java //更新图片
│  │              │      WeiXinParam.java //微信参数
│  │              │      
│  │              └─websocket
│  │                      WebSocket.java
│  │                      
│  └─resources
│      │  application.yml
│      │  
│      ├─mapper
│      │      AddressMapper.xml
│      │      AdminMapper.xml
│      │      CateMapper.xml
│      │      FoorMapper.xml
│      │      OrderMapper.xml
│      │      UserMapper.xml
│      │      
│      └─static
│          └─img
│              │  00282-26029825.png //3个小程序轮播图的图
│              │  00283-3828490356.png
│              │  00284-1374461210.png
│              │  
│              ├─avatar //放头像的文件夹
│              │      ...
│              └─food //放食物图片的文件夹
│                     ...
│                      
└─test
```
#### 数据库er图

<div align=center>
<img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/buffet_order@localhost.png" width="600"/>
</div>

### 注意事项

- application.yml中wx.app-id与wx.app-secret 分别对应AppID(小程序ID)与AppSecret(小程序密钥)，若没有可去微信小程序官方申请获取[微信公众平台](https://mp.weixin.qq.com/)

### 接下来的开发

- 添加分布式锁解决高并发
- 添加员工管理功能