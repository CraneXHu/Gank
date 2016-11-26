package me.pkhope.gank.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pkhope on 16/11/25.
 */

public class GankViewPagerAdapter extends PagerAdapter{

    private Context context;
    private List<String> imageList = new ArrayList<>();

    public GankViewPagerAdapter(Context context, List<String> images){
        this.context = context;
        this.imageList = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(context);
        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        int width = context.getResources().getDisplayMetrics().widthPixels;
        Glide.with(context)
                .load(imageList.get(position) + "?imageView2/2/w/" + width)
                .into(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

}
