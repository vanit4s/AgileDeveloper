package com.dev.sendit.Models;

public class ChatModel {

    private String Sender;
    private String Receiver;
    private String Message;

    public ChatModel(String Sender, String Receiver, String Message) {
        this.Sender = Sender;
        this.Receiver = Receiver;
        this.Message = Message;
    }

    public ChatModel() {

    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
