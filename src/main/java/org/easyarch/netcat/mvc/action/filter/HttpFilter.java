package org.easyarch.netcat.mvc.action.filter;

import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.Action;

/**
 * Created by xingtianyu on 17-3-2
 * 下午5:01
 * description:
 * before返回true会继续执行后续的action,返回false则不执行
 */

public interface HttpFilter extends Action {

    boolean before(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception;
    void after(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception;
}
