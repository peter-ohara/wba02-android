package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

/**
 * Handles Lecturer logic
 */
@IgnoreExtraProperties
public class Lecturer {

    public static final String LECTURERS_KEY = "lecturers";
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

    public static void getLecturer(String key, final Lecturer.LecturerEventListener callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(LECTURERS_KEY).child(key);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);
                callback.onDataChange(lecturer);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
    
    public interface LecturerEventListener {
        void onDataChange(Lecturer lecturer);
    }
}
