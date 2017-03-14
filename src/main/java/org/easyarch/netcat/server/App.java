package org.easyarch.netcat.server;

import org.easyarch.netcat.context.ActionHolder;
import org.easyarch.netcat.context.HandlerContext;
import org.easyarch.netcat.http.protocol.HttpMethod;
import org.easyarch.netcat.kits.StringKits;
import org.easyarch.netcat.mvc.action.filter.Filter;
import org.easyarch.netcat.mvc.action.handler.HttpHandler;
import org.easyarch.netcat.mvc.router.Router;

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

    public App(){
        context = new HandlerContext();
        holder = new ActionHolder();
        launcher = new Launcher(context,holder);
    }

    public void start(){
        launcher.start();
    }
    public void start(int port){
        launcher.start(port);
    }

    public App filter(Filter filter){
        if (filter == null){
            return this;
        }
        holder.addAction(new Router(null),filter);
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
        if (StringKits.isEmpty(path)||httpHandler == null){
            return this;
        }

        holder.addAction(new Router(path, method),httpHandler);
        return this;
    }

    public App config(){
        return this;
    }


}
