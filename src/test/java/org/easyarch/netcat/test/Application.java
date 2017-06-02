package org.easyarch.netcat.test;

import org.easyarch.netpet.web.http.request.HandlerRequest;
import org.easyarch.netpet.web.http.response.HandlerResponse;
import org.easyarch.netpet.web.http.session.HttpSession;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;
import org.easyarch.netpet.web.mvc.entity.Json;
import org.easyarch.netpet.web.server.App;

/**
 * Created by xingtianyu on 17-3-14
 * 下午2:31
 * description:
 */

public class Application {

    static String id = "";

    public static void main(String[] args) throws Exception {
//        AsyncHttpClient client = new AsyncHttpClient("http://localhost:8080");
//        client.get("/", new AsyncResponseHandlerAdapter() {
//            @Override
//            public void onSuccess(AsyncHttpResponse response) throws Exception {
//                System.out.println(response.getString());
//            }
//
//            @Override
//            public void onFailure(int statusCode, Object message) throws Exception {
//
//            }
//        });
        App app = new App();
//        app.config().contextPath("/shopping");
        app.get("/index", new HttpHandler() {
            @Override
            public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
                HttpSession session = request.getSession();
                session.setAttr("username","xingtianyu");
                System.out.println("index session:"+session);
                id = session.getSessionId();
                response.json(new Json("username","xingtianyu"));
            }
        });
        app.get("/session", new HttpHandler() {
            @Override
            public void handle(HandlerRequest request, HandlerResponse response) throws Exception {
                HttpSession session = request.getSession();
                String obj = (String) session.getAttr("username");
                System.out.println("session sessionId:"+session.getSessionId()+", static id:"+id);
                response.json(new Json("msg",obj));
            }
        });
        app.start(6789);
    }
}
