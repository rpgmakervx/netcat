package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.http.request.HandlerRequest;
import org.easyarch.netcat.http.response.HandlerResponse;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;
import org.easyarch.netcat.mvc.entity.Json;

/**
 * Created by xingtianyu on 17-3-19
 * 下午10:54
 * description:
 */

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        System.out.println("login param:"+request.getParameterMap());
        response.json(Json.stringify(request.getParameterMap()));
    }
}
