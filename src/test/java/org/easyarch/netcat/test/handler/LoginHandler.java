package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.http.request.HandlerRequest;
import org.easyarch.netcat.http.response.HandlerResponse;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;
import org.easyarch.netcat.mvc.entity.Json;
import org.easyarch.netcat.test.entity.User;

/**
 * Created by xingtianyu on 17-3-19
 * 下午10:54
 * description:
 */

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        System.out.println("login param:"+request.body(User.class));
        System.out.println("file length:"+request.file("file").length);
        response.json(Json.stringify(request.getParameterMap()));
    }
}
