package me.pkhope.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.gank.App;
import me.pkhope.gank.GankRetrofit;
import me.pkhope.gank.R;
import me.pkhope.gank.adapter.GankRecyclerViewAdapter;
import me.pkhope.gank.model.Gank;
import me.pkhope.gank.model.GankItem;
import me.pkhope.gank.utils.NetworkUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pkhope on 16/11/25.
 */

public class GankFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private int currentPage = 1;
    private String type = "Android";
    private GankRecyclerViewAdapter adapter;
    private List<GankItem> gankItemList = new ArrayList<>();

    private boolean loaded = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            type = getArguments().getString("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        App.getDB().save(gankItemList);
        for (int i = 0; i < gankItemList.size() - 1; i++){
            App.getDB().save(gankItemList.get(i));
        }
    }

    private void initView(View view){
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.gank_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
                R.color.refresh_progress_2, R.color.refresh_progress_1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtil.isNetworkConnected(getContext())){
                    refreshData();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.gank_rv);

        adapter = new GankRecyclerViewAdapter(getActivity(),gankItemList);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                boolean isBottom = lastVisibleItem == adapter.getItemCount() - 1;

                if (isBottom && loaded) {

                    loadMore();
                }
            }
        });

        if (!NetworkUtil.isNetworkConnected(getContext())){
            List<GankItem> result = App.getDB().query(new QueryBuilder(GankItem.class)
                    .whereEquals("type", type));
            gankItemList.addAll(result);
            adapter.notifyDataSetChanged();
        } else {
            App.getDB().delete(GankItem.class, new WhereBuilder()
                    .equals("type", type));
            refreshData();
        }

    }

    private void refreshData(){

        swipeRefreshLayout.setRefreshing(true);

        currentPage = 1;
        gankItemList.clear();
        GankRetrofit.getInstance().gankApi().getGankData(type,currentPage++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Gank>() {
                    @Override
                    public void onCompleted() {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Gank gank) {
                        gankItemList.addAll(gank.getResults());
                        gankItemList.add(null);
                        adapter.notifyDataSetChanged();
                        loaded = true;
                    }
                });
    }

    private void loadMore(){

        loaded = false;

        GankRetrofit.getInstance().gankApi().getGankData(type,currentPage++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Gank>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Gank gank) {
                        gankItemList.remove(gankItemList.size() - 1);
                        gankItemList.addAll(gank.getResults());
                        gankItemList.add(null);
                        adapter.notifyDataSetChanged();
                        loaded = true;
                    }
                });
    }
}
