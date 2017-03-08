package org.easyarch.netcat.http.request;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.session.HttpSession;
import org.easyarch.netcat.kits.StringKits;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-2-24
 * 下午5:19
 * description:
 */

public class HttpHandlerRequest {

    private FullHttpRequest request;
    private HttpHeaders headers;
    private Channel channel;

    private HandlerContext handlerContext;
    private Map<String,Object> attributes = new ConcurrentHashMap<>();

    private String charset;

    private ServerCookieDecoder decoder;
    private static final String QUESTION = "?";
    private static final String NETCATID = "NETCATID";
    public HttpHandlerRequest(FullHttpRequest request, Channel channel){
        this.request = request;
        this.headers = request.headers();
        this.channel = channel;
        this.decoder = ServerCookieDecoder.LAX;
        this.charset = "UTF-8";
    }

    public HandlerContext getHandlerContext() {
        return handlerContext;
    }

    public void setHandlerContext(HandlerContext handlerContext) {
        this.handlerContext = handlerContext;
    }

    public Set<Cookie> getCookies() {
        String cookieVallue = this.headers.get(HttpHeaderNames.COOKIE);
        Set<Cookie> cookie = decoder.decode(cookieVallue);
        return cookie;
    }


    public String getHeader(String name) {
        if (this.headers == null){
            return "";
        }
        String header = headers.get(name);
        return encode(header);
    }

    public Collection<String> getHeaderNames() {
        if (this.headers == null){
            return new ArrayList<>();
        }
        return this.headers.names();
    }

   
    public Long getDateHeader(String name) {
        if (this.headers == null){
            return null;
        }
        return this.headers.getTimeMillis(name);
    }

   
    public String getMethod() {
        return request.method().name();
    }

    public String getContextPath() {
        return handlerContext.getContextPath();
    }

   
    public String getQueryString() {
        String url = request.uri();
        return checkURL(url);
    }

    private String checkURL(String url){
        if (StringKits.isEmpty(url)){
            return null;
        }
        int index = url.lastIndexOf(QUESTION);
        if (index == -1){
            return null;
        }
        String queryString = url.substring(url.lastIndexOf(QUESTION) + 1,url.length());
        return encode(queryString);
    }

    private String encode(String content){
        if (StringKits.isEmpty(content)){
            return null;
        }
        try {
            return new String(content.getBytes(),charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }
    }
    public SocketAddress getRemoteAddress() {
        return channel.remoteAddress();
    }

    public String getRequestedSessionId() {
        return null;
    }

   
    public String getRequestURI() {
        return request.uri();
    }

    public HttpSession getSession() {
        String sessionId = "";
        for(Cookie cookie:getCookies()){
            if (NETCATID.equals(cookie.name())){
                sessionId = cookie.value();
            }
        }
        return handlerContext.getSession(sessionId);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

   
    public Collection<String> getAttributeNames() {
        return attributes.keySet();
    }

   
    public String getCharacterEncoding() {
        return this.charset;
    }

   
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        this.charset = Charset.forName(env).name();
    }

   
    public int getContentLength() {
        String length = getHeader(HttpHeaderNames.CONTENT_LENGTH.toString());
        if (StringKits.isEmpty(length)) {
            return 0;
        }
        return Integer.parseInt(length);
    }


    public String getContentType() {
        String length = this.headers.get(HttpHeaderNames.CONTENT_TYPE);
        if (StringKits.isEmpty(length)){
            return null;
        }
        return length;
    }


    public String getParameter(String name) {

        return null;
    }

   
    public List<String> getParameterNames() {
        return null;
    }

   
    public String[] getParameterValues(String name) {
        return new String[0];
    }

   
    public Map<String, String[]> getParameterMap() {
        return null;
    }

   
    public String getProtocol() {
        return null;
    }

   
    public String getScheme() {
        return null;
    }

   
    public String getServerName() {
        return null;
    }

   
    public int getServerPort() {
        return 0;
    }

   
    public BufferedReader getReader() throws IOException {
        return null;
    }

   
    public String getRemoteAddr() {
        return null;
    }

   
    public String getRemoteHost() {
        return null;
    }

   
    public void setAttribute(String name, Object o) {

    }

    public void removeAttribute(String name) {

    }

    public boolean isSecure() {
        return false;
    }

    public String getRealPath(String path) {
        return null;
    }

    public int getRemotePort() {
        return 0;
    }

   
    public String getLocalName() {
        return null;
    }

   
    public String getLocalAddr() {
        return null;
    }

   
    public int getLocalPort() {
        return 0;
    }

    public static void main(String[] args) {
        String url = "http://www.localhost.com/ask";
        String queryString = url.substring(url.lastIndexOf(QUESTION) + 1,url.length());
        System.out.println(url.lastIndexOf(QUESTION));
    }
}
