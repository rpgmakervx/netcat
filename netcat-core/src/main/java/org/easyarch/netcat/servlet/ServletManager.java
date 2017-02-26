package org.easyarch.netcat.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :
 * Created by xingtianyu on 17-2-26
 * 下午3:45
 * description:
 */
@WebServlet
public class ServletManager {

    public static Map<String,HttpServlet> servlets = new ConcurrentHashMap<>();

    public void addServlet(String url,HttpServlet servlet){
        servlets.put(url,servlet);
    }

    public HttpServlet getServlet(String url){
        return servlets.get(url);
    }
}
