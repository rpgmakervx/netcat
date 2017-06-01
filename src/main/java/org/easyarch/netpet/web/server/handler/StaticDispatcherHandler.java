package org.easyarch.netpet.web.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.easyarch.netpet.web.context.ActionHolder;
import org.easyarch.netpet.web.context.HandlerContext;
import org.easyarch.netpet.web.http.request.impl.HttpHandlerRequest;
import org.easyarch.netpet.web.http.response.impl.HttpHandlerResponse;
import org.easyarch.netpet.kits.Kits;
import org.easyarch.netpet.web.mvc.action.ActionType;
import org.easyarch.netpet.web.mvc.action.filter.HttpFilter;
import org.easyarch.netpet.web.mvc.action.handler.impl.StaticHttpHandler;
import org.easyarch.netpet.web.mvc.router.Router;

import java.util.List;

/**
 * Created by xingtianyu on 17-3-14
 * 下午6:39
 * description:
 */

public class StaticDispatcherHandler extends BaseDispatcherHandler {

    private StaticHttpHandler staticHttpHandler;

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
        Router router = new Router(request.uri(), ActionType.HANDLER, org.easyarch.netpet.web.http.protocol.HttpMethod.getMethod(request.method()));
        List<HttpFilter> filters = holder.getFilters(router);
        if (!Kits.hasResource(context,router.getPath())){
            ctx.fireChannelRead(msg);
            return;
        }
        HttpHandlerRequest req = new HttpHandlerRequest(request, router, context, ctx.channel());
        HttpHandlerResponse resp = new HttpHandlerResponse(response,req.getSessionId(), context, ctx.channel());
        if (!filters.isEmpty()) {
            for (HttpFilter filter : filters) {
                if (!filter.before(req, resp)) {
                    return;
                }
            }
        }
        this.staticHttpHandler = new StaticHttpHandler();
        staticHttpHandler.handle(req,resp);
        if (!filters.isEmpty()) {
            for (HttpFilter filter : filters) {
                filter.after(req, resp);
            }
        }

    }

}
