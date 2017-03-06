package org.easyarch.netcat.mvc.route;

import org.easyarch.netcat.mvc.route.filter.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingtianyu on 17-3-3
 * 下午6:12
 * description:
 */

public class RouteWrapper {

    private Router self;

    private int index;

    private RouteType type;

    private RouteWrapper pre;

    public RouteWrapper(Router router){
        this.self = router;
        this.type = RouteType.getType(router);
        this.index = 0;
        this.pre = null;
    }
    public RouteWrapper(Router router,int index){
        this.self = router;
        this.type = RouteType.getType(router);
        this.index = index;
        this.pre = null;
    }

    public RouteWrapper(Router self, int index, RouteType type) {
        this.self = self;
        this.index = index;
        this.type = type;
        this.pre = null;
    }

    public Router getRouter() {
        return self;
    }

    public void setRouter(Router self) {
        this.self = self;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public RouteType getType() {
        return type;
    }

    public void setType(RouteType type) {
        this.type = type;
    }

    public RouteWrapper getPreRouter() {
        return pre;
    }

    public void setPreRouter(RouteWrapper wrapper) {
        this.pre = wrapper;
    }

    public List<Filter> getFilters(){
        List<Filter> filter = new ArrayList<>();
        RouteWrapper wrapper = this;
        while (wrapper != null){
            RouteType type = wrapper.getType();
            System.out.println("type:"+type);
            if (type == RouteType.FILTER){
                System.out.println("get filter");
                filter.add((Filter) wrapper.getRouter());
            }
            wrapper = wrapper.getPreRouter();
        }
        return filter;
    }
}
