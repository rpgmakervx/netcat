package org.easyarch.netpet.asynclient.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.easyarch.netpet.asynclient.handler.HttpClientHandler;
import org.easyarch.netpet.asynclient.handler.callback.AsyncResponseHandler;
import org.easyarch.netpet.asynclient.http.entity.RequestEntity;
import org.easyarch.netpet.kits.ByteKits;
import org.easyarch.netpet.web.http.protocol.HttpHeaderValue;
import org.easyarch.netpet.web.mvc.entity.Json;

import java.net.URI;
import java.util.Map;

/**
 * Created by xingtianyu on 17-4-5
 * 下午4:50
 * description:
 */

public class AsyncHttpClient {

    private Launcher launcher;

    public AsyncHttpClient(String host) throws Exception {
        launcher = new Launcher(host);
    }

    public void get(String uri, AsyncResponseHandler handler) throws Exception {
        RequestEntity entity = new RequestEntity(uri, HttpMethod.GET,null,null);
        launcher.execute(entity,handler);
    }

    public void get(String uri, Map<String,Object> headers, AsyncResponseHandler handler) throws Exception {
        HttpHeaders header = new DefaultHttpHeaders();
        for (Map.Entry<String,Object> entry:headers.entrySet()){
            header.add(entry.getKey(),entry.getValue());
        }
        RequestEntity entity = new RequestEntity(uri, HttpMethod.GET,header,null);
        launcher.execute(entity,handler);
    }

    public void postEntity(String uri,Map<String,String> params,AsyncResponseHandler handler) throws Exception {
        ByteBuf buf = ByteKits.toByteBuf(Json.stringify(params));
        HttpHeaders headers = new DefaultHttpHeaders();
        headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED);
        RequestEntity entity = new RequestEntity(uri, HttpMethod.POST,headers,buf);
        launcher.execute(entity,handler);
    }

    public void postJson(String uri,Json json,AsyncResponseHandler handler) throws Exception {
        ByteBuf buf = ByteKits.toByteBuf(Json.stringify(json));
        HttpHeaders headers = new DefaultHttpHeaders();
        headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValue.APPLICATION_JSON);
        RequestEntity entity = new RequestEntity(uri, HttpMethod.POST,null,buf);
        launcher.execute(entity,handler);
    }

    public void close(){
        launcher.close();
    }

    public void connect(String host, int port,AsyncResponseHandler handler) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new HttpObjectAggregator(1024000));
                    ch.pipeline().addLast(new ChunkedWriteHandler());
                    ch.pipeline().addLast(new HttpClientHandler(workerGroup,handler));
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            URI uri = new URI("http://127.0.0.1:8800");
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                    "/index/xingtianyu");
            // 构建http请求
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
            // 发送http请求
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }


}
