package com.example.segundoauqui.rocksaucechallenge.view.mainactivity;

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

    @Override
    public void attachView(MainActivityContract.View view) {
        this.view = view;

    }

    @Override
    public void detachView() {

    }


    @Override
    public void restCall(String category) {
        final retrofit2.Call<Example> getAllPost = NetwrokCall.getExampleCall(category);
        getAllPost.enqueue(new retrofit2.Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.body() != null) {
                    List<Child> user = response.body().getData().getChildren();
                    try {
                        view.getAllPostList(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

    }
}

