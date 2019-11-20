# 框架介绍

## server包

1. WebDom4j.java: 用于解析web.xml文档
2. Entity.java: 存放servlet-name和servlet-class的映射实体信息
3. Mapping.java: 存放servlet-name和url-pattern的映射实体信息
4. ServletContent.java: 将存放Entity和Mapping的容器, 存放成Map容器
5. WebApp.java: 存放一个ServletContent对象, 使用WebDom4j对象解析web.xml文档, 并初始化ServeltContent对象
6. Request.java: 访问请求对象, 当客户端连接的时候, 会新建一个Request对象
7. Response.java: 服务器响应对象, 用于服务器向客户端传递信息
8. Dispatcher.java: 多线程类, 每有一个客户端连接, 则会新建立一个Dispatcher对象, 实现并发访问
9. Server.java: 服务器主类, 使用WebApp实现给我一个url, 返回一个Servlet对象, 对Servlet对象进行操作

## servlet包

1. Servlet.java: 所有Servlet的抽象父类, 里边有两个抽象方法doPost和doGet, 分别对应get访问方式和post访问方式, service方法调用doGet方法
2. LoginServlet.java: 登录的Servlet类, 继承自Servlet类

## util包

> 此包为工具包

1. CloseUtil.java: 内部只有一个方法, close方法, 参数是一个不固定的Closeable类型, 将接收到的参数关闭

## WEB_INFO包

1. web.xml: 里边包含了访问页面的配置信息

## 使用的jar包

- DOM4j1.6.1.jar: 解析xml文档使用dom4j方式

# 运行项目
运行pers.jssd.server.Server.java文件即可启动服务器, 可使用index.html文件测试

