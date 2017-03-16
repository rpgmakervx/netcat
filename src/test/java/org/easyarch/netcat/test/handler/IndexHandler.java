package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.http.cookie.HttpCookie;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

import java.util.Set;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:32
 * description:
 */

public class IndexHandler implements HttpHandler{

    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        Set<HttpCookie> cookies = request.getCookies();
        for (HttpCookie cookie:cookies){
            System.out.println("cookie name:"+cookie.getWrapper().name());
        }
        response.html("index-v1");
    }
}
