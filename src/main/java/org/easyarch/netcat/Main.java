package org.easyarch.netcat;

import org.easyarch.netcat.http.protocol.HttpHeaderValue;
import org.easyarch.netcat.kits.file.FileKits;
import org.easyarch.netcat.server.App;

/**
 * Created by xingtianyu on 17-3-4
 * 下午6:42
 * description:
 */

public class Main {

    public static void main(String[] args) {
        App app = new App();
        app.get("/index", (request, response) -> {
            response.html("hello");
        }).get("/image", (request, response) -> {
            byte[] image = FileKits.read("/home/code4j/picture/04.jpeg");
            response.image(image);
        }).get("/download", (request, response) -> {
            byte[] pdf = FileKits.read("/home/code4j/58daojia/技术/文档/mybatis-plus.pdf");
            response.download(pdf,"mybatis权威指南", HttpHeaderValue.PDF);
        }).start(7070);
    }
}
