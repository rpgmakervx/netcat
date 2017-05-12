package org.easyarch.netpet.web.http.request.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import org.easyarch.netpet.kits.HashKits;
import org.easyarch.netpet.kits.StringKits;
import org.easyarch.netpet.web.context.HandlerContext;
import org.easyarch.netpet.web.http.Const;
import org.easyarch.netpet.web.http.cookie.HttpCookie;
import org.easyarch.netpet.web.http.protocol.HttpHeaderName;
import org.easyarch.netpet.web.http.request.BodyWrapper;
import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.request.ParamParser;
import org.easyarch.netpet.web.http.session.HttpSession;
import org.easyarch.netpet.web.http.session.impl.DefaultHttpSession;
import org.easyarch.netpet.web.mvc.entity.Json;
import org.easyarch.netpet.web.mvc.entity.UploadFile;
import org.easyarch.netpet.web.mvc.router.Router;

import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Description :
 * Created by xingtianyu on 17-2-24
 * 下午5:19
 * description:
 */

public class HttpHandlerRequest implements HandlerRequest {

    private FullHttpRequest request;
    private HttpHeaders headers;

    private ByteBuf buf;
    private Channel channel;

    private Router router;

    private HandlerContext context;

    private String charset;
    private ParamParser paramParser;
    private Map<String,String> params ;
    private ServerCookieDecoder decoder;
    private static final String QUESTION = "?";

    public HttpHandlerRequest(FullHttpRequest request,Router router, HandlerContext context, Channel channel){
        this.request = request;
        this.context = context;
        this.router = router;
        this.channel = channel;
        this.paramParser = new ParamParser(request);
        this.params = paramParser.parse();
        this.params.putAll(router.getPathParams());
        this.buf = request.content();
        checkRequest(request);
        this.decoder = ServerCookieDecoder.LAX;
        this.charset = "UTF-8";

    }

    private void checkRequest(FullHttpRequest request){
        if (request == null){
            this.headers = new DefaultHttpHeaders();
        }else{
            this.headers = request.headers();
        }
    }

    private Set<HttpCookie> wrapCookies(Set<Cookie> cookies){
        Set<HttpCookie> httpCookies = new HashSet<>();
        for (Cookie cookie:cookies){
            HttpCookie hc = new HttpCookie(cookie);
            httpCookies.add(hc);
        }
        return httpCookies;
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

    @Override
    public HandlerContext getContext() {
        return context;
    }

    @Override
    public Set<HttpCookie> getCookies() {
        String cookieValue = this.headers.get(HttpHeaderName.COOKIE);
        if (StringKits.isEmpty(cookieValue)){
            return new HashSet<>();
        }
        Set<Cookie> cookies = decoder.decode(cookieValue);
        return wrapCookies(cookies);
    }

    @Override
    public String getHeader(String name) {
        if (this.headers == null){
            return "";
        }
        String header = headers.get(name);
        return encode(header);
    }

    @Override
    public HttpHeaders getHeaders() {
        return request.headers();
    }

    @Override
    public Collection<String> getHeaderNames() {
        if (this.headers == null){
            return new ArrayList<>();
        }
        return this.headers.names();
    }

    @Override
    public Long getDateHeader(String name) {
        if (this.headers == null){
            return null;
        }
        return this.headers.getTimeMillis(name);
    }

    @Override
    public ByteBuf getContent() {
        return this.buf;
    }

    @Override
    public String getMethod() {
        return request.method().name();
    }

    @Override
    public String getContextPath() {
        return context.getContextPath();
    }

    @Override
    public UploadFile file(String name) {
        return paramParser.parseFile(name);
    }

    @Override
    public List<UploadFile> files() {
        return paramParser.parseFiles();
    }

    @Override
    public <T> T body(Class<T> cls) throws Exception {
        Map<String,Object> params = getParamMap();
        BodyWrapper<T> wrapper = new BodyWrapper<>();
        return wrapper.getBean(cls,params);
    }

    @Override
    public Json getJson() {
        Map<String,Object> params = getParamMap();
        Json json = new Json(params);
        return json;
    }

    @Override
    public String getQueryString() {
        String url = request.uri();
        return checkURL(url);
    }

    @Override
    public String getSessionId() {
        for(HttpCookie cookie:getCookies()){
            if (Const.NETPETID.equals(cookie.name())){
                return cookie.value();
            }
        }
        return "";
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return channel.remoteAddress();
    }

    @Override
    public String getRequestURI() {
        return router.getPath();
    }

    /**
     * session不存在则直接创建
     * @return 返回session
     */
    @Override
    public HttpSession getSession() {
        String sessionId = "";
        for(HttpCookie cookie:getCookies()){
            if (Const.NETPETID.equals(cookie.name())){
                sessionId = cookie.value();
                break;
            }
        }
        HttpSession session = context.getSession(sessionId);
        if (StringKits.isEmpty(sessionId) ||session == null){
            sessionId = HashKits
                    .sha1(channel.id().asLongText());
            session = new DefaultHttpSession();
            session.setSessionId(sessionId);
            session.setMaxAge(context.getSessionAge());
            context.addSession(sessionId,session);
        }
        return session;
    }

    @Override
    public String getCharacterEncoding() {
        return this.charset;
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        this.charset = Charset.forName(env).name();
    }

    @Override
    public int getContentLength() {
        String length = getHeader(HttpHeaderNames.CONTENT_LENGTH.toString());
        if (StringKits.isEmpty(length)) {
            return 0;
        }
        return Integer.parseInt(length);
    }

    @Override
    public String getContentType() {
        String length = this.headers.get(HttpHeaderNames.CONTENT_TYPE);
        if (StringKits.isEmpty(length)){
            return null;
        }
        return length;
    }

    @Override
    public String getParam(String name) {
        String value = this.params.get(name);
        return encode(value);
    }

    @Override
    public Integer getIntParam(String name) {
        String value = this.params.get(name);
        return Integer.parseInt(value);
    }

    @Override
    public Collection<String> getParamNames() {
        return this.params.keySet();
    }

    @Override
    public Collection<String> getParamValues(String name) {
        List<String> values = new ArrayList<>();
        for (String val:this.params.values()){
            values.add(encode(val));
        }
        return values;
    }

    @Override
    public Map getParamMap() {
        return this.params;
    }

    @Override
    public String getProtocol() {
        return request.protocolVersion().text();
    }

    @Override
    public String getRemoteAddr() {
        return channel.remoteAddress().toString();
    }


}
