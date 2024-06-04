package com.epds.javafx_login.api.chat;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ChatApiClient {

    private static final String BASE_URL = "http://192.168.201.112:5000/";
    private static volatile Retrofit retrofit = null;

    public static Retrofit getAPIClient() {
        if (retrofit == null) {
            synchronized (ChatApiClient.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                            .build();
                }
            }
        }

        return retrofit;
    }
}
