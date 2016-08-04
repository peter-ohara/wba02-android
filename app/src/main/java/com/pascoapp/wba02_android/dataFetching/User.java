package com.pascoapp.wba02_android.dataFetching;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.IgnoreExtraProperties;
import com.pascoapp.wba02_android.FirebaseItem;

/**
 * Handles User logic
 */
@IgnoreExtraProperties
public class User implements FirebaseItem {

    public String key;

    public String username;
    public String email;

    public String schoolKey;
    public String programmeKey;
    public Long level;

    public Long semester;
    public Long quarter;

    public String voucher;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public static String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getSchoolKey() {
        return schoolKey;
    }

    public void setSchoolKey(String schoolKey) {
        this.schoolKey = schoolKey;
    }

    public String getProgrammeKey() {
        return programmeKey;
    }

    public void setProgrammeKey(String programmeKey) {
        this.programmeKey = programmeKey;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getSemester() {
        return semester;
    }

    public void setSemester(Long semester) {
        this.semester = semester;
    }

    public Long getQuarter() {
        return quarter;
    }

    public void setQuarter(Long quarter) {
        this.quarter = quarter;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", level=" + level +
                ", semester=" + semester +
                ", quarter=" + quarter +
                ", voucher='" + voucher + '\'' +
                '}';
    }
}