package org.easyarch.netcat.mvc.handler;

import org.easyarch.netcat.http.request.HttpHandlerRequest;
import org.easyarch.netcat.http.response.HttpHandlerResponse;

/**
 * Description :
 * Created by xingtianyu on 17-2-27
 * 上午12:11
 * description:
 */

public interface HttpHandler<T> {

    public T handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception;
}
