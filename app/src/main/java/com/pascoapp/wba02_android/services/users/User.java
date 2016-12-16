package com.pascoapp.wba02_android.services.users;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@IgnoreExtraProperties
@Data
@NoArgsConstructor
public class User {
    @Exclude
    private String key;

    private String displayName;
    private String uid;
    private String email;
    private String photoUrl;

    private String providerId;

    private Map<String, Boolean> courseKeys;
    private Map<String, Boolean> questionKeys;
}