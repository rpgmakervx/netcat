package org.easyarch.netcat.mvc.action.handler.impl;

import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.protocol.HttpHeaderValue;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.kits.file.FileKits;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

import static org.easyarch.netcat.context.HandlerContext.DEFAULT_RESOURCE;

/**
 * Created by xingtianyu on 17-3-9
 * 下午3:51
 * description:
 */

public class NotFoundHandler implements HttpHandler {

    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        HandlerContext context = new HandlerContext();
        String webView = context.getWebView();
        StringBuffer resourcePath = new StringBuffer();
        resourcePath.append(webView).append(context.getNotFoundPage());
        if (FileKits.exists(resourcePath.toString())){
            resourcePath = new StringBuffer();
            resourcePath.append(DEFAULT_RESOURCE).append(context.getNotFoundPage());
        }
        response.write(FileKits.read(resourcePath.toString()), HttpHeaderValue.TEXT_HTML);
    }
}
