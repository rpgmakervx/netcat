package org.easyarch.netcat.http.session.impl;

import org.easyarch.netcat.http.session.HttpSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xingtianyu on 17-3-14
 * 下午8:35
 * description:
 */

public class DefaultHttpSession implements HttpSession {


    private Map<String,Object> sessionMap = new HashMap<>();

    private int maxAge;

    public Object getAttr(String name){
        return sessionMap.get(name);
    }
    public void setAttr(String name,String value){
        sessionMap.put(name,value);
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

}
