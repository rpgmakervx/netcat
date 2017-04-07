package org.easyarch.netcat.test.handler;

import org.easyarch.netpet.kits.file.FileKits;
import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;
import org.easyarch.netpet.web.mvc.entity.Json;
import org.easyarch.netpet.web.mvc.entity.UploadFile;

/**
 * Created by xingtianyu on 17-4-7
 * 下午2:45
 * description:
 */

public class UpLoadHandler implements HttpHandler {

    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        UploadFile file = request.file("file");
        FileKits.write("/home/code4j/58daojia",file.getContent());
        response.json(new Json("code",200,"message","file saved complete!"));
    }
}
