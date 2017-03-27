package org.easyarch.netcat.asynclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.easyarch.netcat.asynclient.future.ResponseFuture;
import org.easyarch.netcat.asynclient.handler.BaseClientChildHandler;
import org.easyarch.netcat.asynclient.manager.HttpResponseManager;
import org.easyarch.netcat.kits.StringKits;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xingtianyu on 17-3-27
 * 下午3:41
 * description:
 */

class ClientLauncher {

    private String ip;
    private int port;
    private URL remoteURL;
    private EventLoopGroup workerGroup;
    private Bootstrap b;
    private ChannelFuture future;
    private Channel channel;

    public ClientLauncher(String remoteAddress) throws MalformedURLException {
        this(new URL(remoteAddress));
    }

    public ClientLauncher(URL url){
        try {
            remoteURL = url;
            this.ip = InetAddress.getByName(remoteURL.getHost()).getHostAddress();
            this.port = remoteURL.getPort();
            if(port == -1){
                port = 80;
            }
            workerGroup = new NioEventLoopGroup();
            connect(ip,port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(URL url){
        try {
            init(url.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void init(InetSocketAddress address){
        try {
            this.ip = address.getHostString();
            this.port = address.getPort();
            workerGroup = new NioEventLoopGroup();
            b = new Bootstrap();
            workerGroup = new NioEventLoopGroup();
            b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new BaseClientChildHandler());
            connect(InetAddress.getByName(ip).getHostAddress(),port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(String url) throws Exception {
        try {
            URL u = new URL(url);
            this.ip = InetAddress.getByName(u.getHost()).getHostAddress();
            this.port = u.getPort();
            if(port == -1){
                port = 80;
            }
            workerGroup = new NioEventLoopGroup();
            connect(ip,port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ip() {
        return ip;
    }

    public int port() {
        return port;
    }

    private void connect(String ip,int port) {
        try {
            b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new BaseClientChildHandler());
            future = b.connect(ip,port);
            future.sync();
            channel = future.channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doRequest(String url, HttpMethod method, HttpHeaders headers, ByteBuf buf) {
        String uri = StringKits.isEmpty(url)? File.separator:url;
        DefaultFullHttpRequest request;
        if(buf == null){
            request  = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri);
        }else{
            request  = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri, buf);
        }
        if (headers == null) {
            headers = new DefaultHttpHeaders();
            headers.set(HttpHeaderNames.HOST, url);
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        request.headers().set(headers);
        HttpResponseManager.setAttr(channel, new ResponseFuture<FullHttpResponse>());
        channel.writeAndFlush(request);
    }

    public void doRequest(HttpMethod method, HttpHeaders headers, ByteBuf buf) {
        String uri = StringKits.isEmpty(remoteURL.getPath())? File.separator:remoteURL.getPath();
        System.out.println("uri:"+uri);
        DefaultFullHttpRequest request;
        if(buf == null){
            request  = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri);
        }else{
            request  = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri, buf);
        }
        if (headers == null) {
            headers = new DefaultHttpHeaders();
            headers.set(HttpHeaderNames.HOST, remoteURL.getHost());
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        request.headers().set(headers);
        HttpResponseManager.setAttr(channel, new ResponseFuture<FullHttpResponse>());
        channel.writeAndFlush(request);
    }

    public FullHttpResponse getWholeResponse() throws Exception {
        ResponseFuture<FullHttpResponse> responseFuture =
                HttpResponseManager.getAttr(channel);
        return responseFuture.get();
    }

    public byte[] getContentAsStream() {
        ResponseFuture<FullHttpResponse> responseFuture =
                HttpResponseManager.getAttr(channel);
        try {
            FullHttpResponse response = responseFuture.get();
            return getContent(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public HttpHeaders getHeaders() {
        ResponseFuture<FullHttpResponse> responseFuture =
                HttpResponseManager.getAttr(channel);
        try {
            return getHeaders(responseFuture.get());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> getHeadersAsMap() {
        ResponseFuture<FullHttpResponse> responseFuture =
                HttpResponseManager.getAttr(channel);
        Map<String, Object> headMap = new HashMap<>();
        try {
            HttpHeaders headers = getHeaders(responseFuture.get());
            for (Map.Entry<String, String> entry : headers.entries()) {
                headMap.put(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headMap;
    }

    private void close() {
        workerGroup.shutdownGracefully();
    }

    private byte[] getContent(FullHttpResponse response) {
        ByteBuf buf = response.content();
        return ByteBufUtil.getBytes(buf);
    }

    private HttpHeaders getHeaders(FullHttpResponse response) {
        return response == null ? null : response.headers();
    }
}
