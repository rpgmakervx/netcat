package org.easyarch.netpet.web.context.config;

import org.easyarch.netpet.kits.StringKits;
import org.easyarch.netpet.web.context.HandlerContext;

import java.io.File;

/**
 * Created by xingtianyu on 17-3-14
 * 下午4:32
 * description:
 */

public class HandlerConfig {

    private HandlerContext context;


    public HandlerConfig(HandlerContext context){
        this.context = context;
    }

    public HandlerConfig webView(String webView){
        if (StringKits.isNotEmpty(webView)
                &&!webView.startsWith(File.separator)){
            webView = "/" + webView;
        }
        if (StringKits.isNotEmpty(webView)
                &&!webView.endsWith(File.separator)){
            webView = webView + "/";
        }
        context.setWebView(webView);
        return this;
    }

    public HandlerConfig viewPrefix(String viewPrefix){
        if (StringKits.isNotEmpty(viewPrefix)
                &&!viewPrefix.startsWith(File.separator)){
            viewPrefix = "/" + viewPrefix;
        }
        if (StringKits.isNotEmpty(viewPrefix)
                &&!viewPrefix.endsWith(File.separator)){
            viewPrefix = viewPrefix + "/";
        }
        context.setViewPrefix(viewPrefix);
        return this;
    }

    public HandlerConfig viewSuffix(String viewSuffix){
        context.setViewSuffix(viewSuffix);
        return this;
    }

    public HandlerConfig contextPath(String contextPath){
        if (!contextPath.startsWith("/")){
            contextPath = "/" + contextPath;
        }
        if (!contextPath.endsWith("/")){
            contextPath = contextPath + "/";
        }
        context.setContextPath(contextPath);
        return this;
    }

    public HandlerConfig cacheMaxAge(int maxAge){
        context.setMaxAge(maxAge);
        return this;
    }

    public HandlerConfig useCache(){
        context.setNegoCache(true);
        return this;
    }

    public HandlerConfig maxFileUpload(long maxFileUpload){
        context.setMaxFileUpload(maxFileUpload);
        return this;
    }

    public HandlerConfig errorPage(String view){
        context.setErrorPage(view);
        return this;
    }

    public HandlerConfig notFound(String view){
        context.setNotFound(view);
        return this;
    }

    public HandlerConfig serverError(String view){
        context.setServerError(view);
        return this;
    }

    public HandlerConfig globalConfig(String name,String value){
        context.globalConfig(name,value);
        return this;
    }

    public Object globalConfig(String name){
        return context.globalConfig(name);
    }
}
