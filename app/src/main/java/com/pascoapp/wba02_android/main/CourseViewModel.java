package com.pascoapp.wba02_android.main;

import android.graphics.drawable.Drawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pascoapp.wba02_android.dataFetching.Course;

/**
 * Created by peter on 7/27/16.
 */

public class CourseViewModel {

    public String name;
    public String code;
    public Drawable icon;

    public CourseViewModel(Course course) {
        setName(course.getName());
        setCode(course.getCode());
        setIcon(course.getCode());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(String name) {
        // generate color based on a nodeKey (same nodeKey returns the same color), useful for list/grid views
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(name);

        icon = TextDrawable.builder()
                .buildRound(
                        name.toUpperCase(),
                        color
                );
    }

    @Override
    public String toString() {
        return "CourseViewModel{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", icon=" + icon +
                '}';
    }
}
