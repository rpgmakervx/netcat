package org.easyarch.netcat.http.response;

import io.netty.handler.codec.http.FullHttpResponse;

import javax.servlet.http.Cookie;
import java.util.Collection;

/**
 * Description :
 * Created by xingtianyu on 17-2-24
 * 下午5:23
 * description:
 */

public class HttpResponse {

    private FullHttpResponse response;

    public HttpResponse(FullHttpResponse response) {
        this.response = response;
    }

    public void addCookie(Cookie cookie) {

    }

    public boolean containsHeader(String name) {
        return false;
    }

    public String encodeURL(String url) {
        return null;
    }

    
    public String encodeUrl(String url) {
        return null;
    }

    
    public String encodeRedirectUrl(String url) {
        return null;
    }


    public void setDateHeader(String name, long date) {

    }

    
    public void addDateHeader(String name, long date) {

    }

    
    public void setHeader(String name, String value) {

    }

    
    public void addHeader(String name, String value) {

    }

    
    public void setIntHeader(String name, int value) {

    }

    public void addIntHeader(String name, int value) {

    }

    public void setStatus(int sc) {

    }

    
    public void setStatus(int sc, String sm) {

    }

    public int getStatus() {
        return 0;
    }

    
    public String getHeader(String name) {
        return null;
    }

    
    public Collection<String> getHeaders(String name) {
        return null;
    }

    
    public Collection<String> getHeaderNames() {
        return null;
    }

    
    public String getCharacterEncoding() {
        return null;
    }

    
    public String getContentType() {
        return null;
    }


    public void setCharacterEncoding(String charset) {

    }

    
    public void setContentLength(int len) {

    }

    
    public void setContentLengthLong(long len) {

    }

    
    public void setContentType(String type) {

    }

    
    public void setBufferSize(int size) {

    }

    
    public int getBufferSize() {
        return 0;
    }

}
