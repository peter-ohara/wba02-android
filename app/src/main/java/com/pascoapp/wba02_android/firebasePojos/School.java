package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

@IgnoreExtraProperties
public class School {

    public static final String SCHOOLS_KEY = "schools";
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

    public static void getSchool(String key, final SchoolEventListener callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(SCHOOLS_KEY).child(key);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                School school = dataSnapshot.getValue(School.class);
                callback.onDataChange(school);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }

    public interface SchoolEventListener {
        void onDataChange(School school);
    }
}
