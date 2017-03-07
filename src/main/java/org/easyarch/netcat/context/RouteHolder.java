package org.easyarch.netcat.context;

import org.easyarch.netcat.mvc.action.ActionWrapper;
import org.easyarch.netcat.mvc.action.Action;
import org.easyarch.netcat.mvc.action.filter.Filter;

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
    private static Map<String, ActionWrapper> routers = new LinkedHashMap<>();

    public Action getRouter(String path) {
        ActionWrapper wrapper = routers.get(path);
        if (wrapper == null) {
            return null;
        }
        return wrapper.getRouter();
    }

    public List<Filter> getFilters(String path) {
        ActionWrapper wrapper = routers.get(path);
        if (wrapper == null) {
            return new ArrayList<>();
        }
        return wrapper.getFilters();
    }

    public void addRouter(String path, Action action) {
        int currentIndex = routers.size();
        ActionWrapper wrapper = new ActionWrapper(action, currentIndex);
        int index = 0;
        for (ActionWrapper rw:routers.values()){
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
