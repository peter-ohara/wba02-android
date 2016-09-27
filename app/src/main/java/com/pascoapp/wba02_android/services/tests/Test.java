package com.pascoapp.wba02_android.services.tests;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@IgnoreExtraProperties
@Data
@ToString(exclude={"instructions"})
@NoArgsConstructor
public class Test {
    @Exclude
    private String key;

    private String type;
    private Long year;
    private Long duration;
    private List<String> instructions;

    private String courseKey;
    private Map<String, Boolean> lecturerKeys;
    private Map<String, Double> questionKeys;

}
