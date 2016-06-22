package com.pascoapp.wba02_android.parseSubClasses;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Handles Lecturer logic
 */
@IgnoreExtraProperties
public class Lecturer {

    public String firstName;
    public String middleInitials;
    public String middleNames;
    public String lastName;

    public Lecturer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Lecturer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleInitials() {
        return middleInitials;
    }

    public String getMiddleNames() {
        return middleNames;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
