package com.dev.sendit.Models;

public class UserModel {

    private String Facebook_ID;
    private String First_Name;
    private String Last_Name;
    private String Name;
    private String Email;
    private String Image_URL;
    private String Status;

    public UserModel(String Facebook_ID, String First_Name, String Last_Name, String Name, String Email, String Image_URL, String Status) {
        this.Facebook_ID = Facebook_ID;
        this.First_Name = First_Name;
        this.Last_Name = Last_Name;
        this.Name = Name;
        this.Email = Email;
        this.Image_URL = Image_URL;
        this.Status = Status;
    }

    public UserModel() {

    }


    public String getFacebook_ID() {
        return Facebook_ID;
    }

    public void setFacebook_ID(String facebook_ID) {
        this.Facebook_ID = facebook_ID;
    }

    public String getFirst_name() {
        return First_Name;
    }

    public void setFirst_name(String first_name) {
        this.First_Name = first_name;
    }

    public String getLast_name() {
        return Last_Name;
    }

    public void setLast_name(String last_name) {
        this.Last_Name = last_name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getImage_url() {
        return Image_URL;
    }

    public void setImage_url(String image_url) {
        this.Image_URL = image_url;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
}
