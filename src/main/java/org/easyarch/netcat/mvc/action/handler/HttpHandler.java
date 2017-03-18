package org.easyarch.netcat.mvc.action.handler;

import org.easyarch.netcat.http.request.HandlerRequest;
import org.easyarch.netcat.http.response.HandlerResponse;
import org.easyarch.netcat.mvc.action.Action;

/**
 * Description :
 * Created by xingtianyu on 17-2-27
 * 上午12:11
 * description:
 */

public interface HttpHandler extends Action {

    public void handle(HandlerRequest request, HandlerResponse response) throws Exception;
}
