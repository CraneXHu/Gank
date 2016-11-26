package me.pkhope.gank.activity;

import android.app.SearchManager;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.gank.R;
import me.pkhope.gank.adapter.GankFragmentPagerAdapter;
import me.pkhope.gank.fragment.GankFragment;
import me.pkhope.gank.model.Gank;
import me.pkhope.gank.model.GankItem;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SearchView searchView;

    private List<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        initViewPager();

    }

    private void initViewPager(){

        titleList = new ArrayList<>();
        titleList.add("Android");
        titleList.add("iOS");
        titleList.add("前端");

        List<Fragment> fragmentList = new ArrayList<>();
        GankFragment gankFragment = new GankFragment();
        Bundle args = new Bundle();
        args.putString("type", "Android");
        gankFragment.setArguments(args);
        fragmentList.add(gankFragment);
        gankFragment = new GankFragment();
        args = new Bundle();
        args.putString("type", "iOS");
        gankFragment.setArguments(args);
        fragmentList.add(gankFragment);
        gankFragment = new GankFragment();
        args = new Bundle();
        args.putString("type", "前端");
        gankFragment.setArguments(args);
        fragmentList.add(gankFragment);

        GankFragmentPagerAdapter gankFragmentPagerAdapter = new GankFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        viewPager.setAdapter(gankFragmentPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Bundle appDataBundle = new Bundle();
//                appDataBundle.putString("type", titleList.get(viewPager.getCurrentItem()));
//                startSearch(query, true, appDataBundle, false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

}
