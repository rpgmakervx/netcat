package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:32
 * description:
 */

public class IndexHandler implements HttpHandler{

    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        response.html("index-v1");
    }
}
