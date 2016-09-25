package com.pascoapp.wba02_android.services.programmes;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@IgnoreExtraProperties
@Data
@NoArgsConstructor
public class Programme {
    private String key;

    private String name;

    private String schoolKey;
    private Map<String, Boolean> courseKeys;
}
