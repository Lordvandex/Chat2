package com.example.chat.chat.java.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {
    public String name;
    public String email;

    public User() {
        //empty
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
// [END blog_user_class]
