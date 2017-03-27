package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.web.http.request.HandlerRequest;
import org.easyarch.netcat.web.http.response.HandlerResponse;
import org.easyarch.netcat.web.http.session.HttpSession;
import org.easyarch.netcat.web.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:32
 * description:
 */

public class IndexHandler implements HttpHandler{

    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        HttpSession session = request.getSession();
        System.out.println(request.getParamMap());
        response.html("index-v1");
    }
}
