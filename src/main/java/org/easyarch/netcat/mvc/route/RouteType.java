package org.easyarch.netcat.mvc.route;

import org.easyarch.netcat.mvc.route.filter.Filter;
import org.easyarch.netcat.mvc.route.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-3
 * 下午6:13
 * description:
 */

public enum  RouteType {

    FILTER,HANDLER;

    public static RouteType getType(Router router){
        if (router instanceof HttpHandler){
            return HANDLER;
        }
        if (router instanceof Filter){
            return FILTER;
        }
        return null;
    }
}
