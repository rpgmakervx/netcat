package org.easyarch.netcat.test.handler;

import org.easyarch.netpet.web.http.protocol.HttpHeaderValue;
import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.kits.file.FileKits;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:41
 * description:
 */

public class DownLoadHandler implements HttpHandler {

    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        byte[] pdf = FileKits.read("/home/code4j/dumps/mybatis-plus.pdf");
        response.download(pdf,"mybatis权威指南", HttpHeaderValue.PDF);
    }
}
