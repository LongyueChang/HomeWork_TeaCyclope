package com.example.administrator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/13.
 */
public class MyFragmentAdapter extends FragmentStatePagerAdapter {

    List<Fragment> datas;

    public MyFragmentAdapter(FragmentManager fm, List<Fragment> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas!=null?datas.size():0;
    }
    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }


}
