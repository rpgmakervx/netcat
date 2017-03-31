package org.easyarch.netcat.test.handler;

import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.kits.file.FileKits;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:45
 * description:
 */

public class ImageHandler implements HttpHandler {
    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        byte[] image = FileKits.read("/home/code4j/picture/04.jpeg");
        response.image(image);
    }
}
