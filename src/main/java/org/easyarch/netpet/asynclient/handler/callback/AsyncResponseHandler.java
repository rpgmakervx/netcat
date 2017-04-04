package org.easyarch.netpet.asynclient.handler.callback;

import org.easyarch.netpet.web.http.response.impl.HttpHandlerResponse;

/**
 * Created by xingtianyu on 17-4-4
 * 下午8:27
 * description:
 */

public interface AsyncResponseHandler<T> {

    public void onSuccess(HttpHandlerResponse response);

    public void onSuccess(T t);

    public void onFailure(int statusCode,String message);
}
