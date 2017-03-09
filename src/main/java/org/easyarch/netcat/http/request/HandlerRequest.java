package org.easyarch.netcat.http.request;

import io.netty.handler.codec.http.cookie.Cookie;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.session.HttpSession;

import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by xingtianyu on 17-3-9
 * 下午3:29
 * description:
 */

public interface HandlerRequest {
    public HandlerContext getHandlerContext();

    public void setHandlerContext(HandlerContext handlerContext);

    public Set<Cookie> getCookies();


    public String getHeader(String name);

    public Collection<String> getHeaderNames();


    public Long getDateHeader(String name);


    public String getMethod();

    public String getContextPath();


    public String getQueryString();


    public SocketAddress getRemoteAddress();

    public String getRequestedSessionId();


    public String getRequestURI();

    public HttpSession getSession();

    public Object getAttribute(String name);


    public Collection<String> getAttributeNames();


    public String getCharacterEncoding();


    public void setCharacterEncoding(String env) throws UnsupportedEncodingException;


    public int getContentLength();


    public String getContentType();


    public String getParameter(String name);


    public Collection<String> getParameterNames();


    public Collection<String> getParameterValues(String name);


    public Map<String, String> getParameterMap();


    public String getProtocol();

    public String getRemoteAddr();


    public void setAttribute(String name, Object object);

    public void removeAttribute(String name);
}
