package org.easyarch.netpet.asynclient.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import org.easyarch.netpet.asynclient.future.ResponseFuture;
import org.easyarch.netpet.asynclient.handler.callback.AsyncResponseHandler;
import org.easyarch.netpet.asynclient.manager.HttpResponseManager;


/**
 * Description :
 * Created by xingtianyu on 16-12-8
 * 上午2:03
 */

public class HttpClientHandler extends ChannelInboundHandlerAdapter {

    private AsyncResponseHandler handler;

    public HttpClientHandler(AsyncResponseHandler handler){
        this.handler = handler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse response = (FullHttpResponse) msg;
        channelRead0(ctx, response);
//        ByteBuf buf = response.content();
//        byte[] bytes = new byte[buf.readableBytes()];
//        buf.readBytes(bytes);
    }

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        ResponseFuture<FullHttpResponse> future =
                HttpResponseManager.getAttr(ctx.channel());
        if (future == null) {
            future = new ResponseFuture<FullHttpResponse>();
            HttpResponseManager.setAttr(ctx.channel(), future);
        }
        future.set(response);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        handler.onFailure(500,"internal error:"+cause.getMessage());
    }

}
