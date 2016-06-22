package com.pascoapp.wba02_android.parseSubClasses;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class School {

    public String name;
    public String shortName;


    public School() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public School(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}
