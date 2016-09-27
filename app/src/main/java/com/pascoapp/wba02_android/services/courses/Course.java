package com.pascoapp.wba02_android.services.courses;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@IgnoreExtraProperties
@Data
@NoArgsConstructor
public class Course {
    @Exclude
    private String key;

    private String code;
    private Long level;
    private String name;
    private Long semester;

    private Map<String, Boolean> programmeKeys;
    private Map<String, Boolean> userKeys;
    private Map<String, Boolean> testKeys;
}
