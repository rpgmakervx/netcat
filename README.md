netpet high performance http server
=====================================
[![Build Status](https://travis-ci.org/rpgmakervx/netpet.svg?branch=master)](https://travis-ci.org/rpgmakervx/netpet)

netpet 是基于开源nio框架netty开发的一个web服务器，支持基本http协议，采用嵌入式开发，仅仅需要依赖jar包，
无需容器部署，即可完成一个web应用。

使用该中间件用户只需关注 `处理器（handler）`和`过滤器（filter）`这两个组件，在此基础上进行业务开发即可。

## Features:
 * 基本的地址路由，页面渲染（采用freemarker模板引擎），get/post/put/delete方法支持.
 * 微内核设计的api，支持rest架构风格，支持路由通配符，占位符等;提供多种响应客户端方式（json,html,image等）。
 * 支持服务端session以及cookie.
 * 提供便捷工具类（针对字符串，文件，异步http请求等）
 * 无需配置文件，部分参数可在代码中通过便捷的api进行配置
 
## Install and Prepare
首先下载本项目，在本地进行包依赖安装，在根目录执行：`mvn install`

接着在你的工程中的pom.xml中写入：
```xml
<dependency>
    <groupId>org.easyarch</groupId>
    <artifactId>netpet</artifactId>
    <version>1.0.1</version>
</dependency>
```
然后在你的resource目录放入几个写好的html文件，用来做测试。

安装和准备完成

## Get Started:
### 页面展示：

新建一个类Application,假设你之前有一个index.html文件，代码如下：

```java
public class Application {

    public static void main(String[] args) {
        App app = new App();
        app.get("/index", new HttpHandler() {
            @Override
            public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
                response.html("index");
            }
        }).start(9000);
    }
}
```

打开浏览器，地址栏输入： [localhost:9000](https://localhost:9000) 能够看见你的html页面

注意start若不传递端口号默认为8080（约定由于配置）

### 其他几种响应客户端方式

针对web开发常用的几个功能，`HandlerResponse` 类包含如下api方便开发：

```java

    public void json(String json);
    
    public void image(byte[] bytes);

    public void redirect(String url);

    public void download(byte[] bytes, String filename, String headerValue);
```
自上而下，分别返回json,图片，重定向请求（302），文件下载

`HandlerResponse` 类也包含几个便捷的操作：
```java
    public String getParam(String name);

    public Integer getIntParam(String name);
    
    public UploadFile file(String name);
    
    public List<UploadFile> files();

    public<T> T body(Class<T> cls) throws Exception;

    public Json getJson();
```
自上而下，分别是获取字符串参数，获取整型参数，获取上传文件，获取多个上传文件，获取一个表单实体（字段必须要和bean匹配）


### rest架构风格
我们看到第一个例子中的`get`方法第一个参数填写的是rest接口名，对应`post`,`put`.`delete`方法使用一样，不做过多说明。
特别演示路由通配符和占位符，代码如下：
```java
public class Application {

    public static void main(String[] args) {
        App app = new App();
        app.get("/get/{username}", new HttpHandler() {
            @Override
            public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
                String username = request.getParam("username");
                response.json(new Json("name",username));
            }
        });
        app.get("/check/*", new HttpHandler() {
            @Override
            public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
                // do some thing
            }
        });
        app.start(9000);
    }
}
```
第一个接口，当客户端发起请求为 /get/code4j 时，得到服务端响应：{"username":"code4j"}

第二个接口，一级目录为`check`的请求均会被该接口处理，如/check/data, /check/stuff 等等

## Advanced Features:

### HttpFilter
netpet提供过滤器（类比j2ee的filter）,使用方法如下：
```java
public class Application {

    public static void main(String[] args) {
        App app = new App();
        app.get("/get/{username}", new HttpHandler() {
            @Override
            public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
                String username = request.getParam("username");
                response.json(new Json("name",username));
            }
        });
        app.filter("/get/*", new HttpFilter() {
            
            @Override
            public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
               boolean pass = request.getParam("pass");
               if(pass){
                   return true;
               }else{
                   //do some thing
                   return false;
               }
            }

            @Override
            public void after(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
                //do some thing
            }
        });
        app.start(9000);
    }
}
```
filter使用方法和handler类似，第一个参数表示要拦截的接口名，第二个参数包括两个回调方法。

before代表进入handler之前要处理的事，after表示handler处理完后再做的事。
如果请求路由被handler命中，before返回true，则表示对应handler能够执行，返回false则请求在filter处终止，after也将不被处理。

### 部分常用配置api

netpet尽量简化用户配置，但并不是完全零配置，在默认配置不足以满足用户需求时，用户可以适当进行配置。

```java
public class Application {

    public static void main(String[] args) {
        App app = new App();
        app.config().contextPath("/shopping")
                .webView("/home/code4j/project/demo/static/page")
                .viewPrefix("page")
                .viewSuffix("html")
                .maxFileUpload(1024*1024*4)
                .errorPage("myerror")
                .useCache()
                .cacheMaxAge(1024);
                        
    }
}
```
上面这段代码就是提供给用户进行配置的部分信息，这里逐行简单说明一下：
1. 请求上下文路径，设置上这个路径后，你访问的每个请求前都需要加上这个路径才能访问到，有点类似tomcat中的项目名，类似功能在idea启动web项目时可以设置`application context`选项

2. 静态资源路径，这里填写绝对路径，用户可以通过脚本启动时设置进来，若不填写默认从当前jar目录开始，或者resource目录（约定由于配置）。

3. 前缀，响应渲染页面的前缀，类比springmvc配置。默认的prefix值为`WEB-INF`，也就意味着你的文件必须放在webView配置路径下的/WEB-INF/ 路径才能被访问到。另外类似tomcat，WEB-INF这个名字的prefix不能被请求直接访问。

4. 后缀，响应渲染页面的后缀，只有后缀和你设置匹配的页面才能被渲染

5. 一次文件上传最大阈值

6. 错误页面名，默认错误页面名为 `error`(约定由于配置)，netpet提供了一个默认的error.html文件，在发生500,404等请求时可以被响应。

7. 表示开启强缓存，有关http缓存知识请自行百度 `http强缓存和协商缓存`

8. 强缓存的生命周期
