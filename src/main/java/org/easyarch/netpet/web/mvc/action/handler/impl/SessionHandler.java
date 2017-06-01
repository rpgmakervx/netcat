package org.easyarch.netpet.web.mvc.action.handler.impl;

import io.netty.channel.ChannelHandlerContext;
import org.easyarch.netpet.kits.HashKits;
import org.easyarch.netpet.web.context.HandlerContext;
import org.easyarch.netpet.web.http.Const;
import org.easyarch.netpet.web.http.cookie.HttpCookie;
import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;

import java.net.InetSocketAddress;
import java.util.Set;

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
        String sessionId = HashKits
                .sha1(ctx.channel().id().asLongText());
        Set<HttpCookie> cookies =  request.getCookies();
        boolean flag = false;
        for(HttpCookie cookie:cookies){
            if (Const.NETPETID.equals(cookie.name())){
                flag = true;
                break;
            }
        }
        if (!flag){
            createCookie(sessionId,response);
        }
        System.out.println("创建cookie的handler");
        for (HttpCookie cookie:request.getCookies()){
            if (cookie.name().equals(Const.NETPETID)
                    &&!sessionId.equals(cookie.value())){
                System.out.println("重建cookie");
                createCookie(sessionId,response);
                request.getSession();
            }
        }
    }

    private void createCookie(String sessionId, HandlerResponse response){
        HandlerContext context = response.getContext();
        HttpCookie cookie = new HttpCookie(Const.NETPETID,sessionId);
        InetSocketAddress hostAddress =
                (InetSocketAddress) ctx.channel().remoteAddress();
        cookie.setDomain(hostAddress.getHostName());
        cookie.setPath(context.getContextPath());
        response.addCookie(cookie);
        System.out.println("添加NETPETID cookie");
    }
}
