package org.easyarch.netcat.context;

import org.easyarch.netcat.mvc.action.ActionWrapper;
import org.easyarch.netcat.mvc.action.Action;
import org.easyarch.netcat.mvc.action.filter.Filter;
import org.easyarch.netcat.mvc.router.Router;

import java.util.*;

/**
 * Created by xingtianyu on 17-3-3
 * 下午6:11
 * description:
 */

public class ActionHolder {

    /**
     * handler写path; filter写类全名
     */
    private static Map<Router, ActionWrapper> actions = new LinkedHashMap<>();

    public Action getAction(Router router) {
        System.out.println("get ActionRouter:"+router);
        ActionWrapper wrapper = actions.get(router);
        System.out.println("get Action:"+wrapper);
        if (wrapper == null) {
            return null;
        }
        return wrapper.getAction();
    }

    public List<Filter> getFilters(Router router) {
        ActionWrapper wrapper = actions.get(router);
        if (wrapper == null) {
            return new ArrayList<>();
        }
        return wrapper.getFilters();
    }

    public void addAction(Router router, Action action) {
        int currentIndex = actions.size();
        ActionWrapper wrapper = new ActionWrapper(action, currentIndex);
        int index = 0;
        for (ActionWrapper rw: actions.values()){
            if (index == currentIndex - 1){
                System.out.println("add pre wrapper:"+rw.getType());
                wrapper.setPreAction(rw);
                break;
            }
            index++;
        }
        actions.put(router, wrapper);
    }

}
