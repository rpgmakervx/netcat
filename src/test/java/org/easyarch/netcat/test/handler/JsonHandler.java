package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.http.request.HandlerRequest;
import org.easyarch.netcat.http.response.HandlerResponse;
import org.easyarch.netcat.http.session.HttpSession;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;
import org.easyarch.netcat.mvc.entity.Json;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:44
 * description:
 */

public class JsonHandler implements HttpHandler {
    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        HttpSession session = request.getSession();
        System.out.println("json handler session:"+session);
        response.json(new Json("message",session.getAttr("user"),"code",200));
    }
}
