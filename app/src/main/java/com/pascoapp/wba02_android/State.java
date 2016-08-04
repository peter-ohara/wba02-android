package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.dataFetching.Course;
import com.pascoapp.wba02_android.dataFetching.Lecturer;
import com.pascoapp.wba02_android.dataFetching.Message;
import com.pascoapp.wba02_android.dataFetching.Programme;
import com.pascoapp.wba02_android.dataFetching.Question;
import com.pascoapp.wba02_android.dataFetching.School;
import com.pascoapp.wba02_android.dataFetching.Test;
import com.pascoapp.wba02_android.dataFetching.User;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peter on 7/30/16.
 */

@Value.Immutable
@Gson.TypeAdapters
public abstract class State {

    public static final String NOT_SET = "not_set";

    @Value.Immutable
    @Gson.TypeAdapters
    public static abstract class MainScreen {
        public abstract boolean didInvalidate();
        public abstract Date lastUpdated();
        public abstract List<String> boughtCourses();;
    }

    @Value.Immutable
    @Gson.TypeAdapters
    public static abstract class TestOverviewComponent {
        public abstract boolean didInvalidate();
        public abstract Date lastUpdated();
        public abstract String test();
    }

    public abstract boolean isFetching();
    public abstract String displayErrorMessage();
    public abstract Screens currentScreen();
    public abstract String selectedCourse();
    public abstract MainScreen mainScreen();
    public abstract TestOverviewComponent testOverviewComponent();

    public abstract Map<String, Course> courses();
    public abstract Map<String, Lecturer> lecturers();
    public abstract Map<String, Message> messages();
    public abstract Map<String, Programme> programmes();
    public abstract Map<String, Question> questions();
    public abstract Map<String, School> schools();
    public abstract Map<String, Test> tests();
    public abstract Map<String, User> users();


    static class Default {
        public static State build() {
            ImmutableMainScreen mainScreen = ImmutableMainScreen.builder()
                    .didInvalidate(false)
                    .lastUpdated(new Date())
                    .boughtCourses(new ArrayList<>())
                    .build();
            ImmutableTestOverviewComponent testOverviewComponent
                    = ImmutableTestOverviewComponent.builder()
                    .didInvalidate(false)
                    .lastUpdated(new Date())
                    .test(NOT_SET)
                    .build();
            return ImmutableState.builder()
                    .isFetching(false)
                    .displayErrorMessage(NOT_SET)
                    .currentScreen(Screens.NOT_SET)
                    .selectedCourse(NOT_SET)
                    .mainScreen(mainScreen)
                    .testOverviewComponent(testOverviewComponent)
                    .courses(new HashMap<>())
                    .lecturers(new HashMap<>())
                    .messages(new HashMap<>())
                    .programmes(new HashMap<>())
                    .questions(new HashMap<>())
                    .schools(new HashMap<>())
                    .tests(new HashMap<>())
                    .users(new HashMap<>())
                    .build();
        }
    }

}
