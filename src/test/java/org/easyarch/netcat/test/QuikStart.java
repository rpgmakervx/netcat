package org.easyarch.netcat.test;

import org.easyarch.netpet.asynclient.client.AsyncHttpClient;
import org.easyarch.netpet.asynclient.handler.callback.AsyncResponseHandler;
import org.easyarch.netpet.asynclient.http.entity.FileParam;
import org.easyarch.netpet.asynclient.http.entity.FileParamBuilder;
import org.easyarch.netpet.asynclient.http.response.AsyncHttpResponse;
import org.easyarch.netpet.web.mvc.entity.Json;

/**
 * Created by xingtianyu on 17-3-28
 * 上午12:23
 * description:
 */

public class QuikStart {

    public static void main(String[] args) throws Exception {
        AsyncHttpClient client = new AsyncHttpClient("http://127.0.0.1:8800");
        FileParam param = new FileParamBuilder()
                .buildFileParam("file","/home/code4j/util/elasticsearch-5.0.1.tar.gz")
                .build();

        client.postJson("/user/login", new Json("username", "xingtianyu"), new AsyncResponseHandler() {
            @Override
            public void onSuccess(AsyncHttpResponse response) {
                System.out.println("on success:\n"+response.getString());
            }

            @Override
            public void onFailure(int statusCode, Object message) {

            }
        });

        client.fileUpload("/upload", param,new AsyncResponseHandler() {
            @Override
            public void onSuccess(AsyncHttpResponse response) {
                System.out.println("on success:\n"+response.getString());
            }

            @Override
            public void onFailure(int statusCode, Object message) {
                System.out.println("on fail:\ncode:"+statusCode+", message:"+new String((byte[]) message));

            }
        });
    }
}
