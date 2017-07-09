package com.msharbaji93.mindvalleydownloader.data.model;

/**
 * Created by MHDSHA on 09/07/2017.
 */

public class Category {

    private  int id;
    private String title;
    private int photo_count;
    private CategoryLinks links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhoto_count() {
        return photo_count;
    }

    public void setPhoto_count(int photo_count) {
        this.photo_count = photo_count;
    }

    public CategoryLinks getLinks() {
        return links;
    }

    public void setLinks(CategoryLinks links) {
        this.links = links;
    }
}
