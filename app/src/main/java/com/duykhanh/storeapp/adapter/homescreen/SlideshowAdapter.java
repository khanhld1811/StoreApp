package com.duykhanh.storeapp.adapter.homescreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.duykhanh.storeapp.R;

import com.smarteist.autoimageslider.SliderViewAdapter;

public class SlideshowAdapter extends SliderViewAdapter<SlideshowAdapter.SliderAdapterVH> {

    private Context context;

    public SlideshowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SlideshowAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slide_show_category, null);
        return new SlideshowAdapter.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SlideshowAdapter.SliderAdapterVH viewHolder, int position) {
        switch (position){
            case 0:
                Glide.with(viewHolder.itemView)
                        .load("https://cdn.bannersnack.com/files/cb1b3c0d15f8e1628a083f4f81692170")
                        .into(viewHolder.imageViewBackground);
                break;
            case 1:
                Glide.with(viewHolder.itemView)
                        .load("https://cdn.bannersnack.com/files/4258ef1e8f0bc6580224dddf81692634")
                        .into(viewHolder.imageViewBackground);
                break;
            case 2:
                Glide.with(viewHolder.itemView)
                        .load("https://cdn.bannersnack.com/files/4e97fd544f2d285cd7661a6f81693004")
                        .into(viewHolder.imageViewBackground);
                break;
            default:
                Glide.with(viewHolder.itemView)
                        .load("https://cdn.bannersnack.com/files/8076fe6b3246c897f262a42f81693069")
                        .into(viewHolder.imageViewBackground);
                break;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder{
        View itemView;
        ImageView imageViewBackground;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider_category);
            this.itemView = itemView;
        }
    }

}
