package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Handles Programme logic
 */
@IgnoreExtraProperties
public class Programme {

    public String name;
    public String school;

    public Programme() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Programme(String name, String school) {
        this.name = name;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    @Override
    public String toString() {
        return "Programme{" +
                "name='" + name + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
