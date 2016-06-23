package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

/**
 * Handles Question logic
 */
@IgnoreExtraProperties
public class Question {

    public String type;

    public String question;
    public Map<String, String> choices;

    public String answer;
    public String explanation;

    public String number;
    public String section;
    public Long semester;
    public Long quarter;

    public String test;
    public String lecturer;
    public String course;
    public String programme;
    public String school;


    public Question() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Question(String type, String question, Map<String, String> choices, String answer, String explanation, String number, String section, Long semester, Long quarter, String test, String lecturer, String course, String programme, String school) {
        this.type = type;
        this.question = question;
        this.choices = choices;
        this.answer = answer;
        this.explanation = explanation;
        this.number = number;
        this.section = section;
        this.semester = semester;
        this.quarter = quarter;
        this.test = test;
        this.lecturer = lecturer;
        this.course = course;
        this.programme = programme;
        this.school = school;
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

    public String getTest() {
        return test;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getCourse() {
        return course;
    }

    public String getProgramme() {
        return programme;
    }

    public String getSchool() {
        return school;
    }

    @Override
    public String toString() {
        return "Question{" +
                "type='" + type + '\'' +
                ", question='" + question + '\'' +
                ", choices=" + choices +
                ", answer='" + answer + '\'' +
                ", explanation='" + explanation + '\'' +
                ", number='" + number + '\'' +
                ", section='" + section + '\'' +
                ", semester=" + semester +
                ", quarter=" + quarter +
                ", test='" + test + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", course='" + course + '\'' +
                ", programme='" + programme + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
