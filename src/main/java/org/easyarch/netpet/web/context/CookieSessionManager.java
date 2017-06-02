package org.easyarch.netpet.web.context;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import org.easyarch.netpet.kits.HashKits;
import org.easyarch.netpet.kits.StringKits;
import org.easyarch.netpet.web.http.Const;
import org.easyarch.netpet.web.http.cookie.HttpCookie;
import org.easyarch.netpet.web.http.protocol.HttpHeaderName;
import org.easyarch.netpet.web.http.session.HttpSession;
import org.easyarch.netpet.web.http.session.impl.DefaultHttpSession;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Set;

import static org.easyarch.netpet.web.http.protocol.HttpHeaderName.COOKIE;
import static org.easyarch.netpet.web.http.protocol.HttpHeaderName.SET_COOKIE;

/**
 * Created by xingtianyu on 17-6-1
 * 下午3:58
 * description:
 */

public class CookieSessionManager {

    private FullHttpRequest request;

    private FullHttpResponse response;

    private HandlerContext context;

    public CookieSessionManager(HandlerContext context, FullHttpRequest request, FullHttpResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
    }

    public HttpSession createSession(String sessionId, String hostName){

        HttpCookie cookie = new HttpCookie(Const.NETPETID,sessionId);
//        cookie.setDomain(hostName);
//        cookie.setPath(context.getContextPath());
        response.headers().set(SET_COOKIE,cookie.getWrapper());
        request.headers().set(COOKIE,cookie.getWrapper());

        HttpSession session = new DefaultHttpSession();
        session.setSessionId(sessionId);
        session.setMaxAge(context.getSessionAge());
        context.addSession(sessionId,session);
        return session;
    }

    public void checkCookie(ChannelHandlerContext ctx, FullHttpRequest request){
        String cookieValue = request.headers().get(HttpHeaderName.COOKIE);
        Set<Cookie> cookies;
        if (StringKits.isEmpty(cookieValue)){
            cookies = Collections.emptySet();
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
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        if (flag){
            createSession(sessionId,address.getHostName());
        }
        for (Cookie cookie:cookies){
            if (cookie.name().equals(Const.NETPETID)
                    &&!sessionId.equals(cookie.value())){
                createSession(sessionId,address.getHostName());
            }
        }
    }
}
