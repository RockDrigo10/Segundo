package com.example.segundoauqui.rocksaucechallenge.view.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.example.segundoauqui.rocksaucechallenge.R;
import com.example.segundoauqui.rocksaucechallenge.injection.mainactivity.DaggerMainActivityComponent;
import com.example.segundoauqui.rocksaucechallenge.model.Child;
import com.example.segundoauqui.rocksaucechallenge.model.Example;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    @Inject
    MainActivityPresenter presenter;
    ArrayList<Example>  example = new ArrayList<>();
    ArrayList<Child> data = new ArrayList<>();
    ArrayList<CategoriesAdapter> categoriesAdapters = new ArrayList<>();
    LinearLayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    RecyclerView recycler;
    SwipeRefreshLayout activityMainSwipeRefreshLayout;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpDagger();
        presenter = new MainActivityPresenter();
        presenter.attachView(this);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemAnimator = new DefaultItemAnimator();
        recycler = (RecyclerView) findViewById(R.id.rvDays);
        activityMainSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        searchView = (SearchView) findViewById(R.id.searchView);
        recycler.setLayoutManager(layoutManager);
        recycler.setItemAnimator(itemAnimator);
        recycler.setHasFixedSize(true);
        recycler.setItemViewCacheSize(20);
        recycler.setDrawingCacheEnabled(true);
        recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        activityMainSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activityMainSwipeRefreshLayout.setRefreshing(true);
                if(searchView.getQuery().toString()== "")
                    presenter.restCall("funny/");
                else
                    presenter.restCall(searchView.getQuery().toString());
            }
        });
        presenter.restCall("funny/");

    }

    @Override
    public void sendInfo(ArrayList<Child> value) {
        CategoriesAdapter firstAdapter = new CategoriesAdapter(value);
        recycler.setAdapter(firstAdapter);
        firstAdapter.notifyDataSetChanged();

        this.data= value;
        activityMainSwipeRefreshLayout.setRefreshing(false);
    }

    public void setUpDagger(){
        DaggerMainActivityComponent.create().inject(this);
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getAllPostList(List<Child> getAllPostList) {
        CategoriesAdapter firstAdapter = new CategoriesAdapter((ArrayList<Child>) getAllPostList);
        recycler.setAdapter(firstAdapter);
        firstAdapter.notifyDataSetChanged();

        this.data= (ArrayList<Child>) getAllPostList;
        activityMainSwipeRefreshLayout.setRefreshing(false);
    }

}
