package org.easyarch.netcat.http.response;

import io.netty.handler.codec.http.cookie.Cookie;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.mvc.entity.Json;

import java.util.Collection;
import java.util.Map;

/**
 * Created by xingtianyu on 17-3-9
 * 下午3:32
 * description:
 */

public interface HandlerResponse {


    public HandlerContext getContext();

    public void setContext(HandlerContext context);

    public void addCookie(Cookie cookie);

    public void setDateHeader(String name, long date);

    public void setHeader(String name, String value);
    public void addHeader(String name, String value);

    public void setStatus(int code) ;

    public void setStatus(int code, String msg) ;

    public int getStatus();

    public String getHeader(String name);

    public Collection<String> getHeaderNames();


    public String getCharacterEncoding() ;

    public String getContentType();


    public void setCharacterEncoding(String charset);

    public void setContentLength(int len);


    public void setContentType(String type) ;

    public void write(byte[] content, String headerValue,int statusCode);

    public void write(byte[] content, String headerValue);
    public void write(byte[] content);
    public void write();

    public void text(String content);

    public void json(byte[] json);

    public void json(String json);

    public void json(Map<String,Object> json);

    public void json(Json json);

    public void html(String view);

    public void notFound(String view);

    public void serverError(String view);

    public void image(byte[] bytes);

    public void redirect(String url);

    public void download(byte[] bytes,String filename,String headerValue);

}
