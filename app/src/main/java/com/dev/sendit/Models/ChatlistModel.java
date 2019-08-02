package com.dev.sendit.Models;

public class ChatlistModel {

    public String UserID;

    public ChatlistModel(String UserID) {
        this.UserID = UserID;
    }

    public ChatlistModel() {
    }

    public String getID() {
        return UserID;
    }

    public void setID(String UserID) {
        this.UserID = UserID;
    }
}
