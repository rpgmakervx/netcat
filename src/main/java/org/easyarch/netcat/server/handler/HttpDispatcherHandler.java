package org.easyarch.netcat.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.request.HttpHandlerRequest;
import org.easyarch.netcat.http.response.HttpHandlerResponse;
import org.easyarch.netcat.kits.file.FileKits;
import org.easyarch.netcat.mvc.HttpHandler;
import org.easyarch.netcat.mvc.JsonHttpHandler;
import org.easyarch.netcat.mvc.StringHttpHandler;
import org.easyarch.netcat.mvc.entity.Json;
import org.easyarch.netcat.mvc.entity.ViewHttpHandler;


/**
 * Description :
 * Created by xingtianyu on 17-2-23
 * 下午4:52
 * description:
 */

public class HttpDispatcherHandler extends ChannelInboundHandlerAdapter {

    private static final String NOTFOUND_MSG = "<h1 align='center'>404 Not Found</h1>";

    private static final String REDIRECT = "redirect:";

    private HandlerContext context;

    public HttpDispatcherHandler() {
        this.context = new HandlerContext();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        boolean isSuccess = request.decoderResult().isSuccess();
        if (!isSuccess){
            return ;
        }
        HttpHandler handler = context.getHandler(request.uri());
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        if (handler == null){
            byte[] content = NOTFOUND_MSG.getBytes();
            ByteBuf buf = Unpooled.wrappedBuffer(content,0, content.length);
            response.copy(buf);
            ctx.writeAndFlush(response);
            return ;
        }
        HttpHandlerRequest req = new HttpHandlerRequest(request);
        HttpHandlerResponse resp = new HttpHandlerResponse(response);
        if (handler instanceof ViewHttpHandler){
            StringBuffer resourcePath = new StringBuffer();
            String view = ((ViewHttpHandler) handler).handle(req,resp);
            if (view.startsWith(REDIRECT)){
                response.setStatus(HttpResponseStatus.FOUND);
                ctx.writeAndFlush(response);
                return;
            }else{
                String viewPath = context.getContextPath();
                resourcePath.append(view);
                resourcePath.append(viewPath);
                byte[] content = FileKits.read(resourcePath.toString());
                ByteBuf buf = Unpooled.wrappedBuffer(content,0, content.length);
                response.copy(buf);
                ctx.writeAndFlush(response);
                return;
            }
        }
        if (handler instanceof JsonHttpHandler){
            Json json = ((JsonHttpHandler) handler).handle(req,resp);
            byte[] content = JSONObject.toJSONBytes(json.getJsonMap(),
                    SerializerFeature.PrettyFormat);
            ByteBuf buf = Unpooled.wrappedBuffer(content,0, content.length);
            response.copy(buf);
            ctx.writeAndFlush(response);
            return;
        }
        if (handler instanceof StringHttpHandler){
            byte[] content = ((StringHttpHandler) handler).handle(req,resp).getBytes("UTF-8");
            ByteBuf buf = Unpooled.wrappedBuffer(content,0, content.length);
            response.copy(buf);
            ctx.writeAndFlush(response);
            return;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
