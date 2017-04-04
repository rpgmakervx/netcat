package org.easyarch.netpet.asynclient.http.response.impl;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import org.easyarch.netpet.asynclient.http.response.AsyncHttpResponse;
import org.easyarch.netpet.kits.ByteKits;
import org.easyarch.netpet.web.mvc.entity.Json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xingtianyu on 17-4-4
 * 下午9:51
 * description:
 */

public class AsyncHttpResponseImpl implements AsyncHttpResponse {

    private FullHttpRequest request;
    private HttpHeaders headers;

    private ByteBuf buf;

    public AsyncHttpResponseImpl(FullHttpRequest request){
        this.request = request;
        this.headers = request.headers();
        this.buf = request.content().copy();
    }

    @Override
    public String getHeader(String name) {
        return this.headers.get(name);
    }

    @Override
    public Map<String, String> getAllHeaders() {
        Map<String,String> headers = new HashMap<>();
        for (String name:this.headers.names()){
            String value = this.headers.get(name);
            headers.put(name,value);
        }
        return headers;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.headers;
    }

    @Override
    public byte[] getBytes() {
        return ByteKits.toByteArray(this.buf);
    }

    @Override
    public ByteBuf getBuf() {
        return this.buf;
    }

    @Override
    public ByteArrayOutputStream getStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos;
    }

    @Override
    public String getString() {
        return ByteKits.toString(this.buf);
    }

    @Override
    public Json getJson() {
        String string = getString();
        return Json.parse(string);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }

    @Override
    public int getContentLength() {
        return 0;
    }
}
