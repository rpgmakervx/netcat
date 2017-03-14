package org.easyarch.netcat.test.handler;

import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.kits.file.FileKits;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:45
 * description:
 */

public class ImageHandler implements HttpHandler {
    @Override
    public void handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        byte[] image = FileKits.read("/home/code4j/picture/04.jpeg");
        response.image(image);
    }
}
