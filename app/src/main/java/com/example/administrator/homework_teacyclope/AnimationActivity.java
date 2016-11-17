package com.example.administrator.homework_teacyclope;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.adapters.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AnimationActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private Button mButton;
    private List<ImageView> mImageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initView();//初始化控件

        initData();//pager数据源

        initPager();//设置pager适配器
    }

    private void initData() {
        mImageData = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView imageView=new ImageView(this);
          switch (i){
              case 0:
                  imageView.setImageResource(R.mipmap.slide1);
                    break;
              case 1:
                  imageView.setImageResource(R.mipmap.slide2);
                  break;
              case 2:
                  imageView.setImageResource(R.mipmap.slide3);
                  break;
          }
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageData.add(imageView);
        }
    }

    private void initPager() {
        PagerAdapter adapter=new MyPagerAdapter(mImageData);
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(this);
    }

    private void initView() {
        mViewPager= (ViewPager) findViewById(R.id.animation_ViewPager);
        mButton= (Button) findViewById(R.id.animation_go);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            mButton.setVisibility(View.VISIBLE);
        }else {
            mButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void click(View view) {
        SharedPreferences sp=getSharedPreferences("appConfig",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isFist",false);
        editor.commit();

        Intent intent=new Intent(AnimationActivity.this,MainActivity.class);
        AnimationActivity.this.startActivity(intent);
    }
}
