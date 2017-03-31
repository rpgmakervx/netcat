package org.easyarch.netcat.test.handler;

import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.kits.file.FileKits;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;
import org.easyarch.netpet.web.mvc.entity.Json;
import org.easyarch.netpet.web.mvc.entity.UploadFile;
import org.easyarch.netcat.test.entity.User;

/**
 * Created by xingtianyu on 17-3-19
 * 下午10:54
 * description:
 */

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        System.out.println("login param:"+request.body(User.class));
        UploadFile uploadFile = request.file("file");
        System.out.println(uploadFile);
        FileKits.write("/home/code4j/IDEAWorkspace/netcat/"+uploadFile.getFileName(),uploadFile.getContent());
        response.json(Json.stringify(request.getParamMap()));
    }
}
