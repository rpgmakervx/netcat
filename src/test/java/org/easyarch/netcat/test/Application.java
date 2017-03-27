package org.easyarch.netcat.test;

import org.easyarch.netcat.web.server.App;
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
        app.post("/user/index",new IndexHandler())
                .post("/index/{username}", new ParameterizeHandler())
                .get("/download",new DownLoadHandler())
                .get("/user/{username}",new JsonHandler())
                .get("/image",new ImageHandler())
                .get("/redirect",new RedirectHandler())
                .get("/user/login",new LoginPageHandler())
                .post("/user/login",new LoginHandler())
                .filter("*.html",new DemoFilter())
                .start(8800);
    }
}
