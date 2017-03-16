package org.easyarch.netcat.test;

import org.easyarch.netcat.server.App;
import org.easyarch.netcat.test.filter.LoginFilter;
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
        app.get("/index",new IndexHandler())
                .get("/image",new ImageHandler())
                .get("/json",new JsonHandler())
                .filter(new LoginFilter())
                .get("/get/{username}", new ParameterizeHandler())
                .get("/download",new DownLoadHandler())
                .start(7070);
    }
}
