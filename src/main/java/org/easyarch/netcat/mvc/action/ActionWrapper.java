package org.easyarch.netcat.mvc.action;

import org.easyarch.netcat.mvc.action.filter.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingtianyu on 17-3-3
 * 下午6:12
 * description:
 */

public class ActionWrapper {

    private Action self;

    private int index;

    private ActionType type;

    private ActionWrapper pre;

    public ActionWrapper(Action action){
        this.self = action;
        this.type = ActionType.getType(action);
        this.index = 0;
        this.pre = null;
    }
    public ActionWrapper(Action action, int index){
        this.self = action;
        this.type = ActionType.getType(action);
        this.index = index;
        this.pre = null;
    }

    public ActionWrapper(Action self, int index, ActionType type) {
        this.self = self;
        this.index = index;
        this.type = type;
        this.pre = null;
    }

    public Action getRouter() {
        return self;
    }

    public void setRouter(Action self) {
        this.self = self;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public ActionWrapper getPreRouter() {
        return pre;
    }

    public void setPreRouter(ActionWrapper wrapper) {
        this.pre = wrapper;
    }

    public List<Filter> getFilters(){
        List<Filter> filter = new ArrayList<>();
        ActionWrapper wrapper = this;
        while (wrapper != null){
            ActionType type = wrapper.getType();
            System.out.println("type:"+type);
            if (type == ActionType.FILTER){
                System.out.println("get filter");
                filter.add((Filter) wrapper.getRouter());
            }
            wrapper = wrapper.getPreRouter();
        }
        return filter;
    }
}
