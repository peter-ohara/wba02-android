package com.pascoapp.wba02_android.services.lecturers;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@IgnoreExtraProperties
@Data
@NoArgsConstructor
public class Lecturer {
    @Exclude
    private String key;

    private String firstName;
    private String middleInitials;
    private String middleNames;
    private String lastName;

    private Map<String, Boolean> testKeys;
}
