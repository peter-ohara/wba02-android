package com.pascoapp.wba02_android.services.schools;

import com.google.firebase.database.IgnoreExtraProperties;
import com.pascoapp.wba02_android.services.FirebaseItem;

@IgnoreExtraProperties
public class School implements FirebaseItem {

    public String key;

    public String name;
    public String shortName;


    public School() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public School(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
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
