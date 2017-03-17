package org.easyarch.netcat.mvc.action.handler.impl;

import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

import static org.easyarch.netcat.http.Const.HTTPSTATUS;
import static org.easyarch.netcat.http.Const.MESSAGE;
import static org.easyarch.netcat.http.Const.REASONPHASE;

/**
 * Created by xingtianyu on 17-3-9
 * 下午3:51
 * description:
 */

public class ErrorHandler implements HttpHandler {



    private int code;

    private String reasonPhase;

    private String message = "";

    public ErrorHandler(int code, String reasonPhase) {
        this.code = code;
        this.reasonPhase = reasonPhase;
    }

    public void setMessage(String message){
        if (message != null){
            this.message = message;
        }
    }

    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        HandlerContext context = new HandlerContext();
        response.setAttribute(HTTPSTATUS,code);
        response.setAttribute(REASONPHASE,reasonPhase);
        response.setAttribute(MESSAGE,message);
        response.html(context.getErrorPage(), code);
    }
}
