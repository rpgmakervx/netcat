package org.easyarch.netcat.http.response;

import io.netty.handler.codec.http.cookie.Cookie;
import org.easyarch.netcat.context.HandlerContext;

import java.util.Collection;
import java.util.Map;

/**
 * Created by xingtianyu on 17-3-9
 * 下午3:32
 * description:
 */

public interface HandlerResponse {


    public HandlerContext getHandlerContext();

    public void setHandlerContext(HandlerContext handlerContext);

    public void addCookie(Cookie cookie);

    public void setDateHeader(String name, long date);

    public void setHeader(String name, String value);

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

    public void write(byte[] content, String headerValue);

    public void text(String content);

    public void json(String json);

    public void json(Map<String,Object> json);

    public void html(String view);

    public void image(byte[] bytes);

    public void redirect(String url);

    public void download(byte[] bytes,String filename,String headerValue);

}
