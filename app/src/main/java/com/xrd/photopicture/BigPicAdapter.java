package com.xrd.photopicture;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/9/28.
 */

public class BigPicAdapter extends PagerAdapter {
    private ArrayList<String> list=new ArrayList<>();
    private ArrayList<ImageView> viewList=new ArrayList<>();
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = viewList.get(position);
        String s = list.get(position);
        Glide.with(container.getContext()).load(s).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }

    public void setPic(ArrayList<String> pic, List<ImageView> imageViews) {
        list.clear();
        viewList.clear();
        viewList.addAll(imageViews);
        list.addAll(pic);
        notifyDataSetChanged();
    }
}
