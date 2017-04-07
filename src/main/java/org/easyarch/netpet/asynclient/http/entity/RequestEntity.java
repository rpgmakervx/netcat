package org.easyarch.netpet.asynclient.http.entity;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import org.easyarch.netpet.web.mvc.entity.UploadFile;

import java.util.HashMap;
import java.util.Map;

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

    private Map<String,UploadFile> files = new HashMap<>();

    public RequestEntity(String path, HttpMethod method, HttpHeaders headers, ByteBuf buf) {
        this.path = path;
        this.method = method;
        this.headers = headers;
        this.buf = buf;
    }
    public RequestEntity(String path, HttpMethod method, HttpHeaders headers, Map<String,UploadFile> files) {
        this.path = path;
        this.method = method;
        this.headers = headers;
        this.files.putAll(files);
    }
    public RequestEntity(String path, HttpMethod method, HttpHeaders headers, String name,UploadFile file) {
        this.path = path;
        this.method = method;
        this.headers = headers;
        this.files.put(name,file);
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

    public Map<String, UploadFile> getFiles() {
        return files;
    }

    public void setFiles(Map<String, UploadFile> files) {
        this.files = files;
    }
}
