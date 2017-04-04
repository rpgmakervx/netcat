package org.easyarch.netpet.asynclient.http.response;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;
import org.easyarch.netpet.web.mvc.entity.Json;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * Created by xingtianyu on 17-4-4
 * 下午9:35
 * description:
 */

public interface AsyncHttpResponse {

    public String getHeader(String name);

    public Map<String,String> getAllHeaders();

    public HttpHeaders getHeaders();

    public byte[] getBytes();

    public ByteBuf getBuf();

    public ByteArrayOutputStream getStream();

    public String getString();

    public Json getJson();

    public int getStatusCode();

    public int getContentLength();


}
