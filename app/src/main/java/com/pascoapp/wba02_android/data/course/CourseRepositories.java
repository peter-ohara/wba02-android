package com.pascoapp.wba02_android.data.course;

import android.support.annotation.NonNull;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class CourseRepositories {

    public CourseRepositories() {
        // no instance
    }

    private static CourseRepository sRepository = null;

    public synchronized static CourseRepository getInMemoryRepoInstance(
            @NonNull CourseServiceApi courseServiceApi) {

        checkNotNull(courseServiceApi);
        if (null == sRepository) {
            sRepository = new InMemoryCourseRepository(courseServiceApi);
        }
        return sRepository;
    }
}
