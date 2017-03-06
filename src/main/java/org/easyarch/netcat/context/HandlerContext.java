package org.easyarch.netcat.context;


import java.io.File;

/**
 * Description :
 * Created by xingtianyu on 17-2-28
 * 上午10:51
 * description:
 */

public class HandlerContext {
    public static final String DEFAULT_CONTEXT = File.separator;
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

}
