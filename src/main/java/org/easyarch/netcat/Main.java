package org.easyarch.netcat;

import org.easyarch.netcat.http.protocol.HttpHeaderValue;
import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.kits.file.FileKits;
import org.easyarch.netcat.mvc.action.filter.Filter;
import org.easyarch.netcat.mvc.entity.Json;
import org.easyarch.netcat.server.App;

/**
 * Created by xingtianyu on 17-3-4
 * 下午6:42
 * description:
 */

public class Main {

    public static void main(String[] args) {
        App app = new App();
        app.get("/download", (request, response) -> {
            byte[] pdf = FileKits.read("/home/code4j/58daojia/技术/文档/mybatis-plus.pdf");
            response.download(pdf,"mybatis权威指南", HttpHeaderValue.PDF);
        }).get("/index/{username}", (request, response) -> {
            response.json(new Json("username",request.getParameter("username")
                    ,"code",200));
        }).filter(new Filter() {
            @Override
            public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) {
                response.html("notfound2");
                return false;
            }

            @Override
            public void after(HttpHandlerRequest request, HttpHandlerResponse response) {
                System.out.println("after-->"+request.getRequestURI());
            }
        }).get("/image", (request, response) -> {
            byte[] image = FileKits.read("/home/code4j/picture/04.jpeg");
            response.image(image);
        }).get("/get/{id}", (request, response) -> {
            System.out.println("request:"+request.getParameter("id"));
            response.text("<h3>"+request.getParameter("id")+"</h3>");
        }).get("/get/trade", (request, response) -> {
            response.html("index-v1");
        }).start(7070);
    }
}
