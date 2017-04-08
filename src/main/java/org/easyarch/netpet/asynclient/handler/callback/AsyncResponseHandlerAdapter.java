package org.easyarch.netpet.asynclient.handler.callback;

import org.easyarch.netpet.asynclient.http.response.AsyncHttpResponse;

/**
 * Created by xingtianyu on 17-4-8
 * 下午3:35
 * description:
 */

abstract public class AsyncResponseHandlerAdapter implements AsyncResponseHandler {

    @Override
    public void onFinally(AsyncHttpResponse response) {

    }
}
