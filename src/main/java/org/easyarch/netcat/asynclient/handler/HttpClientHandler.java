package org.easyarch.netcat.asynclient.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import org.easyarch.netcat.asynclient.future.ResponseFuture;
import org.easyarch.netcat.asynclient.manager.HttpResponseManager;


/**
 * Description :
 * Created by xingtianyu on 16-12-8
 * 上午2:03
 */

public class HttpClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpResponse response = (FullHttpResponse) msg;
        channelRead0(ctx, response);
        ByteBuf buf = response.content();
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
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
//        DefaultFullHttpRequest request =
//                new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/");
//        HttpHeaders headers = new DefaultHttpHeaders();
//        headers.set(HttpHeaderNames.CONTENT_TYPE,"text/html;charset=utf-8");
//        headers.set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
//        request.headers().set(headers);
//        ctx.channel().writeAndFlush(request);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
