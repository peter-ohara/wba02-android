package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

/**
 * Handles Programme logic
 */
@IgnoreExtraProperties
public class Programme {

    private static final String PROGRAMMES_KEY = "programmes";
    public String name;
    public String school;
    private School schoolValue;

    public Programme() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Programme(String name, String school) {
        this.name = name;
        this.school = school;
    }

    public static void getProgramme(String key, final ProgrammeEventListener callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(PROGRAMMES_KEY).child(key);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Programme programme = dataSnapshot.getValue(Programme.class);
                School.getSchool(programme.getSchool(), new School.SchoolEventListener() {
                    @Override
                    public void onDataChange(School school) {
                        programme.setSchoolValue(school);
                        callback.onDataChange(programme);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    public void setSchoolValue(School schoolValue) {
        this.schoolValue = schoolValue;
    }

    public interface ProgrammeEventListener {
        void onDataChange(Programme programme);
    }
}
