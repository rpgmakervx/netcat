package org.easyarch.netpet.web.context;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.easyarch.netpet.web.http.Const;
import org.easyarch.netpet.web.http.cookie.HttpCookie;
import org.easyarch.netpet.web.http.session.HttpSession;
import org.easyarch.netpet.web.http.session.impl.DefaultHttpSession;

import static org.easyarch.netpet.web.http.protocol.HttpHeaderName.COOKIE;
import static org.easyarch.netpet.web.http.protocol.HttpHeaderName.SET_COOKIE;

/**
 * Created by xingtianyu on 17-6-1
 * 下午3:58
 * description:
 */

public class CookieManager {

    private FullHttpRequest request;

    private FullHttpResponse response;

    private HandlerContext context;

    public CookieManager(HandlerContext context,FullHttpRequest request, FullHttpResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
    }

    public void createSession(String sessionId, String hostName){

        HttpCookie cookie = new HttpCookie(Const.NETPETID,sessionId);
        cookie.setDomain(hostName);
        cookie.setPath(context.getContextPath());
        response.headers().add(SET_COOKIE,cookie.getWrapper());
        request.headers().add(COOKIE,cookie.getWrapper());

        HttpSession session = new DefaultHttpSession();
        session.setSessionId(sessionId);
        session.setMaxAge(context.getSessionAge());
        context.addSession(sessionId,session);
    }
}
