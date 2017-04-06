package org.easyarch.netpet.asynclient.http.entity;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;

/**
 * Created by xingtianyu on 17-4-5
 * 下午7:38
 * description:
 */

public class RequestEntity {
    private String path;

    private HttpMethod method;

    private HttpHeaders headers;

    private ByteBuf buf;

    public RequestEntity(String path, HttpMethod method, HttpHeaders headers, ByteBuf buf) {
        this.path = path;
        this.method = method;
        this.headers = headers;
        this.buf = buf;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public ByteBuf getBuf() {
        return buf;
    }

    public void setBuf(ByteBuf buf) {
        this.buf = buf;
    }
}
