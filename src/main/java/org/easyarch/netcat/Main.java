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
        }).filter(new Filter() {
            @Override
            public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) {
                response.json(new Json("name","filter1","content","before1"));
                System.out.println("before1");
                return true;
            }

            @Override
            public void after(HttpHandlerRequest request, HttpHandlerResponse response) {
                System.out.println("after1");
            }
        }).get("/index", (request, response) -> {
            System.out.println("visit /index");
            response.html("hello");
        }).filter(new Filter() {
            @Override
            public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) {
                System.out.println("before2");
                response.json(new Json("name","filter2","content","before2"));
                return true;
            }

            @Override
            public void after(HttpHandlerRequest request, HttpHandlerResponse response) {
                System.out.println("after2");
            }
        }).get("/image", (request, response) -> {
            byte[] image = FileKits.read("/home/code4j/picture/04.jpeg");
            response.image(image);
        }).start(7070);
    }
}
