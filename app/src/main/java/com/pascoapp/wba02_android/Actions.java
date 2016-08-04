package com.pascoapp.wba02_android;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.dataFetching.Course;
import com.pascoapp.wba02_android.dataFetching.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 7/31/16.
 */

public class Actions {

    public enum ActionType {
        // Main screen actions
        SHOW_SCREEN,
        SELECT_COURSE,

        // Course Network Actions
        INVALIDATE_COURSE,
        REQUEST_COURSES,
        RECEIVE_COURSES_WITH_SUCCEESS,
        RECEIVE_COURSES_WITH_FAILURE,

        // Test Network Actions
        REQUEST_TESTS,
        RECEIVE_TESTS_WITH_SUCCEESS,
        RECEIVE_TESTS_WITH_FAILURE
    }

    public static Action showScreen(Screens screen) {
        return new Action<>(ActionType.SHOW_SCREEN,
                screen);
    }

    public static Action selectCourse(String testKey) {
        return new Action<>(ActionType.SELECT_COURSE,
                testKey);
    }

    public static Action requestCourses(String key) {
        return new Action<>(ActionType.REQUEST_COURSES,
                key);
    }

    public static Action receiveCoursesWithSuccess(List<Course> courses) {
        return new Action<>(ActionType.RECEIVE_COURSES_WITH_SUCCEESS,
                courses);
    }

    public static Action receiveCoursesWithFailure(String databaseError) {
        return new Action<>(ActionType.RECEIVE_COURSES_WITH_FAILURE,
                databaseError);
    }

    public static Action requestTests(String courseKey) {
        return new Action<>(ActionType.REQUEST_TESTS,
                courseKey);
    }

    public static Action receiveTestsWithSuccess(List<Test> tests) {
        return new Action<>(ActionType.RECEIVE_TESTS_WITH_SUCCEESS,
                tests);
    }

    public static Action receiveTestsWithFailure(String databaseError) {
        return new Action<>(ActionType.RECEIVE_TESTS_WITH_SUCCEESS,
                databaseError);
    }

    public Function<Store<Action, State>, String> fetchBoughtCourses() {
        return (store) -> {
            store.dispatch(Actions.requestCourses(""));

            DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference().child("courses");
            Query coursesQuery = coursesRef.limitToFirst(9);
            coursesQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Course> courses = new ArrayList<>();
                    for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                        Course course = courseSnapshot.getValue(Course.class);
                        course.key = courseSnapshot.getKey();
                        courses.add(course);
                    }
                    store.dispatch(Actions.receiveCoursesWithSuccess(courses));
                    for (Course course : courses) {
                        fetchTestsForCourse(course.getKey());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    store.dispatch(Actions.receiveCoursesWithFailure(databaseError.getMessage()));
                }
            });

            return "Hello";
        };
    }

    private Function<Store<Action, State>, String> fetchTestsForCourse(String courseKey) {
        return (store) -> {
            store.dispatch(Actions.requestTests(courseKey));
            DatabaseReference testsRef = FirebaseDatabase.getInstance().getReference().child("tests");
            // TODO: Uncomment the line below then fix the bug that ensues
            // Query testQuery = testsRef.orderByChild("courseKey").equalTo(courseKey);
            Query testQuery = testsRef.orderByChild("courseKey");
            testQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Test> tests = new ArrayList<>();
                    for (DataSnapshot testSnapshot: dataSnapshot.getChildren()) {
                        Test test = testSnapshot.getValue(Test.class);
                        test.key = testSnapshot.getKey();
                        tests.add(test);
                    }
                    store.dispatch(Actions.receiveTestsWithSuccess(tests));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    store.dispatch(Actions.receiveTestsWithFailure(databaseError.getMessage()));
                }
            });
            return "Hello";
        };
    }
}
