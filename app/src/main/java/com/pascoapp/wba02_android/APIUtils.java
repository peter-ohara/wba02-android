package com.pascoapp.wba02_android;

/**
 * Created by peter on 1/31/17.
 */
public class APIUtils {
    public static final String BASE_URL = "https://nameless-forest-97776.herokuapp.com/android_v1/";

    public static <T> T getPascoService(final Class<T> clazz) {
        T service = RetrofitClient.getClient(BASE_URL).create(clazz);
        return service;
    }
}
