package org.easyarch.netcat.mvc.action.handler;

import org.easyarch.netcat.http.request.HttpHandlerRequest;
import org.easyarch.netcat.http.response.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.Action;

/**
 * Description :
 * Created by xingtianyu on 17-2-27
 * 上午12:11
 * description:
 */

public interface HttpHandler<T> extends Action {

    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception;
}
