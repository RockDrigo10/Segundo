package com.example.segundoauqui.rocksaucechallenge.view.mainactivity;

import com.example.segundoauqui.rocksaucechallenge.model.Example;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by segundoauqui on 11/2/17.
 */

public class NetwrokCall {

    public static final String BASE_URL = "http://www.reddit.com/r/";
    public static final String PATH = ".json";

    public static  Retrofit restCall(String url) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // create a custom client to add the interceptor
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL + url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }


    public static Call<Example> getExampleCall(String category) {
        Retrofit retrofit = restCall(category);
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        return retrofitService.getRetrofitServiceData();
    }

    public interface RetrofitService {

        @GET(PATH)
        Call<Example> getRetrofitServiceData();
    }

}