package com.msharbaji93.mindvalleydownloader.data.model;

/**
 * Created by MHDSHA on 09/07/2017.
 */

public class User {

    private String id;
    private String username;
    private String name;
    private ProfileImage profile_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileImage getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(ProfileImage profile_image) {
        this.profile_image = profile_image;
    }
}
