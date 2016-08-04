package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Handles Programme logic
 */
@IgnoreExtraProperties
public class Programme {

    public String key;
    public String name;
    public String schoolKey;

    public Programme() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Programme(String name, String school) {
        this.name = name;
        this.schoolKey = school;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSchoolKey() {
        return schoolKey;
    }

    @Override
    public String toString() {
        return "Programme{" +
                "name='" + name + '\'' +
                '}';
    }
}
