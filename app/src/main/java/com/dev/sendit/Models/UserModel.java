package com.dev.sendit.Models;

public class UserModel {

    private String id;
    private String first_name;
    private String last_name;
    private String image_url;

    public UserModel(String id, String first_name, String last_name, String image_url) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.image_url = image_url;
    }

    public UserModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
