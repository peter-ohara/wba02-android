package com.pascoapp.wba02_android.views.main;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.Actions.ActionType;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.tests.Tests;

import java.util.ArrayList;
import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

import static com.pascoapp.wba02_android.services.courses.Courses.COURSES_KEY;

/**
 * Created by peter on 8/5/16.
 */

public class MainViewActions {


    public static Action boughtCoursesRequestInitiated(String userId) {
        return new Action<>(ActionType.BOUGHT_COURSES_REQUEST_INITIATED,
                userId);
    }

    public static Action boughtCoursesRequestSuccessful(List<Course> courses) {
        return new Action<>(ActionType.BOUGHT_COURSES_REQUEST_SUCCESSFUL,
                courses);
    }

    public static Action boughtCoursesRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.BOUGHT_COURSES_REQUEST_FAILED,
                databaseErrorMessage);
    }


    public static void fetchBoughtCourses(Store<Action, State> store, String userId) {
        store.dispatch(boughtCoursesRequestInitiated(userId));

        Query query = FirebaseDatabase.getInstance().getReference().child(COURSES_KEY)
                .limitToFirst(10);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Course> courses = new ArrayList<>();
                for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);
                    course.key = courseSnapshot.getKey();
                    courses.add(course);
                }
                store.dispatch(boughtCoursesRequestSuccessful(courses));
                for (String courseKeys : store.getState().courses().keySet()) {
                    Query testQuery = Tests.TESTS_REF.orderByChild("courseKey").equalTo(courseKeys);
                    Tests.fetchListOfTests(store, testQuery);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(boughtCoursesRequestFailed(databaseError.getMessage()));
            }
        });
    }

}
