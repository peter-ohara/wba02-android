package com.pascoapp.wba02_android.services.users;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@IgnoreExtraProperties
@Data
@NoArgsConstructor
public class User {
    private String key;

    private String username;
    private String email;

    private Map<String, Boolean> courseKeys;
    private Map<String, Boolean> questionKeys;
}