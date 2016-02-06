package com.pascoapp.wba02_android.parseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Handles Question logic
 */
@ParseClassName("Question")
public class Question extends ParseObject {

    public Question() {
    }

    public String getQuestion() {
        return getString("question");
    }
    public void setQuestion(String question) {
        put("question", question);
    }

    public String getType() {
        // TODO: Enforce questionType is one of these [essay|mcq|fillIn|score]
        return getString("type");
    }
    public void setType(String type) {
        // TODO: Enforce questionType is one of these [essay|mcq|fillIn|score]
        put("type", type);
    }

    public Number getQuestionNumber() {
        return getNumber("number");
    }
    public void setQuestionNumber(Number number) {
        put("number", number);
    }

    public List<String> getChoices() {
        return getList("choices");
    }
    public void setChoices(List<String> choices) {
        put("choices", choices);
    }

    public String getAnswer() {
        return getString("answer");
    }
    public void setAnswer(String answer) {
        put("answer", answer);
    }

    public Test getTest() {
        return (Test) getParseObject("test");
    }
    public void setTest(Test test) {
        put("test", test);
    }

    public static ParseQuery<Question> getQuery() {
        ParseQuery<Question> query = ParseQuery.getQuery(Question.class);
        query.include("test");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        return query;
    }

    @Override
    public String toString() {
        return getType() + "; "
                + getTest() + "; "
                + getQuestion() + "; "
                + getChoices() + "; "
                + getAnswer();
    }
}
