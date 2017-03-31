package org.easyarch.netcat.test.filter;

import org.easyarch.netpet.web.http.request.impl.HttpHandlerRequest;
import org.easyarch.netpet.web.http.response.impl.HttpHandlerResponse;
import org.easyarch.netpet.web.mvc.action.filter.HttpFilter;

/**
 * Created by xingtianyu on 17-3-16
 * 上午11:38
 * description:
 */

public class LoginFilter implements HttpFilter {
    @Override
    public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        System.out.println("login filter");
        response.html("login");
        return false;
    }

    @Override
    public void after(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception{

    }
}
