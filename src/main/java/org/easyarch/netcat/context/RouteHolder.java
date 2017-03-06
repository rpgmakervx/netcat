package org.easyarch.netcat.context;

import org.easyarch.netcat.mvc.route.RouteWrapper;
import org.easyarch.netcat.mvc.route.Router;
import org.easyarch.netcat.mvc.route.filter.Filter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xingtianyu on 17-3-3
 * 下午6:11
 * description:
 */

public class RouteHolder {

    /**
     * handler写path; filter写类全名
     */
    private static Map<String, RouteWrapper> routers = new LinkedHashMap<>();

    public Router getRouter(String path) {
        RouteWrapper wrapper = routers.get(path);
        if (wrapper == null) {
            return null;
        }
        return wrapper.getRouter();
    }

    public List<Filter> getFilters(String path) {
        RouteWrapper wrapper = routers.get(path);
        if (wrapper == null) {
            return new ArrayList<>();
        }
        return wrapper.getFilters();
    }

    public void addRouter(String path, Router router) {
        int currentIndex = routers.size();
        RouteWrapper wrapper = new RouteWrapper(router, currentIndex);
        int index = 0;
        for (RouteWrapper rw:routers.values()){
            if (index == currentIndex - 1){
                System.out.println("add pre wrapper:"+rw.getType());
                wrapper.setPreRouter(rw);
                break;
            }
            index++;
        }
        System.out.println("add router:" + wrapper.getType());
        routers.put(path, wrapper);
    }

}
