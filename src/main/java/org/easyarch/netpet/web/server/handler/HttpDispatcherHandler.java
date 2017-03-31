package org.easyarch.netpet.web.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.easyarch.netpet.web.context.ActionHolder;
import org.easyarch.netpet.web.context.HandlerContext;
import org.easyarch.netpet.web.http.protocol.HttpStatus;
import org.easyarch.netpet.web.http.request.impl.HttpHandlerRequest;
import org.easyarch.netpet.web.http.response.impl.HttpHandlerResponse;
import org.easyarch.netpet.web.mvc.action.Action;
import org.easyarch.netpet.web.mvc.action.ActionType;
import org.easyarch.netpet.web.mvc.action.ActionWrapper;
import org.easyarch.netpet.web.mvc.action.filter.HttpFilter;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;
import org.easyarch.netpet.web.mvc.action.handler.impl.ErrorHandler;
import org.easyarch.netpet.web.mvc.action.handler.impl.SessionHandler;
import org.easyarch.netpet.web.mvc.router.Router;

import java.util.List;


/**
 * Description :
 * Created by xingtianyu on 17-2-23
 * 下午4:52
 * description:
 * 1.请求路由
 * 2.判断要执行的处理器
 * 3.执行对应处理器
 */

public class HttpDispatcherHandler extends BaseDispatcherHandler {

    private ErrorHandler errorHandler;

    public HttpDispatcherHandler(HandlerContext context, ActionHolder holder) {
        super(context, holder);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        Router router = new Router(request.uri(),
                ActionType.HANDLER, org.easyarch.netpet.web.http.protocol.HttpMethod.getMethod(request.method()));
        System.out.println("request method:"+request.method()+" , router:"+router);
        ActionWrapper wrapper = holder.getAction(router);
        List<HttpFilter> filters;
        filters = holder.getFilters(router);
        Action action = null;
        //直接从wrapper获取比遍历一遍快
        if (wrapper != null) {
            action = wrapper.getAction();
        }
        HttpHandlerRequest req = new HttpHandlerRequest(request, router, context, ctx.channel());
        HttpHandlerResponse resp = new HttpHandlerResponse(response, context, ctx.channel());
        System.out.println("404 ? :"+(filters == null || filters.isEmpty() && action == null));

        if (action == null) {
            this.errorHandler = new ErrorHandler(HttpResponseStatus.NOT_FOUND.code(), HttpResponseStatus.NOT_FOUND.reasonPhrase());
            errorHandler.handle(req, resp);
            return;
        }
        if (wrapper != null&&wrapper.getStatus() == HttpStatus.METHOD_NOT_ALLOWED) {
            System.out.println("method not allowd");
            this.errorHandler = new ErrorHandler(HttpResponseStatus.METHOD_NOT_ALLOWED.code(), HttpResponseStatus.METHOD_NOT_ALLOWED.reasonPhrase());
            errorHandler.handle(req, resp);
            return;
        }
        SessionHandler sessionHandler = new SessionHandler(ctx);
        HttpHandler handler = (HttpHandler) action;
        if (!filters.isEmpty()) {
            for (HttpFilter filter : filters) {
                if (!filter.before(req, resp)) {
                    return;
                }
            }
        }
        sessionHandler.handle(req,resp);
        handler.handle(req, resp);
        if (!filters.isEmpty()) {
            for (HttpFilter filter : filters) {
                filter.after(req, resp);
            }
        }
    }

}
