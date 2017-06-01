package org.easyarch.netcat.test.handler;

import org.easyarch.netpet.web.http.cookie.HttpCookie;
import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.web.http.session.HttpSession;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:32
 * description:
 */

public class IndexHandler implements HttpHandler{

    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.addCookie(new HttpCookie("username","xingtianyu"));
        System.out.println("session 值："+session);
        String username = request.getParam("username");
//        response.addModel("username",username);
        session.setAttr("username",username);
        response.html("hello");
    }
}
