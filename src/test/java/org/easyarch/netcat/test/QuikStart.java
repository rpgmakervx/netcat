package org.easyarch.netcat.test;

import org.easyarch.netcat.web.http.request.HandlerRequest;
import org.easyarch.netcat.web.http.response.HandlerResponse;
import org.easyarch.netcat.web.mvc.action.handler.HttpHandler;
import org.easyarch.netcat.web.mvc.entity.Json;
import org.easyarch.netcat.web.server.App;

/**
 * Created by xingtianyu on 17-3-28
 * 上午12:23
 * description:
 */

public class QuikStart {

    public static void main(String[] args) {
        App app = new App();
        app.get("/hello", new HttpHandler() {
            @Override
            public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
                response.json(new Json("message","hello world"));
            }
        }).start(9090);
    }
}
