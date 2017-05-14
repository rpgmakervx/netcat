package org.easyarch.netcat.test;

import org.easyarch.netcat.test.handler.LoginPageHandler;
import org.easyarch.netcat.test.handler.UpLoadHandler;
import org.easyarch.netpet.web.server.App;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:31
 * description:
 */

public class Application {

    public static void main(String[] args) throws Exception {
//        AsyncHttpClient client = new AsyncHttpClient("http://localhost:8080");
//        client.get("/", new AsyncResponseHandlerAdapter() {
//            @Override
//            public void onSuccess(AsyncHttpResponse response) throws Exception {
//                System.out.println(response.getString());
//            }
//
//            @Override
//            public void onFailure(int statusCode, Object message) throws Exception {
//
//            }
//        });
        App app = new App();
//        app.config().contextPath("/shopping");
        app.get("/user/login", new LoginPageHandler())
                .post("/upload",new UpLoadHandler())
        .start(7001);
    }
}
