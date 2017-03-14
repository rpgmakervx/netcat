package org.easyarch.netcat.context;

/**
 * Created by xingtianyu on 17-3-14
 * 下午4:32
 * description:
 */

public class Config {

    private HandlerContext context;

    public Config(HandlerContext context){
        this.context = context;
    }

    public Config webView(String webView){
        context.setWebView(webView);
        return this;
    }

    public Config contextPath(String contextPath){
        if (!contextPath.startsWith("/")){
            contextPath = "/" + contextPath;
        }
        if (!contextPath.endsWith("/")){
            contextPath = contextPath + "/";
        }
        context.setContextPath(contextPath);
        return this;
    }

    public Config cacheMaxAge(int maxAge){
        context.setMaxAge(maxAge);
        return this;
    }

    public Config useCache(){
        context.setNegoCache(true);
        return this;
    }

}
