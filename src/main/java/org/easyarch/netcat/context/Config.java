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

    public void maxAge(int maxAge){
        context.setMaxAge(maxAge);
    }

    public void webView(String webView){
        context.setWebView(webView);
    }
}
