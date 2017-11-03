package com.example.segundoauqui.rocksaucechallenge.view.mainactivity;

import android.content.Context;

import com.example.segundoauqui.rocksaucechallenge.model.Child;
import com.example.segundoauqui.rocksaucechallenge.model.Example;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;




/**
 * Created by segundoauqui on 11/1/17.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {




    private MainActivityContract.View view;
    private Context context;

    @Override
    public void attachView(MainActivityContract.View view) {
        this.view = view;

    }

    @Override
    public void detachView() {

    }


    @Override
    public void restCall() {
        final retrofit2.Call<Example> getAllPost = NetwrokCall.getExampleCall();
        getAllPost.enqueue(new retrofit2.Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                List<Child> user = response.body().getData().getChildren();
                try{
                    view.getAllPostList(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

    }
}

