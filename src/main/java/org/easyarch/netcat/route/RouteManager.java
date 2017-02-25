package org.easyarch.netcat.route;

import org.easyarch.netcat.http.protocol.HttpMethod;
import org.easyarch.netcat.route.handler.RouteHandler;
import org.easyarch.netcat.utils.StringUtils;

import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-24
 * 上午11:03
 * description:
 */

public class RouteManager {

    private Map<String,Router> routers;

    private Map<String,Router> interceptors;

    public void addRoute(String path, HttpMethod httpMethod, RouteHandler handler){
        if (StringUtils.isEmpty(path)
                ||httpMethod == null
                ||handler == null){
            return;
        }
        Router router = new Router(path,httpMethod,handler);
        if (HttpMethod.AFTER.equals(httpMethod)
                ||HttpMethod.BEFORE.equals(httpMethod)){
            interceptors.put(path,router);
        }else {
            routers.put(path,router);
        }
    }

}
