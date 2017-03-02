package org.easyarch.netcat.mvc.route;

import org.easyarch.netcat.mvc.route.handler.HttpHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingtianyu on 17-3-2
 * 下午5:48
 * description:
 */

public class RouteWrapper {

    private Router self;

    private List<Router> interceptedRouters;

    public RouteWrapper(Router self) {
        this(self,new ArrayList<>());
    }

    public RouteWrapper(Router self,List<Router> interceptedRouters) {
        this.self = self;
        if (self instanceof HttpHandler){
            this.interceptedRouters = new ArrayList<>();
        }else {
            this.interceptedRouters = interceptedRouters;
        }
    }

    public Router getRouter(){
        return this.self;
    }

    public boolean isHandler(){
        return self instanceof HttpHandler;
    }

    public void addRouter(Router router){
        this.interceptedRouters.add(router);
    }
}
