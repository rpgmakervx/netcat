package org.easyarch.netcat.http.request;

import org.easyarch.netcat.http.protocol.HttpMethod;

import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-24
 * 下午5:19
 * description:
 */

public interface Request{

    public Map<String,String> getHeaders();

    public String getHeader(String name);

    public String getParam(String name);

    public Map<String,String[]> getParams();

    public String getProtocol();

    public HttpMethod getMethod();

    public String getPath();

    public String getUrl();

    public String getQueryString();


}
