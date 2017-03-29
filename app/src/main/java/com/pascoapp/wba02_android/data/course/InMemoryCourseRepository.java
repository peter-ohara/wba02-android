package com.pascoapp.wba02_android.data.course;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.collect.ImmutableList;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

class InMemoryCourseRepository implements CourseRepository {

    private final CourseServiceApi mCourseServiceApi;

    /**
     * This method has reduced visibility for testing and is only visible to courses in the same
     * package.
     */
    @VisibleForTesting
    private List<Course> mCachedCourses;

    InMemoryCourseRepository(@NonNull CourseServiceApi courseServiceApi) {
        mCourseServiceApi = checkNotNull(courseServiceApi);
    }

    @Override
    public Observable<Course> getCourse(@NonNull Integer courseId) {
        checkNotNull(courseId);
        return mCourseServiceApi.getCourse(courseId);
    }

    @Override
    public Observable<List<Course>> getCourses() {

        if (mCachedCourses == null) {
            return mCourseServiceApi.getCourses()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .concatMap(courses -> {
                        mCachedCourses = ImmutableList.copyOf(courses);
                        return Observable.just(mCachedCourses);
                    });
        } else {
            return Observable.just(mCachedCourses);
        }
    }

    @Override
    public void refreshData() {
        mCachedCourses = null;
    }
}
