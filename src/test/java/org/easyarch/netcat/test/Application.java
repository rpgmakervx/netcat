package org.easyarch.netcat.test;

import org.easyarch.netcat.test.handler.*;
import org.easyarch.netpet.web.server.App;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:31
 * description:
 */

public class Application {

    public static void main(String[] args) {
        App app = new App();
        app.config().useCache();
        app.post("/index/{username}", new ParameterizeHandler())
                .get("/download",new DownLoadHandler())
                .get("/index/{username}",new JsonHandler())
                .get("/index/xingtianyu",new ImageHandler())
                .get("/redirect",new RedirectHandler())
                .get("/user/login",new LoginPageHandler())
                .post("/user/login",new LoginHandler())
                .post("/upload",new UpLoadHandler())
//                .filter("/*",new DemoFilter())
//                .post("/user/index",new IndexHandler())
                .start(8800);
    }
}
