package com.pascoapp.wba02_android.services.testOwnerships;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@IgnoreExtraProperties
@Data
@NoArgsConstructor
public class TestOwnership {
    @Exclude
    private String key;

    private String userId;
    private Integer testId;

    private Long transactionDate;
    private Long expiryDate;
}
