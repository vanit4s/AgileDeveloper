package com.dev.sendit.Models;

public class RatingModel {

    String name;
    int rating;
    String content;
    int icon;

    public RatingModel(String name, int rating, String content, int icon) {
        this.name = name;
        this.rating = rating;
        this.content = content;
        this.icon = icon;
    }

    public String getName() { return this.name; }

    public int getRating() { return this.rating; }

    public String getContent() { return this.content; }

    public int getIcon() { return this.icon; }
}
