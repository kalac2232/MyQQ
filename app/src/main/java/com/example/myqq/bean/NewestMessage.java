package com.example.myqq.bean;

/*
 * Created by Kalac on 2018/12/16
 */

public class NewestMessage {
    private String contactName;
    private int headiconId;
    private String lastMessage;
    private String lastMessageTime;

    public NewestMessage(int headiconId,String contactName,String lastMessage,String lastMessageTime) {
        this.headiconId = headiconId;
        this.lastMessage = lastMessage;
        this.contactName = contactName;
        this.lastMessageTime = lastMessageTime;
    }
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getHeadiconId() {
        return headiconId;
    }

    public void setHeadicon(int headiconId) {
        this.headiconId = headiconId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
