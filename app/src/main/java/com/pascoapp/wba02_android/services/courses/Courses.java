package com.pascoapp.wba02_android.services.courses;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by peter on 8/4/16.
 */

public class Courses {

    public static final String COURSES_KEY = "courses";
    public static final DatabaseReference COURSES_REF
            = FirebaseDatabase.getInstance().getReference().child(COURSES_KEY);


    public static Observable<Course> fetchCourse(String key) {
        return Observable.create(subscriber -> {
            COURSES_REF.child(key)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                String errorMessage = "Item doesn't exist in the database";
                                subscriber.onError(new FirebaseException(errorMessage));
                                return;
                            }

                            Course course = dataSnapshot.getValue(Course.class);
                            course.setKey(dataSnapshot.getKey());
                            subscriber.onNext(course);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            subscriber.onError(new FirebaseException(databaseError.getMessage()));

                        }
                    });
        });
    }

    public static Observable<List<Course>> fetchListOfCourses(Query query) {
        return Observable.create(subscriber -> {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        String errorMessage = "Item doesn't exist in the database";
                        subscriber.onError(new FirebaseException(errorMessage));
                        return;
                    }

                    List<Course> courses = new ArrayList<>();
                    for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                        Course course = courseSnapshot.getValue(Course.class);
                        course.setKey(courseSnapshot.getKey());
                        courses.add(course);
                    }
                    subscriber.onNext(courses);
                    subscriber.onCompleted();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    subscriber.onError(new FirebaseException(databaseError.getMessage()));
                }
            });
        });
    }

}
