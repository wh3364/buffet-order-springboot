# 不饿了自助点餐微信小程序(暂定)

本项目基于springboot，uniapp 前后端分离开发

- 后端主要技术：springboot，mybatis，redis，rabbitmq 

  ​	地址：[BuffetOrderSpringboot: 这是一个自助点餐系统的后端 (gitee.com)](https://gitee.com/fchgit/buffet-order-springboot)

- 微信小程序：uniapp 

  ​	地址：[BuffetOrderUniApp: 这是一个基于uniapp的微信自助点餐小程序 (gitee.com)](https://gitee.com/fchgit/buffet-order-uni-app)

- 管理员端：vue, element-ui 

  ​	地址：[BuffetOrderAdmin: 这是一个自助点餐小程序的管理页面 (gitee.com)](https://gitee.com/fchgit/buffet-order-admin)

#### 效果图:  

##### 微信小程序:

##### 管理员端:

### 特性/功能

- 特性/功能A

- 特性/功能B


### 项目结构
``` 

```
### 项目部署

部署项目前请先确保环境中redis与rabbitmq配置正确

1. Mysql导入根目录下的[buffet_order.sql](https://gitee.com/fchgit/buffet-order-springboot)
2. Idea打开根目录下的[pom.xml](https://gitee.com/fchgit/buffet-order-springboot/blob/master/pom.xml)后等待Maven下载环境依赖
3. 配置resources文件下的[application.yml](https://gitee.com/fchgit/buffet-order-springboot/blob/master/src/main/resources/application.yml)配置端口号，数据库，redis，rabbitmq，微信小程序id等
4. 启动项目

### 注意事项
- application.yml中wx.app-id与wx.app-secret 分别对应AppID(小程序ID)与AppSecret(小程序密钥)，若没有可去微信小程序官方申请获取[微信公众平台](https://mp.weixin.qq.com/)

### 接下来的开发
- 添加分布式锁解决高并发
- 添加员工管理功能