package com.pascoapp.wba02_android;

import android.content.Context;

import com.pascoapp.wba02_android.data.course.CourseRepositories;
import com.pascoapp.wba02_android.data.course.CourseRepository;
import com.pascoapp.wba02_android.data.course.CourseServiceApiImpl;
import com.pascoapp.wba02_android.data.test.TestRepositories;
import com.pascoapp.wba02_android.data.test.TestRepository;
import com.pascoapp.wba02_android.data.test.TestServiceApiImpl;
import com.pascoapp.wba02_android.data.user.UserRepositories;
import com.pascoapp.wba02_android.data.user.UserRepository;
import com.pascoapp.wba02_android.data.user.UserServiceApiImpl;

public class Injection {
    public static TestRepository provideTestsRepository(Context context) {
        return TestRepositories.getInMemoryRepoInstance(new TestServiceApiImpl(context));
    }

    public static CourseRepository provideCourseRepository(Context context) {
        return CourseRepositories.getInMemoryRepoInstance(new CourseServiceApiImpl(context));
    }

    public static UserRepository provideUserRepository(Context context) {
        return UserRepositories.getInMemoryRepoInstance(new UserServiceApiImpl(context));
    }
}
