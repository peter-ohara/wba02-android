package com.pascoapp.wba02_android.services.schools;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@IgnoreExtraProperties
@Data
@NoArgsConstructor
public class School {
    @Exclude
    private String key;

    private String name;
    private String shortName;

    private Map<String, Boolean> programmeKeys;
}
