package com.example.agroapp.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    static OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(15000, TimeUnit.MILLISECONDS)
            .writeTimeout(15000, TimeUnit.MILLISECONDS)
            .readTimeout(15000, TimeUnit.MILLISECONDS)
            .build();
    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()

                    .baseUrl(WebservicesConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //  Log.i("BAseUrl", Config.BASE_URL);
        return retrofit;
    }

    public static Retrofit getClient1() {
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder()

                    .baseUrl(WebservicesConstant.BASE_URL1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //  Log.i("BAseUrl1", Config.BASE_URL1);
        return retrofit1;
    }
}
