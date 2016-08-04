package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.State;

import java.util.ArrayList;
import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 8/4/16.
 */

public class CourseDatabaseManager {

    public static final String COURSES_KEY = "courses";
    public static final DatabaseReference COURSES_REF
            = FirebaseDatabase.getInstance().getReference().child(COURSES_KEY);

    public static void fetchCourse(Store<Action, State> store, String key) {
        store.dispatch(CourseDatabaseActions.requestCourse(key));

        COURSES_REF.child(key)
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Course course = dataSnapshot.getValue(Course.class);
                course.key = dataSnapshot.getKey();
                store.dispatch(CourseDatabaseActions.receiveCourseWithSuccess(course));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(CourseDatabaseActions
                        .receiveCourseWithFailure(databaseError.getMessage()));
            }
        });
    }

    public static void fetchListOfCourses(Store<Action, State> store, Query query) {
        store.dispatch(CourseDatabaseActions.requestListOfCourses(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Course> courses = new ArrayList<>();
                for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);
                    course.key = courseSnapshot.getKey();
                    courses.add(course);
                }
                store.dispatch(CourseDatabaseActions.receiveListOfCoursesWithSuccess(courses));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(CourseDatabaseActions
                        .receiveListOfCoursesWithFailure(databaseError.getMessage()));
            }
        });
    }

}
