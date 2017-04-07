package org.easyarch.netpet.asynclient.handler;/**
 * Description :
 * Created by YangZH on 16-8-14
 * 下午10:28
 */

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.easyarch.netpet.asynclient.handler.callback.AsyncResponseHandler;

/**
 * Description :
 * Created by YangZH on 16-8-14
 * 下午10:28
 */

public class BaseClientChildHandler extends ChannelInitializer<SocketChannel> {

    private AsyncResponseHandler handler;

    private EventLoopGroup workerGroup;
    public BaseClientChildHandler(EventLoopGroup workerGroup,AsyncResponseHandler handler) {
        this.handler = handler;
        this.workerGroup = workerGroup;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("initChannel");
        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpResponseDecoder());
        pipeline.addLast(new HttpRequestEncoder());
        pipeline.addLast(new HttpObjectAggregator(1024000));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpClientHandler(workerGroup,handler));
    }

}
