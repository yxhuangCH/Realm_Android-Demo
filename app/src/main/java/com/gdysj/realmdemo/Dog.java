package com.gdysj.realmdemo;

import io.realm.RealmObject;

/**
 * Author: yxhuang
 * Date: 2016/11/4
 * Email: yxhuang@gmail.com
 */

public class Dog extends RealmObject{

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
