package com.pascoapp.wba02_android.services.questions;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@IgnoreExtraProperties
@Data
@ToString(exclude = {"question", "choices", "answer", "explanation", "content"})
@NoArgsConstructor
public class Question {
    @Exclude
    private String key;

    private String type;

    private String number;
    private String question;
    private Map<String, String> choices;
    private String answer;
    private String explanation;

    private String title;
    private String content;

    private String testKey;
    private Map<String, String> submittedAnswers;
}
