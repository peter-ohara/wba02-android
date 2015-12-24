package com.pascoapp.wba02_android.parseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Handles Student logic
 */
@ParseClassName("_User")
public class Student extends ParseUser {

    public Student() {
    }

    public String getFirstName() {
        return getString("firstName");
    }
    public void setFirstName(String firstName) {
        put("firstName", firstName);
    }

    public String getLastName() {
        return getString("lastName");
    }
    public void setLastName(String lastName) {
        put("lastName", lastName);
    }

    public String getFullName() {
        return getString("firstName") + " " + getString("lastName");
    }
    public void setName(String firstName, String lastName) {
        put("firstName", firstName);
        put("lastName", lastName);
    }

    public School getSchool() {
        return (School) getParseObject("school");
    }
    public void setSchool(School school) {
        put("school", school);
    }

    public Programme getProgramme() {
        return (Programme) getParseObject("programme");
    }
    public void setProgramme(Programme programme) {
        put("programme", programme);
    }

    public Integer getLevel() {
        return getInt("level");
    }
    public void setLevel(Integer level) {
        put("level", level);
    }

    public Integer getSemester() {
        return getInt("semester");
    }
    public void setSemester(Integer semester) {
        put("semester", semester);
    }

    public String getVoucher() {
        return getString("voucher");
    }
    public void setVoucher(String voucher) {
        put("voucher", voucher);
    }

    public static Student getCurrentUser() {
        return (Student) ParseUser.getCurrentUser();
    }

    // TODO: Check if it's possible to return a student straight away
    public static ParseQuery<ParseUser> getUserQuery() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.include("school");
        query.include("programme");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        return query;
    }


    @Override
    public String toString() {
        return getFullName() + "; "
                + getSchool() + ": "
                + getProgramme() + ": "
                + getLevel() + ": "
                + getSemester() + ": "
                + getVoucher();
    }
}
