package com.example.myqq.Application;

import android.app.Application;

import com.example.myqq.DAO.MySQLiteOpenHelper;

import io.rong.imlib.RongIMClient;

/**
 *
 *
 * Created by 97210 on 2018/3/21.
 */

public class QQApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //启动应用时对数据库进行初始化
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext());
        mySQLiteOpenHelper.getWritableDatabase();
        RongIMClient.init(this);

    }
}
