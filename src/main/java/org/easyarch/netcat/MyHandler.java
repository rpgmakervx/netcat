package org.easyarch.netcat;

import org.easyarch.netcat.http.request.HttpHandlerRequest;
import org.easyarch.netcat.http.response.HttpHandlerResponse;
import org.easyarch.netcat.mvc.JsonHttpHandler;
import org.easyarch.netcat.mvc.entity.Json;

/**
 * Created by xingtianyu on 17-3-5
 * 上午2:18
 * description:
 */

public class MyHandler implements JsonHttpHandler {
    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        Json json = new Json();
        json.put("message","hello world");
    }
}
