package org.easyarch.netpet.asynclient;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import org.easyarch.netpet.web.mvc.entity.Json;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 16-12-8
 * 上午1:35
 */

public class AsyncHttpClient {

    private ClientLauncher launcher;

    public AsyncHttpClient(String url){
        try {
            launcher = new ClientLauncher(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public void send(FullHttpRequest request) throws Exception {
        launcher.doRequest(request.method()
                , request.headers(), request.content());
    }

    public void get(HttpHeaders headers) throws Exception {
        launcher.doRequest(HttpMethod.GET, headers, null);
    }

    public void get() throws Exception {
        launcher.doRequest(HttpMethod.GET, null, null);
    }

    public byte[] getc(HttpHeaders headers) throws Exception {
        launcher.doRequest(HttpMethod.GET, headers, null);
        return getContent();
    }

    public byte[] getc() throws Exception {
        launcher.doRequest(HttpMethod.GET, null, null);
        return getContent();
    }

    public void post(HttpHeaders headers, byte[] bytes) throws Exception {
        if (bytes == null||bytes.length == 0) {
            bytes = new byte[0];
        }
        launcher.doRequest(HttpMethod.POST, headers, Unpooled.wrappedBuffer(bytes));
    }

    public void post(byte[] bytes) throws Exception {
        if (bytes == null||bytes.length == 0) {
            bytes = new byte[0];
        }
        launcher.doRequest(HttpMethod.POST, null, Unpooled.wrappedBuffer(bytes));
    }

    public byte[] postc(HttpHeaders headers, byte[] bytes) throws Exception {
        if (bytes == null||bytes.length == 0) {
            bytes = new byte[0];
        }
        launcher.doRequest(HttpMethod.POST, headers, Unpooled.wrappedBuffer(bytes));
        return getContent();
    }

    public byte[] postc(byte[] bytes) throws Exception {
        if (bytes == null||bytes.length == 0) {
            bytes = new byte[0];
        }
        launcher.doRequest(HttpMethod.POST, null, Unpooled.wrappedBuffer(bytes));
        return getContent();
    }

    public void postJson(Json json) throws Exception {
        if (json == null||json.isEmpty()){
            json = new Json();
        }
        String jsonString = Json.stringify(json);
        post(jsonString.getBytes());
    }

    public void postJson(Map<String,Object> jsonMap) throws Exception {
        String jsonString = Json.stringify(jsonMap);
        post(jsonString.getBytes());
    }

    public void put(String url, HttpHeaders headers, byte[] bytes) throws Exception {
        if (bytes == null||bytes.length == 0) {
            bytes = new byte[0];
        }
        launcher.doRequest(url, HttpMethod.PUT, headers, Unpooled.wrappedBuffer(bytes));
    }
    public void put(String url, byte[] bytes) throws Exception {
        if (bytes == null||bytes.length == 0) {
            bytes = new byte[0];
        }
        launcher.doRequest(url, HttpMethod.PUT, null, Unpooled.wrappedBuffer(bytes));
    }

    public void delete(String url, HttpHeaders headers) throws Exception {
        launcher.doRequest(url, HttpMethod.DELETE, headers, null);
    }
    public void delete(String url) throws Exception {
        launcher.doRequest(url, HttpMethod.DELETE, null, null);
    }

    public byte[] getContent(){
        return launcher.getContentAsStream();
    }
//    public static void main(String[] args) throws Exception {
//        AsyncHttpClient client = new AsyncHttpClient("http://www.baidu.com");
//        client.get("http://www.baidu.com/",null);
//        String content = new String(client.getContentAsStream());
//        System.out.println(content);
//    }
}
