package org.easyarch.netcat.mvc.route;

import org.easyarch.netcat.http.protocol.HttpMethod;
import org.easyarch.netcat.mvc.route.handler.RouteHandler;

/**
 * Description :
 * Created by xingtianyu on 17-2-23
 * 下午8:39
 * description:
 */

public class Router {

    private String path;

    private HttpMethod httpMethod;

    private RouteHandler handler;

    public Router(String path, HttpMethod httpMethod,RouteHandler handler) {
        this.path = path;
        this.httpMethod = httpMethod;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public RouteHandler getHandler() {
        return handler;
    }

    public void setHandler(RouteHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Router)) return false;

        Router router = (Router) o;

        if (path != null ? !path.equals(router.path) : router.path != null) return false;
        if (httpMethod != router.httpMethod) return false;
        return handler != null ? handler.equals(router.handler) : router.handler == null;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (httpMethod != null ? httpMethod.hashCode() : 0);
        result = 31 * result + (handler != null ? handler.hashCode() : 0);
        return result;
    }
}
