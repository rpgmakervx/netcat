package org.easyarch.netcat.http.cookie;

/**
 * Created by xingtianyu on 17-3-16
 * 上午1:13
 * description:
 */

public class HttpCookie {

    private long maxAge;

    private String name;

    private String value;

    private String domain;

    private String path;

    private boolean httpOnly;

    public void setMaxAge(long maxAge){
        this.maxAge = maxAge;
    }

    public void setValue(String value){
        this.value = value;
    }

    public void setName(String name){
        this.name = name;
    }

    public long maxAge(){
        return this.maxAge;
    }

    public String value(){
        return this.value;
    }

    public String name(){
        return this.name;
    }

    public String domain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String path() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }
}
