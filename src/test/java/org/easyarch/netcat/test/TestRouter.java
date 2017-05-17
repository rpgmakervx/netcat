package org.easyarch.netcat.test;

import org.easyarch.netpet.web.mvc.action.ActionType;
import org.easyarch.netpet.web.mvc.router.Router;

/**
 * Created by xingtianyu on 17-5-17
 * 上午11:30
 * description:
 */

public class TestRouter {

    public static void main(String[] args) {
        Router router = new Router("index?uesrname=0", ActionType.HANDLER);
        System.out.println(router);
    }
}
