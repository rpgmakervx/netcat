package org.easyarch.netcat.test.filter;

import org.easyarch.netcat.http.request.impl.HttpHandlerRequest;
import org.easyarch.netcat.http.response.impl.HttpHandlerResponse;
import org.easyarch.netcat.mvc.action.filter.HttpFilter;
import org.easyarch.netcat.mvc.entity.Json;

/**
 * Created by xingtianyu on 17-3-17
 * 下午2:59
 * description:
 */

public class DemoFilter implements HttpFilter {

    @Override
    public boolean before(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {
        response.json(new Json("message","permission deny","code",401));
        return false;
    }

    @Override
    public void after(HttpHandlerRequest request, HttpHandlerResponse response) throws Exception {

    }
}
