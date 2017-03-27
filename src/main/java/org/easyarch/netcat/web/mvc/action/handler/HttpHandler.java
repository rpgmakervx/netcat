package org.easyarch.netcat.web.mvc.action.handler;

import org.easyarch.netcat.web.http.request.HandlerRequest;
import org.easyarch.netcat.web.http.response.HandlerResponse;
import org.easyarch.netcat.web.mvc.action.Action;

/**
 * Description :
 * Created by xingtianyu on 17-2-27
 * 上午12:11
 * description:
 */

public interface HttpHandler extends Action {

    public void handle(HandlerRequest request, HandlerResponse response) throws Exception;
}
