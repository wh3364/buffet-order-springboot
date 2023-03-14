# 不饿了自助点餐微信小程序(暂定)

### 项目介绍

​	本系统采用前后端分离架构，前端主要使用Vue、Uni-App、Echarts等技术，后端使用SpringBoot、MyBatis、Redis、RabbitMQ、Minio等技术，以保证系统的高效性。

​       为了保证系统的安全性，采用JWT（JSON Web Token）对用户进行权限验证，采用SpringSecurity进行安全性的维护。

- 后端主要技术：springboot，mybatis，redis，rabbitmq
  地址：
  - Gitee： [BuffetOrderSpringboot(gitee.com)](https://gitee.com/fchgit/buffet-order-springboot)
  - GitHub：[BuffetOrderSpringboot(github.com)](https://github.com/wh3364/BuffetOrderSpringboot)
- 微信小程序：uniapp
  地址：
  - Gitee：[BuffetOrderUniApp(gitee.com)](https://gitee.com/fchgit/buffet-order-uni-app)
- 管理员端：vue, element-ui
  地址：
  - Gitee：[BuffetOrderAdmin:(gitee.com)](https://gitee.com/fchgit/buffet-order-admin)

#### 效果图: 

##### 	微信小程序:
<div align=center>
    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/wx-1.png" width="300"/>    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/wx-2.png" width="300"/>
</div>

<div align=center>
    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/wx-3.png" width="300"/>    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/wx-4.png" width="300"/>
</div>

<div align=center>
    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/wx-5.png" width="300"/>    <img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/%E6%95%88%E6%9E%9C%E5%9B%BE/wx-6.png" width="300"/>
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
│  │              │      ResponseBean.java
│  │              │      ResultEnum.java
│  │              │      WebNotify.java // 封装的websocket发送的实体
│  │              ├─aspect //AOP切面
│  │              │      AfterClearCache.java //之后清除缓存
│  │              │      BeforeClearCache.java //之前清除缓存
│  │              │      Cache.java //缓存注解
│  │              │      RedisCacheAdvice.java //AOP的Redis增强方法
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
│  │              │      DashboardController.java
│  │              │      FoodController.java
│  │              │      LoginController.java
│  │              │      OrderController.java
│  │              │      TestController.java
│  │              │      UserController.java
│  │              │
│  │              ├─dto
│  │              │      AdminDto.java Admin登录实体
│  │              │      DashboardDto.java 仪表盘所使用数据的实体
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
│  │              │  │  DashboardService.java
│  │              │  │  FoodService.java
│  │              │  │  OrderService.java
│  │              │  │  UserService.java
│  │              │  │  
│  │              │  └─impl
│  │              │          AdminServiceImpl.java
│  │              │          CateServiceImpl.java
│  │              │          DashboardServiceImpl.java
│  │              │          FoodServiceImpl.java
│  │              │          OrderServiceImpl.java
│  │              │          UserServiceImpl.java
│  │              │          
│  │              ├─util
│  │              │      JsonUtil.java //主要处理食物的详细信息
│  │              │      JwtUtils.java //jwt
│  │              │      OpenIdUtil.java //获取小程序openId
│  │              │      OrderIdUtil.java //生成订单Id
│  │              │      RedisUtil.java //Redis
│  │              │      RequestUtil.java //封装的请求类
│  │              │      ThreadLocalUtils.java //本地线程类
│  │              │      UploadImgUtil.java //更新图片
│  │              │      WeiXinParam.java //微信参数
│  │              │      
│  │              └─websocket
│  │                      WebSocket.java
│  │                      
│  └─resources
│      │  application.yml
│      │  application-wx.yml //存放小程序密钥，需要自己创建
│      │  
│      ├─mapper
│      │      AddressMapper.xml
│      │      AdminMapper.xml
│      │      CateMapper.xml
│      │      FoorMapper.xml
│      │      OrderMapper.xml
│      │      UserMapper.xml
│      
│      
│                      
└─test
```
#### 数据库er图

<div align=center>
<img src="https://gitee.com/fchgit/buffet-order-springboot/raw/master/%E5%9B%BE/buffet_order@localhost.png" width="600"/>
</div>

### 注意事项

- application-wx.yml中wx.app-id与wx.app-secret 分别对应AppID(小程序ID)与AppSecret(小程序密钥)，若没有可去微信小程序官方申请获取[微信公众平台](https://mp.weixin.qq.com/)

  格式：

  ```y&#39;m&#39;l
  #微信配置
  wx:
    app-id: 
    app-secret: 
    grant-type: authorization_code
  ```
