package com.example.myqq.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by 97210 on 2018/3/22.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG="MySQLiteOpenHelper";
    public MySQLiteOpenHelper(Context context) {
        super(context, "QQDataBase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建user表
        sqLiteDatabase.execSQL("create table user (QQNumber integer,Nickname varchar(20),HeadImageNumber integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
