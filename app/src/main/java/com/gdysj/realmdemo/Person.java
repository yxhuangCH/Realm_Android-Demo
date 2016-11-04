package com.gdysj.realmdemo;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Author: yxhuang
 * Date: 2016/11/4
 * Email: yxhuang@gmail.com
 */

public class Person extends RealmObject {

    private long id;
    private String name;
    private int age;
    private RealmList<Dog> dogs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public RealmList<Dog> getDog() {
        return dogs;
    }

    public void setDog(RealmList<Dog> dogs) {
        this.dogs = dogs;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Person ID：" + id + " name: " + name + " age:" + age);
        return builder.toString();
    }
}
