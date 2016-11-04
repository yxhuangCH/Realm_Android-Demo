package com.gdysj.realmdemo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Author: yxhuang
 * Date: 2016/11/4
 * Email: yxhuang@gmail.com
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 默认文件的位置 Context.getFilesDir() ，名字是 "default.realm"
        // 当然也可以对build 进行自定义配置
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }
}
