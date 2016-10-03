package com.pascoapp.wba02_android.services.messages;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@IgnoreExtraProperties
@Data
@NoArgsConstructor
public class Message {
    @Exclude
    private String key;

    private String title;
    private String content;
    private Long date;

    private String userKey;
}