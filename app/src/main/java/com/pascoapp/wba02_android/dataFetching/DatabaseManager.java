package com.pascoapp.wba02_android.dataFetching;

/**
 * Created by peter on 8/4/16.
 */

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.FirebaseItem;
import com.pascoapp.wba02_android.ImmutableMainScreen;
import com.pascoapp.wba02_android.ImmutableState;
import com.pascoapp.wba02_android.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import trikita.jedux.Action;
import trikita.jedux.Store;

import static com.pascoapp.wba02_android.dataFetching.DatabaseActions.*;
import static com.pascoapp.wba02_android.Actions.ActionType;

/**
 * Created by peter on 8/4/16.
 */

public class DatabaseManager<T extends FirebaseItem>  {

    private Class<T> tClass;

    public String nodeKey;
    public DatabaseReference nodeRef;

    public DatabaseManager(Class<T> tClass) {
        this.tClass = tClass;
        // TODO: Comment this shady string-typing you did here
        nodeKey = tClass.getSimpleName().toLowerCase() + "s";
        System.out.println("DatabaseManager: " + tClass.getSimpleName());
        nodeRef = FirebaseDatabase.getInstance().getReference().child(nodeKey);
    }

    public void fetch(Store<Action, State> store, String key) {
        store.dispatch(requestInitiated(key));

        nodeRef.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        T item = dataSnapshot.getValue(tClass);
                        item.setKey(dataSnapshot.getKey());
                        store.dispatch(requestSuccessful(item));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(requestFailed(databaseError.getMessage()));
                    }
                });
    }

    public void fetchList(Store<Action, State> store, Query query) {
        store.dispatch(listRequestInitiated(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<T> items = new ArrayList<>();
                for (DataSnapshot childSnapShot: dataSnapshot.getChildren()) {
                    T item = childSnapShot.getValue(tClass);
                    item.setKey(childSnapShot.getKey());
                    items.add(item);
                }
                store.dispatch(listRequestSuccessful(items));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(listRequestFailed(databaseError.getMessage()));
            }
        });
    }



    public State requestInitiatedReducer(Action action, State oldState) {
        ActionType type = (ActionType) action.type;
        switch(type) {
            case REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public State requestSuccessfulReducer(Action action, State oldState) {
        ActionType type = (ActionType) action.type;
        switch(type) {
            case REQUEST_SUCCESSFUL:
                State newState = null;
                switch(tClass.getName()) {
                    case "Course":
                        Course course = (Course) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Course Data
                                .putCourses(course.getKey(), course)
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "Lecturer":
                        Lecturer lecturer = (Lecturer) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Lecturer Data
                                .putLecturers(lecturer.getKey(), lecturer)
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "Message":
                        Message message = (Message) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Message Data
                                .putMessages(message.getKey(), message)
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "Programme":
                        Programme programme = (Programme) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Programme Data
                                .putProgrammes(programme.getKey(), programme)
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "Question":
                        Question question = (Question) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Question Data
                                .putQuestions(question.getKey(), question)
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "School":
                        School school = (School) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new School Data
                                .putSchools(school.getKey(), school)
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "Test":
                        Test test = (Test) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Test Data
                                .putTests(test.getKey(), test)
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "User":
                        User user = (User) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new User Data
                                .putUsers(user.getKey(), user)
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                }
                return newState;
            default:
                return oldState;
        }
    }

    public State requestFailedReducer(Action action, State oldState) {
        ActionType type = (ActionType) action.type;
        switch(type) {
            case REQUEST_FAILED:
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


    public State listRequestInitiatedReducer(Action action, State oldState) {
        ActionType type = (ActionType) action.type;
        switch(type) {
            case LIST_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public State listRequestSuccessfulReducer(Action action, State oldState) {
        ActionType type = (ActionType) action.type;
        switch(type) {
            case LIST_REQUEST_SUCCESSFUL:
                State newState = null;
                switch(tClass.getSimpleName()) {
                    case "Course":
                        List<Course> courses = (List<Course>) action.value;
                        List<String> courseKeys = Stream.of(courses)
                                .map(course -> course.getKey())
                                .collect(Collectors.toList());
                        State.MainScreen mainScreen = ImmutableMainScreen.builder()
                                .from(oldState.mainScreen())
                                .addAllBoughtCourses(courseKeys)
                                .build();
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Course Data
                                .putAllCourses(convertToMap(courses))
                                .mainScreen(mainScreen)
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "Lecturer":
                        List<Lecturer> lecturers = (List<Lecturer>) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Course Data
                                .putAllLecturers(convertToMap(lecturers))
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "Message":
                        List<Message> messages = (List<Message>) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Course Data
                                .putAllMessages(convertToMap(messages))
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "Programme":
                        List<Programme> programmes = (List<Programme>) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Course Data
                                .putAllProgrammes(convertToMap(programmes))
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "Question":
                        List<Question> questions = (List<Question>) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Course Data
                                .putAllQuestions(convertToMap(questions))
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "School":
                        List<School> schools = (List<School>) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Course Data
                                .putAllSchools(convertToMap(schools))
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "Test":
                        List<Test> tests = (List<Test>) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Course Data
                                .putAllTests(convertToMap(tests))
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                    case "User":
                        List<User> users = (List<User>) action.value;
                        newState = ImmutableState.builder()
                                .from(oldState)
                                // Merge new Course Data
                                .putAllUsers(convertToMap(users))
                                // Set isFetching Flag to false
                                .isFetching(false)
                                .build();
                        break;
                }
                return newState;
            default:
                return oldState;
        }
    }

    public State listRequestFailedReducer(Action action, State oldState) {
        ActionType type = (ActionType) action.type;
        switch(type) {
            case LIST_REQUEST_FAILED:
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

    public static <T extends FirebaseItem> Map<String, T> convertToMap(List<T> items) {
        Map<String, T> map = new HashMap<>();
        for (T item : items) {
            map.put(item.getKey(), item);
        }
        return map;
    }

}
