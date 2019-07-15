package com.dev.sendit.Models;

public class ListModel {
    String title;
    String location;
    String description;
    int icon;

    public ListModel(String title, String location, String description, int icon) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLocation() {
        return this.location;
    }

    public String getDescription() {
        return this.description;
    }

    public int getIcon() {
        return this.icon;
    }
}
