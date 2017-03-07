package org.easyarch.netcat.http;

import io.netty.handler.codec.http.cookie.Cookie;

/**
 * Description :
 * Created by xingtianyu on 17-2-27
 * 下午6:03
 * description:
 */

public class HttpCookie implements Cookie{
    @Override
    public String name() {
        return null;
    }

    @Override
    public String value() {
        return null;
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public boolean wrap() {
        return false;
    }

    @Override
    public void setWrap(boolean wrap) {

    }

    @Override
    public String domain() {
        return null;
    }

    @Override
    public void setDomain(String domain) {

    }

    @Override
    public String path() {
        return null;
    }

    @Override
    public void setPath(String path) {

    }

    @Override
    public long maxAge() {
        return 0;
    }

    @Override
    public void setMaxAge(long maxAge) {

    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public void setSecure(boolean secure) {

    }

    @Override
    public boolean isHttpOnly() {
        return false;
    }

    @Override
    public void setHttpOnly(boolean httpOnly) {

    }

    @Override
    public int compareTo(Cookie o) {
        return 0;
    }
}
