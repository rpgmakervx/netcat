package org.easyarch.netcat.mvc;

import org.easyarch.netcat.http.request.HttpRequest;
import org.easyarch.netcat.http.response.HttpResponse;

/**
 * Description :
 * Created by xingtianyu on 17-2-27
 * 上午12:11
 * description:
 */

public interface HttpHandler<T> {

    public T handle(HttpRequest request, HttpResponse response) throws Exception;
}
