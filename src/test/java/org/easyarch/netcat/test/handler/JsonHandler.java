package org.easyarch.netcat.test.handler;

import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.web.http.session.HttpSession;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;
import org.easyarch.netpet.web.mvc.entity.Json;

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
