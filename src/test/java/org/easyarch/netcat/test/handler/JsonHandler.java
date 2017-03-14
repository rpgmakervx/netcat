package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;
import org.easyarch.netcat.mvc.entity.Json;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:44
 * description:
 */

public class JsonHandler implements HttpHandler {
    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        response.json(new Json("message","ok","code",200));
    }
}
