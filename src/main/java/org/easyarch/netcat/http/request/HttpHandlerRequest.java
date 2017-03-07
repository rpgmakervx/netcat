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
import java.util.*;

/**
 * Description :
 * Created by xingtianyu on 17-2-24
 * 下午5:19
 * description:
 */

public class HttpHandlerRequest {

    private FullHttpRequest request;

    private Channel channel;

    private HandlerContext handlerContext;
    private ServerCookieDecoder decoder;
    private static final String QUESTION = "?";
    private static final String NETCATID = "NETCATID";
    public HttpHandlerRequest(FullHttpRequest request, Channel channel){
        this.request = request;
        this.channel = channel;
        this.decoder = ServerCookieDecoder.LAX;
    }

    public HandlerContext getHandlerContext() {
        return handlerContext;
    }

    public void setHandlerContext(HandlerContext handlerContext) {
        this.handlerContext = handlerContext;
    }

    public Set<Cookie> getCookies() {
        String cookieVallue = request.headers().get(HttpHeaderNames.COOKIE);
        Set<Cookie> cookie = decoder.decode(cookieVallue);
        return cookie;
    }


    public String getHeader(String name) {
        HttpHeaders headers = request.headers();
        if (headers == null){
            return "";
        }
        return headers.get(name);
    }

    public Collection<String> getHeaderNames() {
        HttpHeaders headers = request.headers();
        if (headers == null){
            return new ArrayList<>();
        }
        return headers.names();
    }

   
    public Long getDateHeader(String name) {
        HttpHeaders headers = request.headers();
        if (headers == null){
            return null;
        }
        return headers.getTimeMillis(name);
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
        return url.substring(url.lastIndexOf(QUESTION) + 1,url.length());
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

    public void login(String username, String password) {

    }


    public void logout(){

    }

    public Object getAttribute(String name) {
        return null;
    }

   
    public List<String> getAttributeNames() {
        return null;
    }

   
    public String getCharacterEncoding() {
        return null;
    }

   
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

    }

   
    public int getContentLength() {
        return 0;
    }

   
    public long getContentLengthLong() {
        return 0;
    }

   
    public String getContentType() {
        return null;
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
