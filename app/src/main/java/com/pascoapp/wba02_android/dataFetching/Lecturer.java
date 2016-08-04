package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.FirebaseItem;

/**
 * Handles Lecturer logic
 */
@IgnoreExtraProperties
public class Lecturer  implements FirebaseItem {

    public String key;
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

    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
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
