package org.easyarch.netpet.web.server;

import org.easyarch.netpet.web.context.ActionHolder;
import org.easyarch.netpet.web.context.config.HandlerConfig;
import org.easyarch.netpet.web.context.HandlerContext;
import org.easyarch.netpet.web.http.protocol.HttpMethod;
import org.easyarch.netpet.kits.StringKits;
import org.easyarch.netpet.web.mvc.action.ActionType;
import org.easyarch.netpet.web.mvc.action.filter.HttpFilter;
import org.easyarch.netpet.web.mvc.action.handler.HttpHandler;
import org.easyarch.netpet.web.mvc.router.Router;

/**
 * Description :
 * Created by xingtianyu on 17-2-23
 * 下午4:22
 * description:
 */

final public class App {


    private HandlerContext context;
    private ActionHolder holder;

    private Launcher launcher;

    private HandlerConfig handlerConfig;

    public App(){
        context = new HandlerContext();
        holder = new ActionHolder();
        this.handlerConfig = new HandlerConfig(context);
        launcher = new Launcher(context,holder);
    }

    public void start(){
        launcher.start();
    }
    public void start(int port){
        launcher.start(port);
    }

    public App filter(String path,HttpFilter filter){
        if (filter == null){
            return this;
        }
        holder.addFilter(new Router(path, ActionType.FILTER),filter);
        return this;
    }

    public App get(String path, HttpHandler httpHandler){
        return receive(path,httpHandler,HttpMethod.GET);
    }

    public App post(String path,HttpHandler httpHandler){
        return receive(path,httpHandler,HttpMethod.POST);
    }
    public App put(String path,HttpHandler httpHandler){
        return receive(path,httpHandler,HttpMethod.PUT);
    }
    public App delete(String path,HttpHandler httpHandler){
        return receive(path,httpHandler,HttpMethod.DELETE);
    }
    public App receive(String path,HttpHandler httpHandler,HttpMethod method){
        //"/shopping/user/login"
        if (StringKits.isEmpty(path)||httpHandler == null){
            return this;
        }
        if (path.startsWith("/")){
            path = path.substring(1,path.length());
        }
        holder.addAction(new Router(context.getContextPath() + path,ActionType.HANDLER, method),httpHandler);
        return this;
    }

    public HandlerConfig config(){
        return this.handlerConfig;
    }

}
