package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.firebasePojos.Course;
import com.pascoapp.wba02_android.firebasePojos.Lecturer;
import com.pascoapp.wba02_android.firebasePojos.Message;
import com.pascoapp.wba02_android.firebasePojos.Programme;
import com.pascoapp.wba02_android.firebasePojos.Question;
import com.pascoapp.wba02_android.firebasePojos.School;
import com.pascoapp.wba02_android.firebasePojos.Test;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

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

    @Value.Immutable
    @Gson.TypeAdapters
    public static abstract class MainScreen {
        public abstract boolean isFetching();
        public abstract boolean didInvalidate();
        public abstract Date lastUpdated();
        public abstract Map<String, List<String>> items();;
    }

    public abstract Screens currentScreen();
    public abstract String selectedCourse();
    public abstract MainScreen mainScreen();

    public abstract Map<String, Course> courses();
    public abstract Map<String, Lecturer> lecturers();
    public abstract Map<String, Message> messages();
    public abstract Map<String, Programme> programmes();
    public abstract Map<String, Question> questions();
    public abstract Map<String, School> schools();
    public abstract Map<String, Test> tests();


    static class Default {
        public static State build() {
            ImmutableMainScreen mainScreen1 = ImmutableMainScreen.builder()
                    .isFetching(false)
                    .didInvalidate(false)
                    .lastUpdated(new Date())
                    .items(new HashMap<>())
                    .build();
            return ImmutableState.builder()
                    .currentScreen(Screens.NO_SCREEN_YET)
                    .selectedCourse("none_selected")
                    .mainScreen(mainScreen1)
                    .courses(new HashMap<>())
                    .lecturers(new HashMap<>())
                    .messages(new HashMap<>())
                    .programmes(new HashMap<>())
                    .questions(new HashMap<>())
                    .schools(new HashMap<>())
                    .tests(new HashMap<>())
                    .build();
        }
    }

}
