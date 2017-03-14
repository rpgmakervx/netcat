package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:46
 * description:
 */

public class ParameterizeHandler implements HttpHandler {
    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        response.setAttribute("username",request.getParameter("username"));
        response.html("hello");
    }
}
