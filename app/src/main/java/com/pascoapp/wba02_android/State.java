package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.router.Route;
import com.pascoapp.wba02_android.router.Router;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.lecturers.Lecturer;
import com.pascoapp.wba02_android.services.messages.Message;
import com.pascoapp.wba02_android.services.programmes.Programme;
import com.pascoapp.wba02_android.services.questions.Question;
import com.pascoapp.wba02_android.services.schools.School;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.services.users.User;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peter on 7/30/16.
 */

@Value.Immutable
@Gson.TypeAdapters
public abstract class State {

    public static final String NOT_SET = "not_set";

    public abstract Route currentRoute();
    public abstract Map<String, Object> currentResolvedData();

    public abstract boolean isFetching();
    public abstract String displayErrorMessage();

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
            return ImmutableState.builder()
                    .isFetching(false)
                    .displayErrorMessage(NOT_SET)
                    .currentRoute(new Route(Router.Screens.MAIN_SCREEN))
                    .currentResolvedData(new HashMap<>())
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
