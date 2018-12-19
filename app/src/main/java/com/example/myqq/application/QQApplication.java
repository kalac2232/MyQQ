package com.example.myqq.application;

import android.app.Application;

import com.example.myqq.dao.MySQLiteOpenHelper;


/**
 * Created by 97210 on 2018/3/21.
 */

public class QQApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //启动应用时对数据库进行初始化
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext());
        mySQLiteOpenHelper.getWritableDatabase();
        //RongIMClient.init(this);

    }
}
