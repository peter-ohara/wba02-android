package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.router.Route;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.lecturers.Lecturers;
import com.pascoapp.wba02_android.services.messages.Messages;
import com.pascoapp.wba02_android.services.programmes.Programmes;
import com.pascoapp.wba02_android.services.questions.Questions;
import com.pascoapp.wba02_android.services.schools.Schools;
import com.pascoapp.wba02_android.services.tests.Tests;
import com.pascoapp.wba02_android.services.users.Users;
import com.pascoapp.wba02_android.main.MainComponentReducers;

import java.util.Map;

import trikita.jedux.Action;
import trikita.jedux.Store;


/**
 * Created by peter on 7/30/16.
 */

public class RootReducer implements Store.Reducer<Action, State> {

    @Override
    public State reduce(Action action, State oldState) {
        State newState = oldState;

        newState = showScreenReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = setRouteResolutions(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        // MainComponent
        newState = MainComponentReducers.boughtCoursesRequestInitiated(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = MainComponentReducers.boughtCoursesRequestSuccessful(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = MainComponentReducers.boughtCoursesRequestFailed(action, oldState);
        if (!newState.equals(oldState)) { return newState; }


        // Courses
        newState = Courses.courseRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Courses.courseRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Courses.courseRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Courses.courseListRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Courses.courseListRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Courses.courseListRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }


        // Tests
        newState = Tests.testRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Tests.testRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Tests.testRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Tests.testListRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Tests.testListRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Tests.testListRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }


        // Lecturers
        newState = Lecturers.lecturerRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Lecturers.lecturerRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Lecturers.lecturerRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Lecturers.lecturerListRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Lecturers.lecturerListRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Lecturers.lecturerListRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }


        // Messages
        newState = Messages.messageRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Messages.messageRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Messages.messageRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Messages.messageListRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Messages.messageListRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Messages.messageListRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        // Programmes
        newState = Programmes.programmeRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Programmes.programmeRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Programmes.programmeRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Programmes.programmeListRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Programmes.programmeListRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Programmes.programmeListRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        // Questions
        newState = Questions.questionRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Questions.questionRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Questions.questionRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Questions.questionListRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Questions.questionListRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Questions.questionListRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        // Schools
        newState = Schools.schoolRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Schools.schoolRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Schools.schoolRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Schools.schoolListRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Schools.schoolListRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Schools.schoolListRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }


        // Users
        newState = Users.userRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Users.userRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Users.userRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Users.userListRequestInitiatedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Users.userListRequestSuccessfulReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        newState = Users.userListRequestFailedReducer(action, oldState);
        if (!newState.equals(oldState)) { return newState; }

        return newState;
    }

    private State showScreenReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case SHOW_SCREEN:
                return ImmutableState.builder()
                        .from(oldState)
                        .currentRoute((Route) action.value)
                        .build();
            default:
                return oldState;
        }
    }

    private State setRouteResolutions(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case SET_ROUTE_RESOLUTIONS:
                return ImmutableState.builder()
                        .from(oldState)
                        .currentResolvedData((Map<String, Object>) action.value)
                        .build();
            default:
                return oldState;
        }
    }
}
