package org.easyarch.netcat.server.handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.easyarch.netcat.context.ActionHolder;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.protocol.HttpMethod;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.handler.impl.DefaultHttpHandler;
import org.easyarch.netcat.mvc.router.Router;

/**
 * Created by xingtianyu on 17-3-14
 * 下午6:39
 * description:
 */

public class StaticDispatcherHandler extends BaseDispatcherHandler {

    private DefaultHttpHandler defaultHttpHandler;

    public StaticDispatcherHandler(HandlerContext context, ActionHolder holder) {
        super(context,holder);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        boolean isSuccess = request.decoderResult().isSuccess();
        if (!isSuccess) {
            ctx.close();
            return;
        }
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        Router router = new Router(request.uri(), HttpMethod.getMethod(request.method()));
        HttpHandlerRequest req = new HttpHandlerRequest(request, router, context, ctx.channel());
        HttpHandlerResponse resp = new HttpHandlerResponse(response, context, ctx.channel());
        this.defaultHttpHandler = new DefaultHttpHandler();
        defaultHttpHandler.handle(req, resp);
        if (defaultHttpHandler.isInterrupt()) {
            return;
        }
        ctx.fireChannelRead(msg);
    }

}
