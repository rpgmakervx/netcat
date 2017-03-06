package org.easyarch.netcat.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.context.RouteHolder;

/**
 * Description :
 * Created by xingtianyu on 17-2-23
 * 下午4:36
 * description:
 */

public class BaseChildHandler extends ChannelInitializer<SocketChannel> {

    private HandlerContext context;
    private RouteHolder holder;

    public BaseChildHandler(HandlerContext context,RouteHolder holder) {
        this.context = context;
        this.holder = holder;
    }

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("compress", new HttpContentCompressor(9));
        pipeline.addLast("aggregator", new HttpObjectAggregator(1024000));
        pipeline.addLast("decompress", new HttpContentDecompressor());
        pipeline.addLast("httphandler",new HttpDispatcherHandler(context,holder));
    }
}
