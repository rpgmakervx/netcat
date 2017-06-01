package org.easyarch.netpet.web.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import org.easyarch.netpet.kits.HashKits;
import org.easyarch.netpet.kits.StringKits;
import org.easyarch.netpet.web.context.ActionHolder;
import org.easyarch.netpet.web.context.HandlerContext;
import org.easyarch.netpet.web.http.Const;
import org.easyarch.netpet.web.http.cookie.HttpCookie;
import org.easyarch.netpet.web.http.protocol.HttpHeaderName;
import org.easyarch.netpet.web.http.protocol.HttpStatus;
import org.easyarch.netpet.web.http.request.impl.HttpHandlerRequest;
import org.easyarch.netpet.web.http.response.impl.HttpHandlerResponse;
import org.easyarch.netpet.web.http.session.HttpSession;
import org.easyarch.netpet.web.http.session.impl.DefaultHttpSession;
import org.easyarch.netpet.web.mvc.action.Action;
import org.easyarch.netpet.web.mvc.action.ActionType;
import org.easyarch.netpet.web.mvc.action.ActionWrapper;
import org.easyarch.netpet.web.mvc.action.filter.HttpFilter;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;
import org.easyarch.netpet.web.mvc.action.handler.impl.ErrorHandler;
import org.easyarch.netpet.web.mvc.router.Router;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.easyarch.netpet.web.http.protocol.HttpHeaderName.COOKIE;
import static org.easyarch.netpet.web.http.protocol.HttpHeaderName.SET_COOKIE;


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
        System.out.println("请求被触发 active");
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
        checkCookie(ctx,request,response);
        HttpHandlerRequest req = new HttpHandlerRequest(request, router, context, ctx.channel());
        HttpHandlerResponse resp = new HttpHandlerResponse(response,req.getSessionId(), context, ctx.channel());
//        SessionHandler sessionHandler = new SessionHandler(ctx);
//        sessionHandler.handle(req,resp);
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
        HttpHandler handler = (HttpHandler) action;
        if (!filters.isEmpty()) {
            for (HttpFilter filter : filters) {
                if (!filter.before(req, resp)) {
                    return;
                }
            }
        }
        handler.handle(req, resp);
        if (!filters.isEmpty()) {
            for (HttpFilter filter : filters) {
                filter.after(req, resp);
            }
        }
    }

    /**
     * 初始化链接时创建cookie和session
     * @param ctx
     * @param request
     * @param response
     */
    private void checkCookie(ChannelHandlerContext ctx,FullHttpRequest request,FullHttpResponse response){
        String cookieValue = request.headers().get(HttpHeaderName.COOKIE);
        Set<Cookie> cookies;
        if (StringKits.isEmpty(cookieValue)){
            cookies = Collections.emptySet(); ;
        }else {
            ServerCookieDecoder decoder = ServerCookieDecoder.LAX;
            cookies = decoder.decode(cookieValue);
        }
        String sessionId = HashKits
                .sha1(ctx.channel().id().asLongText());
        boolean flag = true;
        for(Cookie cookie:cookies){
            if (Const.NETPETID.equals(cookie.name())){
                flag = false;
                sessionId = cookie.value();
                break;
            }
        }
        if (flag){
            createCookieSession(sessionId,ctx,request,response);
        }
        for (Cookie cookie:cookies){
            if (cookie.name().equals(Const.NETPETID)
                    &&!sessionId.equals(cookie.value())){
                createCookieSession(sessionId,ctx,request,response);
            }
        }
    }
    private void createCookieSession(String sessionId,ChannelHandlerContext ctx,FullHttpRequest request, FullHttpResponse response){
        HttpCookie cookie = new HttpCookie(Const.NETPETID,sessionId);
        InetSocketAddress hostAddress =
                (InetSocketAddress) ctx.channel().remoteAddress();
        cookie.setDomain(hostAddress.getHostName());
        cookie.setPath(context.getContextPath());
        response.headers().add(SET_COOKIE,cookie.getWrapper());
        request.headers().add(COOKIE,cookie.getWrapper());
        HttpSession session = new DefaultHttpSession();
        session.setSessionId(sessionId);
        session.setMaxAge(context.getSessionAge());
        context.addSession(sessionId,session);
    }

}
