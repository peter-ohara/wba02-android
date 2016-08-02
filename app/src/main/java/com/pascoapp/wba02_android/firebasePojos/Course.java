package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Handles Course logic
 */
@IgnoreExtraProperties
public class Course {

    public static final String COURSES_KEY = "courses";
    public String key;
    public String code;
    public String name;
    public Long semester;
    public Long level;
    public String programme;
    public String school;

    public Course() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Course(String code, String name, Long semester, Long level, String programme, String school) {
        this.code = code;
        this.name = name;
        this.semester = semester;
        this.level = level;
        this.programme = programme;
        this.school = school;
    }

    public String getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Long getSemester() {
        return semester;
    }

    public Long getLevel() {
        return level;
    }

    public String getProgramme() {
        return programme;
    }

    public String getSchool() {
        return school;
    }

    public static void fetchCourse(String courseKey, ValueEventListener valueEventListener) {
        DatabaseReference testRef = FirebaseDatabase.getInstance().getReference()
                .child(COURSES_KEY).child(courseKey);
        testRef.addValueEventListener(valueEventListener);
    }

    public static Observable<List<Course>> fetchCourses() {
        return Observable.create(new Observable.OnSubscribe<List<Course>>() {
            @Override
            public void call(final Subscriber<? super List<Course>> subscriber) {
                DatabaseReference testRef = FirebaseDatabase.getInstance().getReference()
                        .child(COURSES_KEY);
                testRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildren() == null) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(new NullPointerException());
                            }
                        } else {
                            if (!subscriber.isUnsubscribed()) {

                                List<Course> courses = new ArrayList<Course>();

                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    Course course = snapshot.getValue(Course.class);
                                    courses.add(course);
                                }

                                subscriber.onNext(courses);
                                subscriber.onCompleted();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }


    @Override
    public String toString() {
        return "Course{" +
                "key='" + key + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", semester=" + semester +
                ", level=" + level +
                ", programme='" + programme + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
