package org.easyarch.netcat.context;

import org.easyarch.netcat.mvc.HttpHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-2-28
 * 上午10:51
 * description:
 */

public class HandlerContext {
    public static Map<String,HttpHandler> handlers = new ConcurrentHashMap<>();

    public void addHandler(String url,HttpHandler handler){
        handlers.put(url,handler);
    }

    public HttpHandler getHandler(String url){
        return handlers.get(url);
    }

    public String getContextPath(){
        return "";
    }
}
