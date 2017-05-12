package org.easyarch.netcat.test;

import org.easyarch.netcat.test.handler.IndexHandler;
import org.easyarch.netcat.test.handler.LoginHandler;
import org.easyarch.netcat.test.handler.LoginPageHandler;
import org.easyarch.netpet.web.http.request.impl.HttpHandlerRequest;
import org.easyarch.netpet.web.http.response.impl.HttpHandlerResponse;
import org.easyarch.netpet.web.mvc.action.filter.HttpFilter;
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
        app.get("/user/login.html", new LoginPageHandler())
                .get("/user/{username}",new IndexHandler())
                .post("/user/*",new LoginHandler())
                .filter("/user/*", new HttpFilter() {
                    @Override
                    public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
                        return true;
                    }

                    @Override
                    public void after(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {

                    }
                })
        .start(7001);
    }
}
