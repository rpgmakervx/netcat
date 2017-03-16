package org.easyarch.netcat.context;

import org.easyarch.netcat.http.protocol.HttpStatus;
import org.easyarch.netcat.kits.StringKits;
import org.easyarch.netcat.mvc.action.Action;
import org.easyarch.netcat.mvc.action.ActionWrapper;
import org.easyarch.netcat.mvc.action.filter.HttpFilter;
import org.easyarch.netcat.mvc.router.Router;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.easyarch.netcat.mvc.router.Router.LEFT;
import static org.easyarch.netcat.mvc.router.Router.RIGHT;

/**
 * Created by xingtianyu on 17-3-3
 * 下午6:11
 * description:
 */

public class ActionHolder {

    /**
     * handler写path; filter写类全名
     * 顺序的连式结构
     */
    private static Map<Router, ActionWrapper> actions = new LinkedHashMap<>();

    /**
     * 1.遍历所有的router和action映射
     * 2.做eq对比，参数化url和真正url对比出结果
     * 3.不相等的继续遍历比较，相等的进行下一个逻辑
     * 4.如果是path完全相等，则直接返回action,返回前检查method是否匹配，否则返回405；
     * 5.否则是参数化匹配的结果，记录结果，继续遍历（因为path完全相等优先级最高）
     *
     * @param router
     * @return
     */
    public ActionWrapper getAction(Router router) {
        ActionWrapper wrapper = null;
        for (Map.Entry<Router, ActionWrapper> entry : actions.entrySet()) {
            Router r = entry.getKey();
            boolean equals = eq(r, router);
            if (equals) {
                if (r.getPath().equals(router.getPath())) {
                    wrapper = entry.getValue();
                    if (!r.getMethod().equals(router.getMethod())){
                        wrapper.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
                    }
                    return wrapper;
                }
                wrapper = entry.getValue();
                if (!r.getMethod().equals(router.getMethod())){
                    wrapper.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
                }
            }
        }
        return wrapper;
    }

    /**
     * 获取拦截这个请求的所有拦截器
     * 便利顺序的action集合，如果命中router则说明
     * @param router
     * @return
     */
    public List<HttpFilter> getFilters(Router router) {
        ActionWrapper wrapper = null;
        for (Map.Entry<Router, ActionWrapper> entry : actions.entrySet()) {
            Router r = entry.getKey();
            boolean equals = eq(r, router);
            if (equals) {
                if (r.getPath().equals(router.getPath())) {
                    wrapper = entry.getValue();
                    if (wrapper == null) {
                        return null;
                    }
                    return wrapper.getFilters();
                }
                wrapper = entry.getValue();
            }
        }
        if (wrapper == null) {
            return null;
        }
        return wrapper.getFilters();
    }

    public void addAction(Router router, Action action) {
        int currentIndex = actions.size();
        ActionWrapper wrapper = new ActionWrapper(action, currentIndex);
        int index = 0;
        for (ActionWrapper rw : actions.values()) {
            if (index == currentIndex - 1) {
                wrapper.setPreAction(rw);
                break;
            }
            index++;
        }
        actions.put(router, wrapper);
        System.out.println("add Router:" + router + ", actionsize:" + actions.size());
    }

    /**
     * 1.路由层级不同，不相等
     * 2.路由地址相同必定相等
     * 3.路由包括参数化片段，则进一步匹配;如果不包括，不相等
     * 4.匹配每个片段，如果除了参数化片段都相等，则相等
     * eq方法执行过程中，将参数保存，供request获取
     *
     * @param router1
     * @param router2
     * @return
     */
    private boolean eq(Router router1, Router router2) {
        if (router1 == null || router2 == null) {
            return false;
        }
        if (router1.getLevel() != router2.getLevel()) {
            return false;
        }
        if (router1.getPath().equals(router2.getPath())) {
            return true;
        } else if (router1.isParameterize() || router2.isParameterize()) {
            for (int index = 0; index < router1.getLevel(); index++) {
                String seg1 = router1.getSegements().get(index);
                String seg2 = router2.getSegements().get(index);
                String paramSeg1 = router1.getParameterizeUrl().get(index);
                //两个片段不相等，或当前片段不是参数化的,则不相等
                if (!seg1.equals(seg2) && paramSeg1 == null) {
                    return false;
                }
                if (paramSeg1 != null) {
                    String name = StringKits.strip(paramSeg1, LEFT, RIGHT);
                    router2.getPathParams().put(name, seg2);
                }
            }
            return true;
        }
        return false;
    }

}
