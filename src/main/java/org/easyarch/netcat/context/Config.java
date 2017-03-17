package org.easyarch.netcat.context;

import org.easyarch.netcat.kits.StringKits;

import java.io.File;

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
        if (!webView.startsWith("/")){
            webView = "/" + webView;
        }
        context.setWebView(webView);
        return this;
    }

    public Config viewPrefix(String viewPrefix){
        if (StringKits.isNotEmpty(viewPrefix)&&!viewPrefix.startsWith(File.separator)){
            viewPrefix = "/" + viewPrefix;
        }
        context.setViewPrefix(viewPrefix);
        return this;
    }

    public Config viewSuffix(String viewSuffix){
        context.setViewSuffix(viewSuffix);
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
