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
    public static final String DEFAULT_SUFFIX = "html";

    private static Map<String,HttpSession> sessionMap = new ConcurrentHashMap<>();

    public String contextPath = File.separator;
    public String realPath;
    /**
     * web资源路径
     */
    public String webView = "/home/code4j/58daojia/技术/echarts-2.2.7/";
    /**
     * 视图资源路径前缀
     */
    public String viewPrefix;
    /**
     * 视图资源文件后缀（默认html）
     */
    public String viewSuffix = DEFAULT_SUFFIX;

    public HandlerContext(){
    }

    public String getRealPath() {
        return realPath;
    }

    public String getContextPath(){
        return contextPath;
    }

    public String getWebView(){
        return webView;
    }

    public void setWebView(String root){
        this.webView = root;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getViewPrefix() {
        return viewPrefix;
    }

    public void setViewPrefix(String viewPrefix) {
        this.viewPrefix = viewPrefix;
    }

    public String getViewSuffix() {
        return viewSuffix;
    }

    public void setViewSuffix(String viewSuffix) {
        this.viewSuffix = viewSuffix;
    }

    public HttpSession getSession(String cookieId){
        return sessionMap.get(cookieId);
    }

    public void setSession(String cookieId,HttpSession session){
        sessionMap.put(cookieId,session);
    }
}
