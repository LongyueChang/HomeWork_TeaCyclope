package com.example.administrator.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/13.
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<ImageView> imageData;

    public MyPagerAdapter(List<ImageView> imageData) {
        this.imageData = imageData;
    }

    @Override
    public int getCount() {
        return imageData!=null?imageData.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        ImageView ret=imageData.get(position);
        container.addView(ret);
        return ret;
    }

    @Override
    public void destroyItem(final ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView(imageData.get(position));
    }
}
