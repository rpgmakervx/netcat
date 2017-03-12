package org.easyarch.netcat.http.request;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

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
        this.fullReq = req;
    }

    /**
     * 解析请求参数
     * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
     * @throws IOException
     */
    public Map<String, String> parse() {
        HttpMethod method = fullReq.method();

        Map<String, String> parmMap = new HashMap<>();

        if (HttpMethod.GET == method) {
            // 是GET请求
            QueryStringDecoder decoder = new QueryStringDecoder(fullReq.uri());
//            System.out.println("query string param:"+decoder.parameters());
            decoder.parameters().entrySet().forEach( entry -> {
                // entry.getValue()是一个List, 只取第一个元素
                parmMap.put(entry.getKey(), entry.getValue().get(0));
            });
        } else if (HttpMethod.POST == method) {
            // 是POST请求
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullReq);
            decoder.offer(fullReq);

            List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();

            for (InterfaceHttpData parm : parmList) {
                Attribute data = (Attribute) parm;
                try {
                    parmMap.put(data.getName(), data.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }

        }
        return parmMap;
    }
}
