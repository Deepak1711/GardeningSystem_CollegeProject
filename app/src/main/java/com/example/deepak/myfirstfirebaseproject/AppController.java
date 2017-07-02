package com.example.deepak.myfirstfirebaseproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 2/4/17.
 */


public class AppController {
    private static final String END_POINT = "http://192.168.43.142/collegeProject/";
    private static AppController appController;
    private Retrofit retrofit;

    public static synchronized AppController getInstance() {
        if (appController == null) {
            appController = new AppController();
        }
        return appController;
    }

    public Retrofit getRetrofitBuilder() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
