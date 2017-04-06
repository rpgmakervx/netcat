package org.easyarch.netpet.asynclient.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.easyarch.netpet.asynclient.handler.callback.AsyncResponseHandler;
import org.easyarch.netpet.asynclient.http.response.AsyncHttpResponse;
import org.easyarch.netpet.asynclient.http.response.impl.AsyncHttpResponseImpl;


/**
 * Description :
 * Created by xingtianyu on 16-12-8
 * 上午2:03
 */

public class HttpClientHandler<T> extends ChannelInboundHandlerAdapter {

    private AsyncResponseHandler<T> handler;

    private EventLoopGroup workerGroup;

    public HttpClientHandler(EventLoopGroup workerGroup,AsyncResponseHandler handler){
        this.handler = handler;
        this.workerGroup = workerGroup;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse response = (FullHttpResponse) msg;
        channelRead0(ctx, response);
        workerGroup.shutdownGracefully();
    }

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        AsyncHttpResponse resp = new AsyncHttpResponseImpl(response);
        System.out.println("收到服务器消息");
        if (!response.status().equals(HttpResponseStatus.OK)){
            handler.onFailure(resp.getStatusCode(),resp.getBytes());
        }else{
            handler.onSuccess(resp);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        handler.onFailure(500,"internal error:"+cause.getMessage());
    }

}
