package org.easyarch.netcat;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xingtianyu on 17-3-12
 * 下午5:31
 * description:
 */

public class User {

    private String username;

    private int age;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("User equals");

        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (age != user.age) return false;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + age;
        return result;
    }

    public static void main(String[] args) {
        User user1 = new User("xingtian",22);
        User user2 = new User("liuyuanlin",21);
        User user3 = new User("xingtianyu",23);
        User user4 = new User("xingtianyu",23);
        User user5 = new User("xingtian",23);

        Map<User,String> map = new LinkedHashMap<>();
        map.put(user1,"xxx");
        map.put(user2,"lll");
        map.put(user3,"yyy");
        System.out.println(map.get(user4));
        System.out.println(map.get(user5));
    }
}
