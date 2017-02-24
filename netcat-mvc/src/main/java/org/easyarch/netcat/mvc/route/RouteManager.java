package org.easyarch.netcat.mvc.route;

import org.easyarch.netcat.http.protocol.HttpMethod;
import org.easyarch.netcat.mvc.route.handler.RouteHandler;
import org.easyarch.netcat.utils.StringUtils;

import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-24
 * 上午11:03
 * description:
 */

public class RouteManager {

    public static final String SEPARATOR = "#";

    private Map<String, Router> routers;

    private Map<String, Router> interceptors;

    public void addRouter(String path, HttpMethod httpMethod, RouteHandler handler) {
        if (StringUtils.isEmpty(path)
                || httpMethod == null
                || handler == null) {
            return;
        }
        Router router = new Router(path, httpMethod, handler);
        if (HttpMethod.AFTER.equals(httpMethod)
                || HttpMethod.BEFORE.equals(httpMethod)) {
            interceptors.put(path + SEPARATOR + httpMethod, router);
        } else {
            routers.put(path + SEPARATOR + httpMethod, router);
        }
    }

    public Router getRouter(String path, HttpMethod httpMethod) {
        return routers.get(path + SEPARATOR + httpMethod);
    }

}
