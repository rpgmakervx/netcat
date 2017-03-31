package org.easyarch.netpet.web.mvc.action;

import org.easyarch.netpet.web.mvc.action.filter.HttpFilter;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;

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
        if (action instanceof HttpFilter){
            return FILTER;
        }
        return null;
    }
}
