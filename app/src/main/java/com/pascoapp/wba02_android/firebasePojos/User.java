package com.pascoapp.wba02_android.firebasePojos;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Handles User logic
 */
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    public String school;
    public String programme;
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

    public static String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
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
                ", school='" + school + '\'' +
                ", programme='" + programme + '\'' +
                ", level=" + level +
                ", semester=" + semester +
                ", quarter=" + quarter +
                ", voucher='" + voucher + '\'' +
                '}';
    }
}