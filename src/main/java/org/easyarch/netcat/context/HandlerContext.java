package org.easyarch.netcat.context;


import org.easyarch.netcat.http.session.HttpSession;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-2-28
 * 上午10:51
 * description:
 */

public class HandlerContext {
    public static final String DEFAULT_CONTEXT = File.separator;

    private static Map<String,HttpSession> sessionMap = new ConcurrentHashMap<>();

    public String contextPath = File.separator;
    public String realPath;
    public String webRoot = "/home/code4j/58daojia/技术/echarts-2.2.7/";

    public HandlerContext(){
    }

    public String getRealPath() {
        return realPath;
    }

    public String getContextPath(){
        return contextPath;
    }

    public String getWebRoot(){
        return webRoot;
    }

    public void setWebRoot(String root){
        this.webRoot = root;
    }

    public HttpSession getSession(String cookieId){
        return sessionMap.get(cookieId);
    }
}
