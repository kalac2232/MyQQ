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
    private int HeadImageIndex;


    /**
     * 用户的头像
     */
    private Drawable HeadImage;

    /**
     * 登陆用户的Token值 用于融云服务
     */
    private String Token;
    public UserInfo(Context context,int QQNumber, String nickname, int headImageIndex,String Token) {
        this.QQNumber = QQNumber;
        Nickname = nickname;
        HeadImageIndex = headImageIndex;
        HeadImage = findDrawableFromNumber(context, headImageIndex);
        this.Token = Token;
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
        return HeadImageIndex;
    }

    public void setHeadImageIndex(int headImageIndex) {
        HeadImageIndex = headImageIndex;
    }
    public Drawable getHeadImage() {
        return HeadImage;
    }

    public void setHeadImage(Drawable headImage) {
        HeadImage = headImage;
    }
    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
    @Override
    public String toString() {
        return "UserInfo{" +
                "QQNumber=" + QQNumber +
                ", Nickname='" + Nickname + '\'' +
                ", HeadImageIndex=" + HeadImageIndex +
                '}';
    }
    public Drawable findDrawableFromNumber(Context context,int headImageIndex) {
        Drawable drawable = null;
        switch (headImageIndex) {
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
