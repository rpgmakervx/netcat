package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.http.request.HandlerRequest;
import org.easyarch.netcat.http.response.HandlerResponse;
import org.easyarch.netcat.http.session.HttpSession;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-19
 * 下午1:56
 * description:
 */

public class RedirectHandler implements HttpHandler {
    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        HttpSession session = request.getSession();
        System.out.println("session:"+session);
        session.setAttr("user","xingtianyu");
        response.redirect("/user/json");
    }
}
