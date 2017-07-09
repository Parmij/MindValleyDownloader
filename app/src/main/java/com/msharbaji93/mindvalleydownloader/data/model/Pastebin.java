package com.msharbaji93.mindvalleydownloader.data.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MHDSHA on 09/07/2017.
 */

public class Pastebin {

    private String id;
    private Date created_at;
    private int width;
    private int height;
    private String color;
    private int likes;
    private boolean liked_by_user;
    private User user;
    private ArrayList<User> current_user_collections;
    private Url urls;
    private ArrayList<Category> categories;
    private Link links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLiked_by_user() {
        return liked_by_user;
    }

    public void setLiked_by_user(boolean liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getCurrent_user_collections() {
        return current_user_collections;
    }

    public void setCurrent_user_collections(ArrayList<User> current_user_collections) {
        this.current_user_collections = current_user_collections;
    }

    public Url getUrls() {
        return urls;
    }

    public void setUrls(Url urls) {
        this.urls = urls;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }
}
