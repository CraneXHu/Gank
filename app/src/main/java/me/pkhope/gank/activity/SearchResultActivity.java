package me.pkhope.gank.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.gank.GankRetrofit;
import me.pkhope.gank.R;
import me.pkhope.gank.adapter.GankRecyclerViewAdapter;
import me.pkhope.gank.model.Gank;
import me.pkhope.gank.model.GankItem;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pkhope on 16/11/26.
 */

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GankRecyclerViewAdapter adapter;

    private String query = null;
    private String type = null;
    private List<GankItem> resultList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        //must be after setSupportActionBar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initView();
        handleIntent(getIntent());
    }

    private void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.search_rv);
        adapter = new GankRecyclerViewAdapter(this, resultList);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void handleIntent(Intent intent){

        query = intent.getStringExtra("query");
        type = intent.getStringExtra("type");
        GankRetrofit.getInstance().gankApi().getSearchData(query, type, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Gank>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Gank gank) {
                        resultList.addAll(gank.getResults());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
