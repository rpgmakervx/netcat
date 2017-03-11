package org.easyarch.netcat.mvc.action;

import org.easyarch.netcat.mvc.action.filter.Filter;

import java.util.ArrayList;
import java.util.Collections;
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

    public Action getAction() {
        return self;
    }

    public void setAction(Action self) {
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

    public ActionWrapper getPreAction() {
        return pre;
    }

    public void setPreAction(ActionWrapper wrapper) {
        this.pre = wrapper;
    }

    public List<Filter> getFilters(){
        List<Filter> filter = new ArrayList<>();
        ActionWrapper wrapper = this;
        boolean continuly = true;
        while (wrapper != null){
            ActionType type = wrapper.getType();
            Action action = wrapper.getAction();
            if (type == ActionType.FILTER&&continuly){
                filter.add((Filter) action);
                ActionWrapper preWrapper = wrapper.getPreAction();
                //下一个action如果不是过滤器的话就结束过滤器遍历
                if (preWrapper.getType() != ActionType.FILTER
                        ||preWrapper == null){
                    continuly = false;
                }
            }
            wrapper = wrapper.getPreAction();
        }
        Collections.reverse(filter);
        return filter;
    }
}
