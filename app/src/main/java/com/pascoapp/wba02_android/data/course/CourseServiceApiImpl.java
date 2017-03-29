package com.pascoapp.wba02_android.data.course;

import android.content.Context;

import com.pascoapp.wba02_android.APIUtils;

import java.util.List;

import retrofit2.http.Path;
import rx.Observable;

public class CourseServiceApiImpl implements CourseServiceApi {

    private final Context mContext;

    public CourseServiceApiImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<Course>> getCourses() {
        return APIUtils.getPascoService(mContext, CourseServiceApi.class).getCourses();
    }

    @Override
    public Observable<Course> getCourse(@Path("id") Integer courseId) {
        return APIUtils.getPascoService(mContext, CourseServiceApi.class).getCourse(courseId);
    }
}
