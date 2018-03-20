package com.example.myqq.Bean;

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
}
