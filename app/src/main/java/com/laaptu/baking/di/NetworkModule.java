package com.laaptu.baking.di;

import com.laaptu.baking.data.api.ApiConstants;
import com.laaptu.baking.data.api.RecipesApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    static Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl(ApiConstants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();
    }

    @Provides
    static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.BODY);

        return new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(ApiConstants.TIMEOUT_CONNECTION_IN_SECS, TimeUnit.SECONDS)
            .readTimeout(ApiConstants.TIMEOUT_READ_IN_SECS, TimeUnit.SECONDS)
            .build();
    }

    @Provides
    @Singleton
    static RecipesApiService getRecipesService(Retrofit retrofit) {
        return retrofit.create(RecipesApiService.class);
    }
}
