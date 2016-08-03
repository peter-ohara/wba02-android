package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

/**
 * Handles Question logic
 */
@IgnoreExtraProperties
public class Question {

    private static final String QUESTIONS_KEY = "questions";

    public String key;

    public String type;

    public String question;
    public Map<String, String> choices;

    public String answer;
    public String explanation;

    public String number;
    public String section;
    public Long semester;
    public Long quarter;

    public String testKey;
    public String lecturerKey;
    public String courseKey;
    public String programmeKey;
    public String schoolKey;

    public Question() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Question(String type, String question, Map<String, String> choices, String answer, String explanation, String number, String section, Long semester, Long quarter, String testKey, String lecturerKey, String courseKey, String programmeKey, String schoolKey) {
        this.type = type;
        this.question = question;
        this.choices = choices;
        this.answer = answer;
        this.explanation = explanation;
        this.number = number;
        this.section = section;
        this.semester = semester;
        this.quarter = quarter;
        this.testKey = testKey;
        this.lecturerKey = lecturerKey;
        this.courseKey = courseKey;
        this.programmeKey = programmeKey;
        this.schoolKey = schoolKey;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public Map<String, String> getChoices() {
        return choices;
    }

    public String getAnswer() {
        return answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getNumber() {
        return number;
    }

    public String getSection() {
        return section;
    }

    public Long getSemester() {
        return semester;
    }

    public Long getQuarter() {
        return quarter;
    }

    public String getTestKey() {
        return testKey;
    }

    public String getLecturerKey() {
        return lecturerKey;
    }

    public String getCourseKey() {
        return courseKey;
    }

    public String getProgrammeKey() {
        return programmeKey;
    }

    public String getSchoolKey() {
        return schoolKey;
    }

    @Override
    public String toString() {
        return "Question{" +
                "key='" + key + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
