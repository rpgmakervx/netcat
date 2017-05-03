package org.easyarch.netpet.web.http.request;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import org.easyarch.netpet.kits.ByteKits;
import org.easyarch.netpet.web.mvc.entity.Json;
import org.easyarch.netpet.web.mvc.entity.UploadFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xingtianyu on 17-3-9
 * 上午1:17
 * description:
 */

public class ParamParser {
    private FullHttpRequest fullReq;

    /**
     * 构造一个解析器
     * @param req 要解析的请求
     */
    public ParamParser(FullHttpRequest req) {
        this.fullReq = req.copy();
    }

    /**
     * 解析请求参数
     * post请求先解析json
     * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
     */
    public Map<String, String> parse() {
        if (this.fullReq == null){
            return new HashMap<>();
        }
        HttpMethod method = fullReq.method();
        HttpHeaders headers = fullReq.headers();
        String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE);
        Map<String, String> parmMap = new HashMap<>();

        if (HttpMethod.GET == method) {
            // 是GET请求
            parmMap = decodeQueryString();
        } else if (HttpMethod.POST == method) {
            ByteBuf copyBuf = fullReq.content().copy();
            String requestData = ByteKits.toString(copyBuf);
            System.out.println("content length:"+requestData.length());
            System.out.println("content type:"+contentType);
            if (Json.isJson(requestData)){
                System.out.println("is json data");
                Json json = Json.parse(requestData);
                parmMap.putAll(json.getJsonMap());
            }
            // 是POST请求
            parmMap.putAll(decodeQueryString());
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false),fullReq);
            if (HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString().equals(contentType)
                    ||decoder.isMultipart()){
                return parmMap;
            }
            List<InterfaceHttpData> paramlist = decoder.getBodyHttpDatas();
            for (InterfaceHttpData param : paramlist) {
                System.out.println("data type:"+param.getHttpDataType());
                if (param.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                    MemoryAttribute data = (MemoryAttribute) param;
                    parmMap.put(data.getName(), data.getValue());
                }
            }
        }
        System.out.println("result request data:"+parmMap);
        return parmMap;
    }

    public List<UploadFile> parseFiles(){
        List<UploadFile> files = new ArrayList<>();
        if (this.fullReq == null){
            return files;
        }
        HttpMethod method = fullReq.method();
        if (HttpMethod.POST != method){
            return files;
        }
        HttpHeaders headers = fullReq.headers();
        String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE);
        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                new DefaultHttpDataFactory(false),fullReq);
        if (!contentType.equals(HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                &&!decoder.isMultipart()){
            return files;
        }
        List<InterfaceHttpData> paramlist = decoder.getBodyHttpDatas();
        for (InterfaceHttpData param : paramlist) {
            if (param.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload){
                MemoryFileUpload data = (MemoryFileUpload) param;
                UploadFile uploadFile = new UploadFile(data.getFilename(),data.get(),false);
                uploadFile.setContentType(data.getContentType());
                files.add(uploadFile);
            }
        }
        return files;
    }

    public UploadFile parseFile(String name){
        byte[] content = new byte[0];
        UploadFile uploadFile = new UploadFile("null",content,false);
        if (this.fullReq == null){
            return uploadFile;
        }
        HttpMethod method = fullReq.method();
        HttpHeaders headers = fullReq.headers();
        String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE);
        if (HttpMethod.POST == method){
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false),fullReq);
            if (!HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString().equals(contentType)
                    &&!decoder.isMultipart()){
                return uploadFile;
            }
            List<InterfaceHttpData> paramlist = decoder.getBodyHttpDatas();
            for (InterfaceHttpData param : paramlist) {
                if (param.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload){
                    MemoryFileUpload data = (MemoryFileUpload) param;
                    if (data.getName().equals(name)){
                        content = data.get();
                        uploadFile.setContent(content);
                        uploadFile.setFileName(data.getFilename());
                        uploadFile.setContentType(data.getContentType());
                    }
                }
            }
        }
        return uploadFile;
    }

    private Map<String, String> decodeQueryString(){
        Map<String, String> paramMap = new HashMap<>();
        QueryStringDecoder decoder = new QueryStringDecoder(fullReq.uri());
        Map<String, List<String>> parame = decoder.parameters();
        for(Map.Entry<String, List<String>> entry:parame.entrySet()){
            paramMap.put(entry.getKey(), entry.getValue().get(0));
        }
        return paramMap;
    }
}
