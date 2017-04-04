package org.easyarch.netpet.web.http.request;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.*;
import org.easyarch.netpet.kits.ByteKits;
import org.easyarch.netpet.web.mvc.entity.Json;
import org.easyarch.netpet.web.mvc.entity.UploadFile;

import java.io.IOException;
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
     * @param req
     */
    public ParamParser(FullHttpRequest req) {
        this.fullReq = req.copy();
    }

    /**
     * 解析请求参数
     * post请求先解析json
     * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
     * @throws IOException
     */
    public Map<String, String> parse() {
        if (this.fullReq == null){
            return new HashMap<>();
        }
        HttpMethod method = fullReq.method();

        Map<String, String> parmMap = new HashMap<>();

        if (HttpMethod.GET == method) {
            // 是GET请求
            parmMap = decodeQueryString();
        } else if (HttpMethod.POST == method) {
            ByteBuf copyBuf = fullReq.content().copy();
            String requestData = ByteKits.toString(copyBuf);
            System.out.println("content length:"+requestData.length());
            System.out.println("content data:"+requestData);
            System.out.println("uri param:"+fullReq.uri());
            if (Json.isJson(requestData)){
                System.out.println("is json data");
                Json json = Json.parse(requestData);
                parmMap.putAll(json.getJsonMap());
            }
            // 是POST请求
            parmMap.putAll(decodeQueryString());
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false),fullReq);
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

    public UploadFile getFile(String name){
        byte[] content = new byte[0];
        UploadFile uploadFile = new UploadFile("null",content);
        if (this.fullReq == null){
            return uploadFile;
        }
        HttpMethod method = fullReq.method();
        if (HttpMethod.POST == method){
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false),fullReq);
            if (!decoder.isMultipart()){
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
