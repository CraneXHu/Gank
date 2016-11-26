package me.pkhope.gank.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import me.pkhope.gank.activity.DetailActivity;
import me.pkhope.gank.R;
import me.pkhope.gank.model.GankItem;
import me.pkhope.gank.widget.IndicatorView;

/**
 * Created by pkhope on 16/11/25.
 */

public class GankRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TEXT_ITEM = 0;
    private static final int TYPE_IMAGE_ITEM = 1;
    private static final int TYPE_IMAGES_ITEM = 2;
    private static final int TYPE_FOOTER = 3;


    private Context context;
    private List<GankItem> list;

    public GankRecyclerViewAdapter(Context context, List<GankItem> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(view);
        }

        if (viewType == TYPE_TEXT_ITEM){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_pure_text, parent, false);
            return new PureTextViewHolder(view);
        } else if (viewType == TYPE_IMAGE_ITEM){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_text_image, parent, false);
            return new TextWithImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_text_images, parent, false);
            return new TextWithMutiImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FooterViewHolder){
            return;
        }

        GankItem gankItem = list.get(position);
        ((PureTextViewHolder)holder).strDesc = gankItem.getDesc();
        ((PureTextViewHolder)holder).url = gankItem.getUrl();

        ((PureTextViewHolder)holder).desc.setText(gankItem.getDesc());
        ((PureTextViewHolder)holder).who.setText("via " + gankItem.getWho());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(gankItem.getPublishedAt());
        ((PureTextViewHolder)holder).time.setText(date);

        if (holder instanceof TextWithImageViewHolder){

            int width = context.getResources().getDisplayMetrics().widthPixels;
            Glide.with(context)
                    .load(gankItem.getImages().get(0) + "?imageView2/2/w/" + width)
                    .centerCrop()
                    .dontAnimate()
                    .thumbnail(0.5f)
//                    .override(holder.image.getMeasuredWidth(), holder.image.getMeasuredHeight())
//                    .placeholder(R.drawable.default_image)
                    .into(((TextWithImageViewHolder)holder).preview);
        }

        if (holder instanceof TextWithMutiImageViewHolder){

            final IndicatorView indicatorView = ((TextWithMutiImageViewHolder)holder).indicatorView;
            indicatorView.setCount(gankItem.getImages().size());
            ViewPager viewPager = ((TextWithMutiImageViewHolder)holder).viewPager;
            GankViewPagerAdapter adapter = new GankViewPagerAdapter(context, gankItem.getImages());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    indicatorView.setCurrent(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {

        GankItem gankItem = list.get(position);
        if (gankItem == null){
            return TYPE_FOOTER;
        }
        if (gankItem.getImages() == null){
            return TYPE_TEXT_ITEM;
        } else if (gankItem.getImages().size() == 1){
            return TYPE_IMAGE_ITEM;
        } else {
            return TYPE_IMAGES_ITEM;
        }

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView){
            super(itemView);
        }
    }

    public class PureTextViewHolder extends RecyclerView.ViewHolder {

        String url;
        String strDesc;
        TextView desc;
        TextView who;
        TextView time;

        public PureTextViewHolder(View itemView){
            super(itemView);

            desc = (TextView) itemView.findViewById(R.id.desc);
            who = (TextView) itemView.findViewById(R.id.who);
            time = (TextView) itemView.findViewById(R.id.time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("title", strDesc);
                    intent.putExtra("url", url);
                    context.startActivity(intent);
                }
            });
        }
    }

    public class TextWithImageViewHolder extends PureTextViewHolder {

        ImageView preview;

        public TextWithImageViewHolder(View itemView){
            super(itemView);

            preview = (ImageView) itemView.findViewById(R.id.preview);
        }
    }

    public class TextWithMutiImageViewHolder extends PureTextViewHolder {

        ViewPager viewPager;
        IndicatorView indicatorView;

        public TextWithMutiImageViewHolder(View itemView){
            super(itemView);

            viewPager = (ViewPager) itemView.findViewById(R.id.image_viewpager);
            indicatorView = (IndicatorView) itemView.findViewById(R.id.indicator);
        }
    }
}
