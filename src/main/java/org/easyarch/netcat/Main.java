package org.easyarch.netcat;

import org.easyarch.netcat.http.protocol.HttpHeaderValue;
import org.easyarch.netcat.kits.file.FileKits;
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
        }).get("/index", (request, response) -> {
            response.json(new Json("username",request.getParameter("username")
                    ,"code",request.getParameter("code")));
        }).get("/image/{id}", (request, response) -> {
            byte[] image = FileKits.read("/home/code4j/picture/04.jpeg");
            response.image(image);
        }).get("/get/{id}", (request, response) -> {
            response.html("index-v1");
        }).get("/text", (request, response) -> {
            response.text("<h3>hello world</h3>");
        }).start(7070);
    }
}
