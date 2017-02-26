package org.easyarch.netcat.context;

import org.easyarch.netcat.mvc.HttpHandler;

import javax.servlet.annotation.WebServlet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-2-26
 * 下午3:45
 * description:
 */
@WebServlet
public class ContextManager {

    public static Map<String,HttpHandler> servlets = new ConcurrentHashMap<>();

    public void addServlet(String url,HttpHandler handler){
        servlets.put(url,handler);
    }

    public HttpHandler getServlet(String url){
        return servlets.get(url);
    }
}
