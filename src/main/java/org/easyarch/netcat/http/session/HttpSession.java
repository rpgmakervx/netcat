package org.easyarch.netcat.http.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-27
 * 上午12:01
 * description:
 */

public class HttpSession implements Serializable {

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
