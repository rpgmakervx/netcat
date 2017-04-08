package org.easyarch.netpet.asynclient.http.response.impl;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import org.easyarch.netpet.asynclient.http.response.AsyncHttpResponse;
import org.easyarch.netpet.kits.ByteKits;
import org.easyarch.netpet.kits.StringKits;
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

    private FullHttpResponse response;
    private HttpHeaders headers;

    private ByteBuf buf;

    public AsyncHttpResponseImpl(FullHttpResponse response){
        this.response = response;
        this.headers = response.headers();
        this.buf = response.content().copy();
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
        return response.status().code();
    }

    @Override
    public int getContentLength() {
        String contentLength = getHeader(
                HttpHeaderNames.CONTENT_LENGTH.toString());
        if (StringKits.isEmpty(contentLength)){
            return 0;
        }
        return Integer.parseInt(contentLength);
    }

    @Override
    public FullHttpResponse getResponse() {
        return response;
    }
}
