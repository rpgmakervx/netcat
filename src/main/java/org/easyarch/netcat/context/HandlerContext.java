package org.easyarch.netcat.context;


import org.easyarch.netcat.mvc.route.RouteWrapper;

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
    private Map<String,RouteWrapper> routers = new LinkedHashMap<>();
    public String contextPath;
    public String realPath;

    public void addRouter(String path,RouteWrapper router){
        routers.put(path,router);
    }

    public RouteWrapper getRouter(String path){
        return routers.get(path);
    }

    public String getRealPath() {
        return realPath;
    }

    public String getContextPath(){
        return contextPath;
    }
}
