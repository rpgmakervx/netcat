package org.easyarch.netcat.http.request;

import io.netty.handler.codec.http.FullHttpRequest;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.Cookie;
import org.easyarch.netcat.http.session.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-24
 * 下午5:19
 * description:
 */

public class HttpHandlerRequest {

    private FullHttpRequest request;

    public HandlerContext handlerContext;

    public HttpHandlerRequest(FullHttpRequest request){
        this.request = request;
    }

    public HandlerContext getHandlerContext() {
        return handlerContext;
    }

    public void setHandlerContext(HandlerContext handlerContext) {
        this.handlerContext = handlerContext;
    }

    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    public long getDateHeader(String name) {
        return 0;
    }

   
    public String getHeader(String name) {
        return null;
    }
   
    public List<String> getHeaderNames() {
        return null;
    }

   
    public int getIntHeader(String name) {
        return 0;
    }

   
    public String getMethod() {
        return null;
    }

   
    public String getPathInfo() {
        return null;
    }

   
    public String getPathTranslated() {
        return null;
    }

   
    public String getContextPath() {
        return null;
    }

   
    public String getQueryString() {
        return null;
    }

   
    public String getRemoteUser() {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

   
    public String getRequestURI() {
        return null;
    }

   
    public StringBuffer getRequestURL() {
        return null;
    }

   
    public String getServletPath() {
        return null;
    }

   
    public HttpSession getSession(boolean create) {
        return null;
    }

   
    public HttpSession getSession() {
        return null;
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

}
