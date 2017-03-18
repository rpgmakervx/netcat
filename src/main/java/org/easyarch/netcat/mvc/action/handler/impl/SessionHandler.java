package org.easyarch.netcat.mvc.action.handler.impl;

import io.netty.channel.ChannelHandlerContext;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.cookie.HttpCookie;
import org.easyarch.netcat.http.request.HandlerRequest;
import org.easyarch.netcat.http.response.HandlerResponse;
import org.easyarch.netcat.kits.HashKits;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;

import java.net.InetSocketAddress;

import static org.easyarch.netcat.http.Const.NETCATID;

/**
 * Created by xingtianyu on 17-3-17
 * 上午12:43
 * description:
 */

public class SessionHandler implements HttpHandler {

    private ChannelHandlerContext ctx;

    public SessionHandler(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }

    @Override
    public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
        HandlerContext context = request.getContext();
        if (request.getCookies().isEmpty()){
            HttpCookie cookie = new HttpCookie(NETCATID,
                    HashKits.sha1(ctx.channel().id().asLongText()));
            InetSocketAddress hostAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            cookie.setDomain(hostAddress.getHostName());
            cookie.setPath(context.getContextPath());
            response.addCookie(cookie);
        }
    }
}
