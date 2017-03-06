package org.easyarch.netcat.mvc.route.filter;

import org.easyarch.netcat.mvc.route.handler.HttpHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingtianyu on 17-3-2
 * 下午5:48
 * description:
 */

public class FilterWrapper {

    private int offset;

    private Filter self;
    private Filter pre;

    private List<HttpHandler> handlers;

    public FilterWrapper(Filter self) {
        this(0,self,new ArrayList<>());
    }

    public FilterWrapper(int offset, Filter self, List<HttpHandler> handlers) {
        this.offset = offset;
        this.self = self;
        if (self instanceof HttpHandler){
            this.handlers = new ArrayList<>();
        }else {
            this.handlers = handlers;
        }
    }

    public Filter getInterceptor(){
        return this.self;
    }

    public void addHandler(HttpHandler handler){
        this.handlers.add(handler);
    }

    public List<HttpHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<HttpHandler> handlers) {
        this.handlers = handlers;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }
}
