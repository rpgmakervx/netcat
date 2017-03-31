package org.easyarch.netpet.web.mvc.action.handler.impl;

import org.easyarch.netpet.web.http.protocol.HttpStatus;
import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;
import org.easyarch.netpet.web.http.Const;

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
        response.addModel(Const.HTTPSTATUS,code);
        response.addModel(Const.REASONPHASE,reasonPhase);
        response.addModel(Const.MESSAGE,message);
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
