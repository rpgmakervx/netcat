package org.easyarch.netcat.mvc.action.handler.impl;

import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-9
 * 下午3:51
 * description:
 */

public class ErrorHandler implements HttpHandler {

    private static final String HTTPSTATUS = "HTTPSTATUS";
    private static final String REASONPHASE = "REASONPHASE";


    private int code;

    private String message;

    public ErrorHandler(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        HandlerContext context = new HandlerContext();
        String webView = context.getWebView();
        StringBuffer resourcePath = new StringBuffer();
        resourcePath.append(webView).append(context.getErrorPage());
        request.setAttribute(HTTPSTATUS,code);
        request.setAttribute(REASONPHASE,message);
        response.html("error", code);
    }
}
