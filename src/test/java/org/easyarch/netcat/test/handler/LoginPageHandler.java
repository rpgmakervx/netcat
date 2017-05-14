package org.easyarch.netcat.test.handler;

import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-19
 * 下午10:55
 * description:
 */

public class LoginPageHandler implements HttpHandler {
    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        response.html("login");
    }
}
