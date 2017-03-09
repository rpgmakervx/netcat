package org.easyarch.netcat.http.response.impl;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.cookie.Cookie;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.protocol.HttpHeaderValue;
import org.easyarch.netcat.http.response.HandlerResponse;
import org.easyarch.netcat.kits.ByteUtil;
import org.easyarch.netcat.kits.JsonKits;
import org.easyarch.netcat.kits.StringKits;
import org.easyarch.netcat.kits.file.FileKits;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;

import static org.easyarch.netcat.http.Const.NETCATID;

/**
 * Description :
 * Created by xingtianyu on 17-2-24
 * 下午5:23
 * description:
 */

public class HttpHandlerResponse implements HandlerResponse {

    private FullHttpResponse response;
    private Channel channel;
    private HttpHeaders headers;
    private String charset;

    public HandlerContext handlerContext;

    public HttpHandlerResponse(FullHttpResponse response, Channel channel) {
        this.response = response;
        this.headers = this.response.headers();
        this.charset = "UTF-8";
        this.channel = channel;
    }

    public HandlerContext getHandlerContext() {
        return handlerContext;
    }

    public void setHandlerContext(HandlerContext handlerContext) {
        this.handlerContext = handlerContext;
    }

    public void addCookie(Cookie cookie) {
        this.headers.add(NETCATID, cookie);
    }

    public void setDateHeader(String name, long date) {
        this.headers.add(name, date);
    }

    public void setHeader(String name, String value) {
        this.headers.set(name, value);
    }

    public void setStatus(int code) {
        response.setStatus(HttpResponseStatus.valueOf(code));
    }

    public void setStatus(int code, String msg) {
        response.setStatus(new HttpResponseStatus(code, msg));
    }

    public int getStatus() {
        return response.status().code();
    }


    public String getHeader(String name) {
        String headr = this.headers.get(name);
        return encode(headr);
    }


    public Collection<String> getHeaderNames() {
        return this.headers.names();
    }


    public String getCharacterEncoding() {
        return this.charset;
    }


    public String getContentType() {
        return this.headers.get(HttpHeaderNames.CONTENT_TYPE);
    }


    public void setCharacterEncoding(String charset) {
        this.charset = charset;
    }


    public void setContentLength(int len) {
        this.headers.set(HttpHeaderNames.CONTENT_LENGTH, len);
    }


    public void setContentType(String type) {
        this.headers.set(HttpHeaderNames.CONTENT_TYPE, type);
    }

    private String encode(String content) {
        if (StringKits.isEmpty(content)) {
            return null;
        }
        try {
            return new String(content.getBytes(), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }
    }

    public void write(byte[] content, String headerValue) {
        setHeader(HttpHeaderNames.CONTENT_TYPE.toString(), headerValue);
        setHeader(HttpHeaderNames.CONTENT_LENGTH.toString(), String.valueOf(content.length));
        response = response.copy(ByteUtil.toByteBuf(content));
        channel.writeAndFlush(response);
    }

    public void text(String content) {
        write(content.getBytes(), HttpHeaderValue.TEXT_PLAIN);
    }

    public void json(String json) {
        write(json.getBytes(), HttpHeaderValue.APPLICATION_JSON);
    }
    public void json(Map<String,Object> json) {
        json(JsonKits.toString(json));
    }

    public void html(String view) {
        StringBuffer pathBuffer = new StringBuffer();
        pathBuffer.append(handlerContext.getWebView())
                .append(view)
                .append(handlerContext.getViewPrefix())
                .append(handlerContext.getViewSuffix());
        try {
            byte[] content = FileKits.read(pathBuffer.toString());
            write(content,HttpHeaderValue.TEXT_HTML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void image(byte[] bytes){
        write(bytes,HttpHeaderValue.IMAGE);
    }

    public void redirect(String url){
        response.setStatus(HttpResponseStatus.FOUND);
        setHeader(HttpHeaderNames.LOCATION.toString(),url);
    }

    public void download(byte[] bytes,String filename,String headerValue){
        String fn = filename;
        try {
            fn = new String(filename.getBytes("GB2312"),"ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setHeader(HttpHeaderNames.CONTENT_DISPOSITION.toString(),HttpHeaderValue.ATTACHMENT + fn);
        write(bytes,headerValue.toString());
    }

}
