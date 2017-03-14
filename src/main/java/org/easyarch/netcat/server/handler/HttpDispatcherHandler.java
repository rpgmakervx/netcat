package org.easyarch.netcat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.easyarch.netcat.context.ActionHolder;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.protocol.HttpMethod;
import org.easyarch.netcat.http.protocol.HttpStatus;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.Action;
import org.easyarch.netcat.mvc.action.ActionWrapper;
import org.easyarch.netcat.mvc.action.filter.Filter;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;
import org.easyarch.netcat.mvc.action.handler.impl.ErrorHandler;
import org.easyarch.netcat.mvc.router.Router;

import java.util.List;


/**
 * Description :
 * Created by xingtianyu on 17-2-23
 * 下午4:52
 * description:
 */

public class HttpDispatcherHandler extends BaseDispatcherHandler {

    private ErrorHandler errorHandler;

    public HttpDispatcherHandler(HandlerContext context, ActionHolder holder) {
        super(context,holder);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
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
        List<Filter> filters = holder.getFilters(router);
        ActionWrapper wrapper = holder.getAction(router);
        HttpHandlerRequest req = new HttpHandlerRequest(request, router, context, ctx.channel());
        HttpHandlerResponse resp = new HttpHandlerResponse(response, context, ctx.channel());
        Action action = null;
        if (wrapper != null && wrapper.getAction() != null) {
            action = wrapper.getAction();
        }
        if (filters == null || filters.isEmpty() && action == null) {
            this.errorHandler = new ErrorHandler(HttpResponseStatus.NOT_FOUND.code(), HttpResponseStatus.NOT_FOUND.reasonPhrase());
            errorHandler.handle(req, resp);
            return;
        }
        if (wrapper.getStatus() == HttpStatus.METHOD_NOT_ALLOWED) {
            this.errorHandler = new ErrorHandler(HttpResponseStatus.METHOD_NOT_ALLOWED.code(), HttpResponseStatus.METHOD_NOT_ALLOWED.reasonPhrase());
            errorHandler.handle(req, resp);
            return;
        }
        if (!filters.isEmpty()) {
            for (Filter filter : filters) {
                if (!filter.before(req, resp)) {
                    return;
                }
            }
        }
        HttpHandler handler = (HttpHandler) action;
        handler.handle(req, resp);
        if (!filters.isEmpty()) {
            for (Filter filter : filters) {
                filter.after(req, resp);
            }
        }
    }

}
