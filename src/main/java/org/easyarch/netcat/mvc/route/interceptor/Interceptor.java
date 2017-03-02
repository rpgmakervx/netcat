package org.easyarch.netcat.mvc.route.interceptor;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.easyarch.netcat.mvc.route.Router;

/**
 * Created by xingtianyu on 17-3-2
 * 下午5:01
 * description:
 */

public interface Interceptor extends Router {

    void before(HttpRequest request, HttpResponse response);
    void after(HttpRequest request, HttpResponse response);
}
