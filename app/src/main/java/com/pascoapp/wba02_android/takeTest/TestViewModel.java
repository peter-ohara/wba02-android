package com.pascoapp.wba02_android.takeTest;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.databinding.library.baseAdapters.BR;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.firebasePojos.Course;
import com.pascoapp.wba02_android.firebasePojos.Lecturer;
import com.pascoapp.wba02_android.firebasePojos.Programme;
import com.pascoapp.wba02_android.firebasePojos.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/23/16.
 */

public class TestViewModel extends BaseObservable {

    public String courseCode;
    public String courseName;
    public String testName;
    public String testDuration;
    public Drawable lecturerIcon;
    public String lecturerName;
    public List<String> instructions = new ArrayList<>();

    private Context context;
    private String testKey;

    public TestViewModel(Context context, String testKey) {
        this.context = context;
        this.testKey = testKey;
        fetchTest(testKey);
    }

    @Bindable
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(Course course) {
        courseCode = course.getCode();
        notifyPropertyChanged(BR.courseCode);
    }

    @Bindable
    public String getCourseName() {
        return courseName;
    }

    private void setCourseName(Course course) {
        courseName = course.getName();
        notifyPropertyChanged(BR.courseName);
    }

    @Bindable
    public String getTestName() {
        return testName;
    }

    private void setTestName(Test test) {
        Long year = test.getYear();
        String type;
        if (test.getType().equalsIgnoreCase("endOfSem")) {
            type = "End of Semester Exam";
        } else if (test.getType().equalsIgnoreCase("midSem")) {
            type = "Mid-Semester Exam";
        } else if (test.getType().equalsIgnoreCase("classTest")) {
            type = "Class Test";
        } else if (test.getType().equalsIgnoreCase("assignment")) {
            type = "Assignment";
        } else {
            type = "Unknown";
        }
        testName = year + " " + type;
        notifyPropertyChanged(BR.testName);
    }

    @Bindable
    public String getTestDuration() {
        return testDuration;
    }

    private void setTestDuration(Test test) {
        testDuration = test.getDuration() + "hrs";
        notifyPropertyChanged(BR.testDuration);
    }

    @Bindable
    public Drawable getLecturerIcon() {
        return lecturerIcon;
    }

    public void setLecturerIcon(String name) {
        // generate color based on a key (same key returns the same color), useful for list/grid views
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(name);

        lecturerIcon = TextDrawable.builder()
                .buildRound(
                        name.substring(0, 1).toUpperCase(),
                        color
                );
        notifyPropertyChanged(BR.lecturerIcon);
    }

    @Bindable
    public String getLecturerName() {
        return lecturerName;
    }

    private void setLecturerName(Lecturer lecturer) {
        lecturerName = lecturer.getFirstName() + " " + lecturer.getLastName();
        notifyPropertyChanged(BR.lecturerName);
    }

    public void setInstructions(Test test) {
        instructions.clear();
        instructions.addAll(test.getInstructions());
    }

    private void fetchTest(String testKey) {
        Test.fetchTest(testKey, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Test test = dataSnapshot.getValue(Test.class);
                setTestDuration(test);
                setTestName(test);
                setInstructions(test);
                fetchCourse(test.getCourse());
                fetchLecturer(test.getLecturer());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchCourse(String courseKey) {
        Course.fetchCourse(courseKey, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Course course = dataSnapshot.getValue(Course.class);
                setCourseCode(course);
                setCourseName(course);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchLecturer(String lecturerKey) {
        Lecturer.fetchLecturer(lecturerKey, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);
                setLecturerName(lecturer);
                setLecturerIcon(lecturer.getFirstName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public View.OnClickListener onClickStart() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Starting Test...", Toast.LENGTH_LONG).show();
            }
        };
    }

    public List<Programme> getProgrammes() {
        List<Programme> programmes = new ArrayList<>();
        programmes.add(new Programme("Computer Science [B.Sc.]", "knust"));
        programmes.add(new Programme("Computer Engineering [B.Sc.]", "knust"));
        programmes.add(new Programme("Information Technology [B.Sc.]", "knust"));
        return programmes;
    }

    public List<String> getInstructions() {
        return instructions;
    }
}
