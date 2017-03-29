package org.easyarch.netcat.web.mvc.action.handler.impl;

import org.easyarch.netcat.web.http.protocol.HttpStatus;
import org.easyarch.netcat.web.http.request.HandlerRequest;
import org.easyarch.netcat.web.http.response.HandlerResponse;
import org.easyarch.netcat.web.mvc.action.handler.HttpHandler;

import static org.easyarch.netcat.web.http.Const.*;

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
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        response.addModel(HTTPSTATUS,code);
        response.addModel(REASONPHASE,reasonPhase);
        response.addModel(MESSAGE,message);
        switch (code){
            case HttpStatus.NOT_FOUND:
                response.notFound();
                return;
            case HttpStatus.INTERNAL_SERVER_ERROR:
                response.serverError();
                return;
        }
        response.error(code);
    }
}
