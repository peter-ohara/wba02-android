package com.pascoapp.wba02_android;

import android.content.Context;

public class APIUtils {

    public static <T> T getPascoService(Context context, final Class<T> clazz) {
        return RetrofitClient.getClient(context, BuildConfig.BASE_URL).create(clazz);
    }
}
