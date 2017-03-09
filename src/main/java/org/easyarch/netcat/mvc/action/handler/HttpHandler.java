package org.easyarch.netcat.mvc.action.handler;

import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.Action;

/**
 * Description :
 * Created by xingtianyu on 17-2-27
 * 上午12:11
 * description:
 */

public interface HttpHandler extends Action {

    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception;
}
