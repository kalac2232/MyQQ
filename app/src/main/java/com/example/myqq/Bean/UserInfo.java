package com.example.myqq.Bean;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.myqq.R;

/**
 * Created by 97210 on 2018/3/20.
 */

public class UserInfo {
    /**
     * QQ号码
     */
    private int QQNumber;

    /**
     * 用户的昵称
     */
    private String Nickname;

    /**
     * 用户头像编码
     */
    private int HeadImageNumber;


    /**
     * 用户的头像
     */
    private Drawable HeadImage;
    public UserInfo(Context context,int QQNumber, String nickname, int headImageNumber) {
        this.QQNumber = QQNumber;
        Nickname = nickname;
        HeadImageNumber = headImageNumber;
        HeadImage = findDrawableFromNumber(context, headImageNumber);
    }


    public int getQQNumber() {
        return QQNumber;
    }

    public void setQQNumber(int QQNumber) {
        this.QQNumber = QQNumber;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public int getHeadImageNumber() {
        return HeadImageNumber;
    }

    public void setHeadImageNumber(int headImageNumber) {
        HeadImageNumber = headImageNumber;
    }
    public Drawable getHeadImage() {
        return HeadImage;
    }

    public void setHeadImage(Drawable headImage) {
        HeadImage = headImage;
    }
    @Override
    public String toString() {
        return "UserInfo{" +
                "QQNumber=" + QQNumber +
                ", Nickname='" + Nickname + '\'' +
                ", HeadImageNumber=" + HeadImageNumber +
                '}';
    }
    public Drawable findDrawableFromNumber(Context context,int headImageNumber) {
        Drawable drawable = null;
        switch (headImageNumber) {
            case 0:
                drawable = context.getResources().getDrawable(R.drawable.headimage_0);
                break;
            case 1:
                drawable = context.getResources().getDrawable(R.drawable.headimage_1);
                break;
            case 2:
                drawable = context.getResources().getDrawable(R.drawable.headimage_2);
                break;
            case 3:
                drawable = context.getResources().getDrawable(R.drawable.headimage_3);
                break;
            case 4:
                drawable = context.getResources().getDrawable(R.drawable.headimage_4);
                break;
            case 5:
                drawable = context.getResources().getDrawable(R.drawable.headimage_5);
                break;
            case 6:
                drawable = context.getResources().getDrawable(R.drawable.headimage_6);
                break;
            case 7:
                drawable = context.getResources().getDrawable(R.drawable.headimage_7);
                break;

        }
        return drawable;
    }
}
