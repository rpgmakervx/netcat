package org.easyarch.netcat;

import org.easyarch.netcat.http.request.HttpHandlerRequest;
import org.easyarch.netcat.http.response.HttpHandlerResponse;
import org.easyarch.netcat.mvc.JsonHttpHandler;
import org.easyarch.netcat.mvc.ViewHttpHandler;
import org.easyarch.netcat.mvc.entity.Json;
import org.easyarch.netcat.mvc.route.filter.Filter;
import org.easyarch.netcat.server.App;

/**
 * Created by xingtianyu on 17-3-4
 * 下午6:42
 * description:
 */

public class Main {

    public static void main(String[] args) {
        App app = new App();
        app.filter(new Filter() {
            @Override
            public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) {
                System.out.println("brefore1");
                return true;
            }

            @Override
            public boolean after(HttpHandlerRequest request, HttpHandlerResponse response) {
                System.out.println("after1");
                return true;

            }
        }).filter(new Filter() {
            @Override
            public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) {
                System.out.println("brefore2");
                return true;
            }

            @Override
            public boolean after(HttpHandlerRequest request, HttpHandlerResponse response) {
                System.out.println("after2");
                return true;

            }
        }).get("/json", new JsonHttpHandler() {
            @Override
            public Json handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
                Json json = new Json();
                json.put("message","hello world");
                return json;
            }
        }).get("/index", new ViewHttpHandler() {
            @Override
            public String handle(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
                return "index-en.html";
            }
        }).start(9000);
    }
}
