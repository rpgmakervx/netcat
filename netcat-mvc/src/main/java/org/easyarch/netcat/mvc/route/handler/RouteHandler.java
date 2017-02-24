package org.easyarch.netcat.mvc.route.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description :
 * Created by xingtianyu on 17-2-24
 * 上午1:41
 * description:
 */

public interface RouteHandler {

    public<T> T handle(HttpServletRequest request, HttpServletResponse response);
}
