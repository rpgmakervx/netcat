package org.easyarch.netcat.mvc.action.filter;

import org.easyarch.netcat.http.request.HttpHandlerRequest;
import org.easyarch.netcat.http.response.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.Action;

/**
 * Created by xingtianyu on 17-3-2
 * 下午5:01
 * description:
 */

public interface Filter extends Action {

    boolean before(HttpHandlerRequest request, HttpHandlerResponse response);
    boolean after(HttpHandlerRequest request, HttpHandlerResponse response);
}
