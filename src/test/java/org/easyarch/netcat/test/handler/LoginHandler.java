package org.easyarch.netcat.test.handler;

import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-19
 * 下午10:54
 * description:
 */

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
//        System.out.println("login param:"+request.body(User.class));
//        UploadFile uploadFile = request.file("file");
//        System.out.println(uploadFile);
        System.out.println("request json:"+request.getJson());
        System.out.println("request map:"+request.getParamMap());
        response.json(request.getJson());
    }
}
