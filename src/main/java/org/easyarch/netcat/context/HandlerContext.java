package org.easyarch.netcat.context;

import org.easyarch.netcat.mvc.handler.HttpHandler;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-28
 * 上午10:51
 * description:
 */

public class HandlerContext {
    public static final String DEFAULT_CONTEXT = File.separator;
    private Map<String,HttpHandler> handlers = new LinkedHashMap<>();
    public String contextPath;
    public String realPath;

    public void addHandler(String url,HttpHandler handler){
        handlers.put(url,handler);
    }

    public HttpHandler getHandler(String url){
        return handlers.get(url);
    }

    public String getRealPath() {
        return realPath;
    }

    public String getContextPath(){
        return contextPath;
    }
}
