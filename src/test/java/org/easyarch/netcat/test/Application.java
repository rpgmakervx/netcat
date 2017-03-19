package org.easyarch.netcat.test;

import org.easyarch.netcat.server.App;
import org.easyarch.netcat.test.filter.DemoFilter;
import org.easyarch.netcat.test.handler.*;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:31
 * description:
 */

public class Application {

    public static void main(String[] args) {
        App app = new App();
        app.config().useCache();
        app.get("/user/index",new IndexHandler())
                .get("/index/{username}", new ParameterizeHandler())
                .get("/download",new DownLoadHandler())
                .get("/user/json",new JsonHandler())
                .get("/image",new ImageHandler())
                .get("/redirect",new RedirectHandler())
                .get("/user/login",new LoginPageHandler())
                .post("/user/login",new LoginHandler())
                .filter("*.html",new DemoFilter())
                .start(7070);
    }
}
