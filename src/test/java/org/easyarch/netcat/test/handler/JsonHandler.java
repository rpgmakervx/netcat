package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.web.http.request.HandlerRequest;
import org.easyarch.netcat.web.http.response.HandlerResponse;
import org.easyarch.netcat.web.http.session.HttpSession;
import org.easyarch.netcat.web.mvc.action.handler.HttpHandler;
import org.easyarch.netcat.web.mvc.entity.Json;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:44
 * description:
 */

public class JsonHandler implements HttpHandler {
    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        HttpSession session = request.getSession();
        System.out.println("username:"+request.getParam("username"));
        response.json(new Json("message",request.getParam("username"),"code",200));
    }
}
