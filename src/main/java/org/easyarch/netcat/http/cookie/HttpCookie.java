package org.easyarch.netcat.http.cookie;

/**
 * Created by xingtianyu on 17-3-16
 * 上午1:13
 * description:
 */

public interface HttpCookie {

    public void setMaxAge(int expiry);

    public void setValue(String value);

    public void setName(String name);

    public int getMaxAge();

    public String getValue();

    public String getName();

}
