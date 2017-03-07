package org.easyarch.netcat.mvc.action;

import org.easyarch.netcat.mvc.action.filter.Filter;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-3
 * 下午6:13
 * description:
 */

public enum ActionType {

    FILTER,HANDLER;

    public static ActionType getType(Action action){
        if (action instanceof HttpHandler){
            return HANDLER;
        }
        if (action instanceof Filter){
            return FILTER;
        }
        return null;
    }
}
