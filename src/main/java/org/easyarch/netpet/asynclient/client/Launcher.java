package org.easyarch.netpet.asynclient.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import org.easyarch.netpet.asynclient.handler.BaseClientChildHandler;
import org.easyarch.netpet.asynclient.handler.callback.AsyncResponseHandler;
import org.easyarch.netpet.asynclient.http.entity.FileParam;
import org.easyarch.netpet.asynclient.http.entity.RequestEntity;
import org.easyarch.netpet.kits.ByteKits;
import org.easyarch.netpet.kits.StringKits;
import org.easyarch.netpet.web.mvc.entity.Json;
import org.easyarch.netpet.web.mvc.entity.UploadFile;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by xingtianyu on 17-4-5
 * 下午4:50
 * description:
 */

public class Launcher {

    private String ip;
    private int port;
    private URL remoteURL;

    private ChannelFuture future;
    private Bootstrap b;
    private Channel channel;

    public Launcher(String remoteAddress) throws MalformedURLException {
        this(new URL(remoteAddress));
    }

    public Launcher(URL url){
        try {
            remoteURL = url;
            this.ip = InetAddress.getByName(remoteURL.getHost()).getHostAddress();
            this.port = remoteURL.getPort();
            if(port == -1){
                port = 80;
            }
            System.out.println("ip:"+ip+",port:"+port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doRequest(HttpMethod method, HttpHeaders headers, ByteBuf buf) throws HttpPostRequestEncoder.ErrorDataEncoderException {
        doRequest(remoteURL.getPath(),method,headers,buf);
    }

    private void doRequest(String path,HttpMethod method, HttpHeaders headers, List<FileParam> files) throws HttpPostRequestEncoder.ErrorDataEncoderException {
        String uri = checkURI(path);
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri);
        headers = checkHeaders(headers,files);
        if (HttpMethod.POST.equals(method)){
            addFileParam(request,headers,files);
        }
        request.headers().set(headers);
        future.channel().writeAndFlush(request);
    }

    private void doRequest(String path,HttpMethod method, HttpHeaders headers, ByteBuf buf) throws HttpPostRequestEncoder.ErrorDataEncoderException {
        String uri = checkURI(path);
        DefaultFullHttpRequest request;
        if(buf == null){
            request  = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri);
        }else{
            request  = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri, buf);
        }
        headers = checkHeaders(headers,buf);
        if (HttpMethod.POST.equals(method)){
            checkParam(request,headers,buf);
        }
        request.headers().set(headers);
        future.channel().writeAndFlush(request);
    }

    private String checkURI(String path){
        return StringKits.isEmpty(path)? remoteURL.getPath():path;
    }

    private HttpHeaders checkHeaders(HttpHeaders headers,ByteBuf buf){
        if (headers == null) {
            headers = new DefaultHttpHeaders();
        }
        headers.set(HttpHeaderNames.HOST, remoteURL.getHost());
        headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        headers.set(HttpHeaderNames.CONTENT_LENGTH, buf==null?0:buf.readableBytes());
        return headers;
    }
    private HttpHeaders checkHeaders(HttpHeaders headers,List<FileParam> fileParams){
        if (headers == null) {
            headers = new DefaultHttpHeaders();
        }
        int contentLength = 0;
        for (FileParam param:fileParams){
            UploadFile file = param.getFile();
            if (file != null){
                contentLength += file.getContent().length;
            }
        }
        headers.set(HttpHeaderNames.HOST, remoteURL.getHost());
        headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        headers.set(HttpHeaderNames.CONTENT_LENGTH, contentLength);
        return headers;
    }

    private void checkParam(DefaultFullHttpRequest request,HttpHeaders headers,ByteBuf buf) throws HttpPostRequestEncoder.ErrorDataEncoderException {
        String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE);
        HttpPostRequestEncoder encoder = new HttpPostRequestEncoder(request,false);
        if (HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.equals(contentType)){
            String data = ByteKits.toString(buf);
            Map<String,Object> map = Json.toMap(data);
            for (Map.Entry<String,Object> entry:map.entrySet()){
                if (entry.getValue() instanceof String){
                    encoder.addBodyAttribute(entry.getKey(),String.valueOf(entry.getValue()));
                }else if (entry.getValue() instanceof UploadFile){
                    UploadFile uploadFile = (UploadFile) entry.getValue();
                    encoder.addBodyFileUpload(entry.getKey(),uploadFile.getFile(),uploadFile.getContentType(),false);
                }
            }
        }
    }

    private void addFileParam(DefaultFullHttpRequest request,HttpHeaders headers,List<FileParam> fileParams) throws HttpPostRequestEncoder.ErrorDataEncoderException {
        String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE);
        HttpPostRequestEncoder encoder = new HttpPostRequestEncoder(request,false);
        if (HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.equals(contentType)
                ||HttpHeaderValues.MULTIPART_FORM_DATA.equals(contentType)){
            for (FileParam param:fileParams){
                UploadFile file = param.getFile();
                encoder.addBodyFileUpload(param.getParamName(),file.getFile(),file.getContentType(),false);
            }
        }
    }

    public void execute(RequestEntity entity, AsyncResponseHandler handler) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new BaseClientChildHandler(workerGroup,handler));
            future = b.connect(ip,port).sync();
            if (entity.getFiles().isEmpty()){
                doRequest(entity.getPath(),entity.getMethod(),entity.getHeaders(),entity.getBuf());
            }else{
                doRequest(entity.getPath(),entity.getMethod(),entity.getHeaders(),entity.getFiles());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        future.channel().close();

    }

}
