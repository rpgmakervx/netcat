package org.easyarch.netpet.web.http.response.impl;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import org.easyarch.netpet.kits.ByteKits;
import org.easyarch.netpet.kits.StringKits;
import org.easyarch.netpet.kits.file.FileKits;
import org.easyarch.netpet.web.context.HandlerContext;
import org.easyarch.netpet.web.http.Const;
import org.easyarch.netpet.web.http.cookie.HttpCookie;
import org.easyarch.netpet.web.http.protocol.HttpHeaderName;
import org.easyarch.netpet.web.http.protocol.HttpHeaderValue;
import org.easyarch.netpet.web.http.protocol.HttpStatus;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.web.mvc.entity.Json;
import org.easyarch.netpet.web.mvc.temp.TemplateParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.easyarch.netpet.web.http.protocol.HttpHeaderName.SET_COOKIE;

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

    private TemplateParser tmpParser;

    private Map<String, Object> models = new ConcurrentHashMap<>();

    public HandlerContext context;

    private ServerCookieEncoder encoder;


    public HttpHandlerResponse(FullHttpResponse response, HandlerContext context, Channel channel) throws IOException {
        init(response, context, channel);
    }

    private void init(FullHttpResponse response, HandlerContext context, Channel channel) throws IOException {
        this.context = context;
        this.response = response;
        this.tmpParser = new TemplateParser(context);
        if (response == null) {
            this.headers = new DefaultHttpHeaders();
        } else {
            this.headers = this.response.headers();
        }
        this.charset = "UTF-8";
        this.channel = channel;
        this.encoder = ServerCookieEncoder.LAX;
    }

    @Override
    public HandlerContext getContext() {
        return context;
    }

    @Override
    public void setContext(HandlerContext context) {
        this.context = context;
    }

    @Override
    public void addCookie(HttpCookie cookie) {
        this.headers.add(SET_COOKIE, encoder.encode(cookie.getWrapper()));
    }

    @Override
    public void setDateHeader(String name, long date) {
        this.headers.set(name, date);
    }

    @Override
    public void setHeader(String name, String value) {
        this.headers.set(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        this.headers.add(name, value);
    }

    @Override
    public void setStatus(int code) {
        response.setStatus(HttpResponseStatus.valueOf(code));
    }

    @Override
    public void setStatus(int code, String msg) {
        response.setStatus(new HttpResponseStatus(code, msg));
    }

    @Override
    public int getStatus() {
        return response.status().code();
    }


    @Override
    public String getHeader(String name) {
        String headr = this.headers.get(name);
        return encode(headr);
    }


    @Override
    public Collection<String> getHeaderNames() {
        return this.headers.names();
    }


    @Override
    public String getCharacterEncoding() {
        return this.charset;
    }


    @Override
    public String getContentType() {
        return this.headers.get(HttpHeaderNames.CONTENT_TYPE);
    }


    @Override
    public void setCharacterEncoding(String charset) {
        this.charset = charset;
    }

    @Override
    public Object getModel(String name) {
        return models.get(name);
    }

    @Override
    public Map<String, Object> getModel() {
        return models;
    }

    @Override
    public Collection<String> getModelNames() {
        return models.keySet();
    }

    @Override
    public void addModel(String name, Object value) {
        System.out.println("name:" + models + ",value:" + value);
        models.put(name, value);
    }

    @Override
    public void removeModel(String name) {
        models.remove(name);
    }

    @Override
    public void setContentLength(int len) {
        this.headers.set(HttpHeaderNames.CONTENT_LENGTH, len);
    }

    @Override
    public void setContentType(String type) {
        this.headers.set(HttpHeaderNames.CONTENT_TYPE, type);
    }

    @Override
    public void write(byte[] content, String contentType, int statusCode) {
        setHeader(HttpHeaderName.CONTENT_TYPE, contentType);
        setHeader(HttpHeaderName.CONTENT_LENGTH, String.valueOf(content.length));
        response = response.copy(ByteKits.toByteBuf(content));
        response.setStatus(HttpResponseStatus.valueOf(statusCode));
        channel.writeAndFlush(response);
    }

    @Override
    public void write(byte[] content, String contentType) {
        setHeader(HttpHeaderName.CONTENT_TYPE, contentType);
        setHeader(HttpHeaderName.CONTENT_LENGTH, String.valueOf(content.length));
        response = response.copy(ByteKits.toByteBuf(content));
        channel.writeAndFlush(response);
    }

    @Override
    public void write(byte[] content) {
        setHeader(HttpHeaderName.CONTENT_LENGTH, String.valueOf(content.length));
        response = response.copy(ByteKits.toByteBuf(content));
        channel.writeAndFlush(response);
    }

    @Override
    public void write() {
        channel.writeAndFlush(response);
    }

    @Override
    public void text(String content) {
        write(encode(content).getBytes(), HttpHeaderValue.TEXT_PLAIN);
    }

    @Override
    public void json(byte[] json) {
        write(json, HttpHeaderValue.APPLICATION_JSON);
    }

    @Override
    public void json(String json) {
        write(json.getBytes(), HttpHeaderValue.APPLICATION_JSON);
    }

    @Override
    public void json(Map<String, Object> json) {
        json(encode(Json.stringify(json)));
    }

    @Override
    public void json(Json json) {
        json(json.getJsonMap());
    }

    @Override
    public void json(Json json, int statusCode) {
        String jsonString = "";
        if (json!=null){
            jsonString = Json.stringify(json);
        }
        write(jsonString.getBytes(), HttpHeaderValue.APPLICATION_JSON,statusCode);
    }

    /**
     * 先检查resource或jar包内有没有资源。
     * 存在则读取；若不存在则进一步检查根目录+前缀+资源名存不存在
     * 存在则读取；若不存在，则金玉不检查根目录+资源名存不存在
     * 存在则读取；若不存在则看当前请求是否是200,
     * 是则认为找不到资源，报错；否则根据code返回系统自定义404页面
     *
     * @param view
     * @param statusCode
     */
    private void html(String view, int statusCode) throws Exception {
        StringBuffer pathBuffer = new StringBuffer();
        pathBuffer.append("/").append(view).append(Const.POINT)
                .append(context.getViewSuffix());
        String wholePath = context.getWebView() + context.getViewPrefix() + pathBuffer.toString();
        tmpParser.addParam(models);
        byte[] content = null;
        InputStream errorStream = this.getClass().getResourceAsStream(
                context.getViewPrefix()+pathBuffer.toString());
        System.out.println("errorStream path:"+(context.getViewPrefix()+pathBuffer.toString()));
        if (errorStream != null){
            content = tmpParser.getTemplate(errorStream).getBytes();
            write(content, HttpHeaderValue.TEXT_HTML, statusCode);
            System.out.println("in resource or jar");
            return;
        }else if (!FileKits.exists(wholePath)) {
            wholePath = context.getWebView()+pathBuffer.toString();
            System.out.println("wholePath with out prefix:"+wholePath);
            if (!FileKits.exists(wholePath)){
                HttpResponseStatus status = null;
                if (statusCode != 200){
                    status = HttpResponseStatus.valueOf(statusCode);
                }else{
                    status = HttpResponseStatus.NOT_FOUND;
                }
                content = getErrorPageContent(status.code()
                        ,status.reasonPhrase()
                        ,"")
                        .getBytes();
                write(content, HttpHeaderValue.TEXT_HTML, statusCode);
                System.out.println("in server code");
                return ;
            }
        }
        content = tmpParser.getTemplate(wholePath).getBytes();
        System.out.println("wholePath:"+wholePath);
        write(content, HttpHeaderValue.TEXT_HTML, statusCode);
    }

    @Override
    public void error(int statusCode) throws Exception {
        if (StringKits.isEmpty(context.getErrorPage())){
            HttpResponseStatus status = HttpResponseStatus.valueOf(statusCode);
            String content = getErrorPageContent(status.code(),status.reasonPhrase(),"");
            write(content.getBytes(),HttpHeaderValue.TEXT_HTML,statusCode);
            return ;
        }
        html(context.getErrorPage(), statusCode);
    }

    @Override
    public void html(String view) throws Exception {
        html(view, HttpResponseStatus.OK.code());
    }

    @Override
    public void notFound() throws Exception {
        if (StringKits.isEmpty(context.getErrorPage())){
            HttpResponseStatus status = HttpResponseStatus.NOT_FOUND;
            String content = getErrorPageContent(status.code(),status.reasonPhrase(),"");
            write(content.getBytes(),HttpHeaderValue.TEXT_HTML,status.code());
            return ;
        }
        html(context.getNotFound(), HttpStatus.NOT_FOUND);
    }

    @Override
    public void serverError() throws Exception {
        html(context.getServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public void image(byte[] bytes) {
        write(bytes, HttpHeaderValue.IMAGE);
    }

    @Override
    public void redirect(String url) {
        response.setStatus(HttpResponseStatus.FOUND);
        setHeader(HttpHeaderNames.LOCATION.toString(), url);
        write();
    }

    @Override
    public void download(byte[] bytes, String filename, String contentType) {
        String fn = filename;
        try {
            fn = new String(filename.getBytes("GB2312"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setHeader(HttpHeaderNames.CONTENT_DISPOSITION.toString(), HttpHeaderValue.ATTACHMENT + fn);
        write(bytes, contentType.toString());
    }

    private String encode(String content) {
        if (StringKits.isEmpty(content)) {
            return "";
        }
        try {
            return new String(content.getBytes(), charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return content;
        }
    }

    private String getErrorPageContent(int code,String reasonPhrase,String message){
        StringBuffer errorBuffer = new StringBuffer();
        errorBuffer.append("<h1 align='center'>"+code+" "+reasonPhrase+"</h1>");
        errorBuffer.append("<p>"+message+"</p><hr/>");
        errorBuffer.append("<div align='center'><span>netcat/1.0 inner</span></div>");
        return errorBuffer.toString();
    }
}
