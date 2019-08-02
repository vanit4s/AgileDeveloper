package com.dev.sendit.Models;

public class ListModel {
    String Author;
    String Name;
    String Type;
    String Description;
    String Location;

    public ListModel(String Author, String Name, String Type, String Description, String Location) {
        this.Author = Author;
        this.Name = Name;
        this.Type = Type;
        this.Description = Description;
        this.Location = Location;
    }

    public ListModel() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }
}
