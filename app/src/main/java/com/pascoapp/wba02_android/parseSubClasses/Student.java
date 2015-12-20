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

    public ParseObject getSchool() {
        return getParseObject("school");
    }
    public void setSchool(School school) {
        put("school", school);
    }

    public ParseObject getProgramme() {
        return getParseObject("programme");
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

    public int getVoucher() {
        return getInt("voucher");
    }
    public void setVoucher(String voucher) {
        put("voucher", voucher);
    }

    public static Student getCurrentUser() {
        return (Student) ParseUser.getCurrentUser();
    }

    // TODO: Fix this
//    public static ParseQuery<Student> getQuery() {
//        ParseQuery<Student> query = ParseQuery.getQuery(Student.class);
//        return query;
//    }
}
