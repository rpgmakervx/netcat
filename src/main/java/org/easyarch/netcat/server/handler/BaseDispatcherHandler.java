package org.easyarch.netcat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.easyarch.netcat.context.ActionHolder;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.handler.impl.ErrorHandler;
import org.easyarch.netcat.mvc.router.Router;

/**
 * Created by xingtianyu on 17-3-14
 * 下午6:42
 * description:
 */

public class BaseDispatcherHandler extends ChannelInboundHandlerAdapter {

    protected HandlerContext context;
    protected ActionHolder holder;
    protected ErrorHandler errorHandler;

    public BaseDispatcherHandler(HandlerContext context, ActionHolder holder) {
        this.context = context;
        this.holder = holder;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        StringBuffer errorBuffer = new StringBuffer();
        errorBuffer.append(cause.toString());
        StackTraceElement[] elements = cause.getStackTrace();
        for (StackTraceElement e : elements) {
            errorBuffer.append("<br/><span style='margin-left:50px'>at  ").append(e).append("</span>");
        }
        HttpResponseStatus status = HttpResponseStatus.INTERNAL_SERVER_ERROR;
        errorHandler = new ErrorHandler(status.code(), status.reasonPhrase());
        errorHandler.setMessage(errorBuffer.toString());
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,status);
        HttpHandlerRequest req = new HttpHandlerRequest(null, new Router(null), context, ctx.channel());
        HttpHandlerResponse resp = new HttpHandlerResponse(response,context, ctx.channel());
        errorHandler.handle(req,resp);
    }
}
