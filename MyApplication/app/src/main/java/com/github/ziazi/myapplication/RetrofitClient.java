package com.github.ziazi.myapplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance= null;
    private BoredApi myApi;
    private RetrofitClient() {
        this.myApi= new Retrofit.Builder()
                .baseUrl(BoredApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BoredApi.class);
        instance= this;
    }
    public static RetrofitClient getClient(){
        if (instance == null){
            instance= new RetrofitClient();
        }
        return instance;
    }
    public  BoredApi getApi(){
        return this.myApi;
    }



}
