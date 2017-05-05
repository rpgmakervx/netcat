package org.easyarch.netcat.test;

import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.request.impl.HttpHandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.web.http.response.impl.HttpHandlerResponse;
import org.easyarch.netpet.web.mvc.action.filter.HttpFilter;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;
import org.easyarch.netpet.web.server.App;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:31
 * description:
 */

public class Application {

    public static void main(String[] args) {
        App app = new App();
//        app.config().contextPath("/shopping")
//                .webView("/home/code4j/project/demo/static/page")
//                .viewPrefix("page")
//                .viewSuffix("html")
//                .maxFileUpload(1024*1024*4)
//                .errorPage("myerror")
//                .useCache()
//                .cacheMaxAge(1024);

        app.get("/index", new HttpHandler() {
            @Override
            public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
                response.html("login");
            }
        }).filter("/get", new HttpFilter() {
            @Override
            public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
                return false;
            }

            @Override
            public void after(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {

            }
        }).start(8888);
//        app.post("/index/{username}", new ParameterizeHandler())
//                .get("/download",new DownLoadHandler())
//                .get("/index/{username}",new JsonHandler())
//                .get("/index/xingtianyu",new ImageHandler())
//                .get("/redirect",new RedirectHandler())
//                .get("/user/login",new LoginPageHandler())
//                .post("/user/login",new LoginHandler())
//                .post("/upload",new UpLoadHandler())
//                .filter("/*",new DemoFilter())
//                .post("/user/index",new IndexHandler())
//                .start(8800);
    }
}
