package com.pascoapp.wba02_android;

import android.content.Context;

import com.pascoapp.wba02_android.signInScreen.CheckCurrentUser;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by peter on 1/31/17.
 */
public class RetrofitClient {

    private static final String CACHE_CONTROL = "Cache-Control";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context, String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(provideOkHttpClient(context))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient provideOkHttpClient (Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor( provideHttpLoggingInterceptor() )
                .addInterceptor( provideOfflineCacheInterceptor() )
//                .addNetworkInterceptor( provideCacheInterceptor() )
                .addNetworkInterceptor( provideTokenAuthenticationInterceptor(context) )
                .cache( provideCache() )
                .build();
    }

    private static Cache provideCache () {
        Cache cache = null;
        try {
            cache = new Cache( new File( PascoApp.getInstance().getCacheDir(), "http-cache" ),
                    10 * 1024 * 1024 ); // 10 MB
        } catch (Exception e) {
            Timber.e( e, "Could not create Cache!" );
        }
        return cache;
    }

    private static Interceptor provideTokenAuthenticationInterceptor (Context context) {
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Request original = chain.request();



                Request request = original.newBuilder()
                        .header("Authorization", "Token token=" + CheckCurrentUser.getAuthToken(context))
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        };
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor () {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log (String message)
                    {
                        Timber.d( message );
                    }
                } );
        httpLoggingInterceptor.setLevel( BuildConfig.DEBUG ? HEADERS : NONE );
        return httpLoggingInterceptor;
    }

    public static Interceptor provideCacheInterceptor () {
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Response response = chain.proceed( chain.request() );

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge( 2, TimeUnit.MINUTES )
                        .build();

                return response.newBuilder()
                        .header( CACHE_CONTROL, cacheControl.toString() )
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor () {
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Request request = chain.request();

                if ( !PascoApp.hasNetwork() ) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale( 7, TimeUnit.DAYS )
                            .build();

                    request = request.newBuilder()
                            .cacheControl( cacheControl )
                            .build();
                }

                return chain.proceed( request );
            }
        };
    }
}
