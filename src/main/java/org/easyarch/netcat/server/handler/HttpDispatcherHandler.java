package org.easyarch.netcat.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.context.RouteHolder;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.Action;
import org.easyarch.netcat.mvc.action.filter.Filter;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;
import org.easyarch.netcat.mvc.action.handler.impl.DefaultHttpHandler;
import org.easyarch.netcat.mvc.action.handler.impl.NotFoundHandler;

import java.util.List;


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
    private RouteHolder holder;

    private HttpHandler defaultHttpHandler;
    private HttpHandler notFoundHttpHandler;
    public HttpDispatcherHandler(HandlerContext context, RouteHolder holder) {
        this.context = context;
        this.holder = holder;
        this.defaultHttpHandler = new DefaultHttpHandler();
        this.notFoundHttpHandler = new NotFoundHandler();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        boolean isSuccess = request.decoderResult().isSuccess();
        String uri = request.uri();
        String viewPath = context.getWebView();
        if (!isSuccess) {
            ctx.close();
            return;
        }
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        HttpHandlerRequest req = new HttpHandlerRequest(request,context,ctx.channel());
        HttpHandlerResponse resp = new HttpHandlerResponse(response,context,ctx.channel());

        defaultHttpHandler.handle(req,resp);

        List<Filter> filters = holder.getFilters(uri);
        Action action = holder.getRouter(uri);
        System.out.println("filters:" + filters+" action:"+action);
        if (filters == null || filters.isEmpty() && action == null) {
            notFoundHttpHandler.handle(req,resp);
            return;
        }
//        if (!filters.isEmpty()) {
//            for (Filter filter : filters) {
//                boolean interrupt = filter.before(req, resp);
//            }
//        }
//        if (!filters.isEmpty()) {
//            for (Filter filter : filters) {
//                filter.after(req, resp);
//            }
//        }
        HttpHandler handler = (HttpHandler)action;
        handler.handle(req,resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        StringBuffer buffer = new StringBuffer();
        buffer.append("<h1>500 Server Error</h1>");
        buffer.append("<p>" + cause.toString() + "</p>");
        byte[] content = buffer.toString().getBytes();
        ByteBuf buf = Unpooled.wrappedBuffer(content, 0, content.length);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        ctx.writeAndFlush(response);
    }
    /**
     * if (action instanceof ViewHttpHandler){
     String view = ((ViewHttpHandler) action).handle(req,resp);
     System.out.println("viewhandler:"+ view);
     if (view.startsWith(REDIRECT)){
     FullHttpResponse copyResponse = new DefaultFullHttpResponse(
     HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
     ctx.writeAndFlush(copyResponse);
     }else{
     StringBuffer buffer = new StringBuffer();
     buffer.append(viewPath);
     buffer.append(view);
     byte[] content = FileKits.read(buffer.toString());
     System.out.println("path:"+buffer.toString());
     System.out.println("file content:"+content.length);
     ByteBuf buf = Unpooled.wrappedBuffer(content,0, content.length);
     FullHttpResponse copyResponse = new DefaultFullHttpResponse(
     HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buf);
     HttpHeaders headers = copyResponse.headers();
     headers.set(HttpHeaderName.CONTENT_TYPE, HttpHeaderValue.TEXT_HTML);
     headers.set(HttpHeaderName.CONTENT_LENGTH,content.length);
     ctx.writeAndFlush(copyResponse);
     }
     }else if (action instanceof JsonHttpHandler){
     Json json = ((JsonHttpHandler) action).handle(req,resp);
     byte[] content = JSONObject.toJSONBytes(json.getJsonMap(),
     SerializerFeature.PrettyFormat);
     ByteBuf buf = Unpooled.wrappedBuffer(content,0, content.length);
     FullHttpResponse copyResponse = new DefaultFullHttpResponse(
     HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buf);
     HttpHeaders headers = copyResponse.headers();
     headers.set(HttpHeaderName.CONTENT_TYPE, HttpHeaderValue.APPLICATION_JSON);
     headers.set(HttpHeaderName.CONTENT_LENGTH,content.length);
     ctx.writeAndFlush(copyResponse);
     }else if (action instanceof StringHttpHandler){
     byte[] content = ((StringHttpHandler) action).handle(req,resp).getBytes("UTF-8");
     ByteBuf buf = Unpooled.wrappedBuffer(content,0, content.length);
     FullHttpResponse copyResponse = new DefaultFullHttpResponse(
     HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buf);
     HttpHeaders headers = copyResponse.headers();
     headers.set(HttpHeaderName.CONTENT_TYPE, HttpHeaderValue.TEXT_PLAIN);
     headers.set(HttpHeaderName.CONTENT_LENGTH,content.length);
     ctx.writeAndFlush(copyResponse);
     }else if (action instanceof ByteHttpHandler){
     byte[] content = ((ByteHttpHandler) action).handle(req,resp);
     ByteBuf buf = Unpooled.wrappedBuffer(content,0, content.length);
     FullHttpResponse copyResponse = new DefaultFullHttpResponse(
     HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buf);
     HttpHeaders headers = copyResponse.headers();
     headers.set(HttpHeaderName.CONTENT_LENGTH,content.length);
     ctx.writeAndFlush(copyResponse);
     }
     */
}
