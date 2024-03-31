package com.example.restaurantapplication;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private String name;
    private String imageUrl;
    private String description;
    private String phone;
    private String rating;
    private String location;

    public Restaurant(String name, String imageUrl, String description, String phone, String rating, String location) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.phone = phone;
        this.rating = rating;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    public String getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }
}
