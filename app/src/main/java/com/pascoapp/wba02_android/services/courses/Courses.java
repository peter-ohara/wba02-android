package com.pascoapp.wba02_android.services.courses;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.Actions;
import com.pascoapp.wba02_android.ImmutableState;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.Helpers;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.ArrayList;
import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 8/4/16.
 */

public class Courses {

    public static final String COURSES_KEY = "courses";
    public static final DatabaseReference COURSES_REF
            = FirebaseDatabase.getInstance().getReference().child(COURSES_KEY);

    public static Promise fetchCourse(Store<Action, State> store, String key) {
        store.dispatch(CourseActions.courseRequestInitiated(key));
        Deferred deferred = new DeferredObject();
        COURSES_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            String errorMessage = "Item doesn't exist in the database";
                            store.dispatch(CourseActions
                                    .courseRequestFailed(errorMessage));
                            deferred.reject(errorMessage);
                            return;
                        }

                        Course course = dataSnapshot.getValue(Course.class);
                        course.key = dataSnapshot.getKey();
                        store.dispatch(CourseActions.courseRequestSucceeded(course));
                        deferred.resolve(course);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(CourseActions
                                .courseRequestFailed(databaseError.getMessage()));
                        deferred.reject(databaseError.getMessage());
                    }
                });
        return deferred.promise();
    }

    public static Promise fetchListOfCourses(Store<Action, State> store, Query query) {
        store.dispatch(CourseActions.listCourseRequestInitiated(query));
        Deferred deferred = new DeferredObject();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    String errorMessage = "Item doesn't exist in the database";
                    store.dispatch(CourseActions
                            .courseRequestFailed(errorMessage));
                    deferred.reject(errorMessage);
                    return;
                }

                List<Course> courses = new ArrayList<>();
                for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);
                    course.key = courseSnapshot.getKey();
                    courses.add(course);
                }
                store.dispatch(CourseActions.listCourseRequestSucceeded(courses));
                deferred.resolve(courses);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(CourseActions
                        .courseRequestFailed(databaseError.getMessage()));
                deferred.reject(databaseError.getMessage());
            }
        });
        return deferred.promise();
    }

    public static State courseRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case COURSE_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State courseRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case COURSE_REQUEST_SUCCESSFUL:
                Course course = (Course) action.value;
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Course Data
                        .putCourses(course.getKey(), course)
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State courseRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case COURSE_REQUEST_FAILED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(false)
                        .displayErrorMessage((String) action.value)
                        .build();
            default:
                return oldState;
        }
    }


    public static State courseListRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_COURSE_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State courseListRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_COURSE_REQUEST_SUCCESSFUL:
                List<Course> courses = (List<Course>) action.value;
                List<String> courseKeys = Stream.of(courses)
                        .map(course -> course.getKey())
                        .collect(Collectors.toList());
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Course Data
                        .putAllCourses(Helpers.convertToMap(courses))
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State courseListRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_COURSE_REQUEST_FAILED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(false)
                        .displayErrorMessage((String) action.value)
                        .build();
            default:
                return oldState;
        }
    }

}
