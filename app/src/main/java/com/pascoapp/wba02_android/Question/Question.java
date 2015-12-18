package com.pascoapp.wba02_android.Question;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.Test.Test;

import java.util.List;

/**
 * ParseObject Subclass for Question. Handles Question logic
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
        // TODO: Enforce type is one of these [essay|mcq|fillIn]
        return getString("type");
    }
    public void setType(String type) {
        // TODO: Enforce type is one of these [essay|mcq|fillIn]
        put("type", type);
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

    public ParseObject getTest() {
        return getParseObject("test");
    }
    public void setTest(Test test) {
        put("test", test);
    }

    public static ParseQuery<Question> getQuery() {
        return ParseQuery.getQuery(Question.class);
    }
}
