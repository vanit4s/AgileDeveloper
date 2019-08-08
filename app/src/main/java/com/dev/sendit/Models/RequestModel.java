package com.dev.sendit.Models;

public class RequestModel {

    private String Sender;
    private String Receiver;
    private boolean Request;

    public RequestModel(String sender, String receiver, boolean request) {
        Sender = sender;
        Receiver = receiver;
        Request = request;
    }

    public RequestModel() {
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public boolean isRequest() {
        return Request;
    }

    public void setRequest(boolean request) {
        Request = request;
    }
}
