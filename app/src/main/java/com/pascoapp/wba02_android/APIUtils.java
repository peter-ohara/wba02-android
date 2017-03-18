package com.pascoapp.wba02_android;

/**
 * Created by peter on 1/31/17.
 */
public class APIUtils {
//    public static final String BASE_URL = "https://wba02-server.herokuapp.com/android_v1/";
//    public static final String BASE_URL = "http://192.168.43.208:3000/android_v1/";
    public static final String BASE_URL = "http://10.0.2.2:3000/android_v1/";

    public static <T> T getPascoService(final Class<T> clazz) {
        T service = RetrofitClient.getClient(BASE_URL).create(clazz);
        return service;
    }
}
