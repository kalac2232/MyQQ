package com.example.myqq.dao.UserDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.myqq.bean.UserInfo;
import com.example.myqq.dao.MySQLiteOpenHelper;

/**
 * Created by 97210 on 2018/3/22.
 */

public class UserDAOUtil {

    private MySQLiteOpenHelper mySQLiteOpenHelper = null;
    Context context;
    public UserDAOUtil(Context context) {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
        this.context = context;
    }

    /**
     * 插入用户信息
     * @param user 要插入的用户bean
     * @return 插入的结果 ture 成功
     */
    public boolean insertUser(UserInfo user) {
        SQLiteDatabase database = mySQLiteOpenHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("QQNumber",user.getQQNumber());
        contentValues.put("Nickname",user.getNickname());
        contentValues.put("HeadImageNumber",user.getHeadImageNumber());
        contentValues.put("Token",user.getToken());
        long result = database.insert("user", null, contentValues);
        database.close();
        if (result == -1) {
            return false;
        }
        return true;
    }

    /**
     * 查询数据库中的用户信息
     * @return 查询出的bean对象，为空为无用户或错误
     */
    public UserInfo querytUser() {
        UserInfo user = null;
        SQLiteDatabase database = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.query("user", null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int QQNumber = cursor.getInt(0);
                String Nickname = cursor.getString(1);
                int headImageNumber = cursor.getInt(2);
                String token = cursor.getString(3);
                user = new UserInfo(context,QQNumber,Nickname,headImageNumber,token);
            }
        }
        database.close();
        return user;
    }

    /**
     * 删除数据库中的用户信息
     * @return 成功与否
     */
    public boolean delectUser() {
        SQLiteDatabase database = mySQLiteOpenHelper.getReadableDatabase();
        int result = database.delete("user", null, null);
        database.close();
        if (result == -1) {
            return false;
        }
        return true;
    }
}
