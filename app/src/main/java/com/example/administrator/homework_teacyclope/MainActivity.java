package com.example.administrator.homework_teacyclope;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.administrator.adapters.MyFragmentAdapter;
import com.example.administrator.fragments.MyFragment;
import com.softpo.viewpagertransformer.AccordionTransformer;

import java.util.ArrayList;
import java.util.List;

import static urls.Urls.SEARCH_URL;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;
    private List<Fragment> datas;
    private FragmentStatePagerAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private EditText mEditText;
    private Fragment mFragment,searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initViewPager();

        initRadioGroup();

        initDrawer();//抽屉初始化

    }

    private void initView() {
        mEditText= (EditText) findViewById(R.id.dr_ed_search);
    }

    private void initDrawer() {
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mDrawerLayout,0,0);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initRadioGroup() {
        mRadioGroup= (RadioGroup) findViewById(R.id.tables);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
                    if(checkedId==group.getChildAt(i).getId()){
                        mViewPager.setCurrentItem(i);
                    }
                }
            }
        });
        mRadioGroup.check(R.id.rd_1);
    }

    private void initViewPager() {
        mDrawerLayout= (DrawerLayout) findViewById(R.id.activity_main);

        mViewPager= (ViewPager) findViewById(R.id.viewPager);

        datas=new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            mFragment = new MyFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("index",j);
            mFragment.setArguments(bundle);
            datas.add(mFragment);
            Log.i("TAG", "----------->Fragment的datas:" +datas.size());
       }

        mAdapter = new MyFragmentAdapter(getSupportFragmentManager(),datas);
        mViewPager.setAdapter(mAdapter);

        //TODO 动画展示效果
        mViewPager.setPageTransformer(true,new AccordionTransformer());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mRadioGroup.check(mRadioGroup.getChildAt(position).getId());

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
            });
    }


    //抽屉里面按钮监听
    public void drawer(View view) {
        switch (view.getId()){
            case R.id.dr_cent://中间抽屉
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;

            case R.id.dr_rt_right://右侧返回抽屉
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                break;

            case R.id.dr_home://右侧抽屉home

                break;

            case R.id.dr_bt_collect://收藏
                Intent intent=new Intent(MainActivity.this, CollectActivity.class);
                this.startActivity(intent);

                break;

            case R.id.dr_hist_about://跳转历史记录
                Intent histIntent=new Intent(MainActivity.this, HistActivity.class);
                this.startActivity(histIntent);

                break;

            case R.id.dr_bt_about://关于
                break;

            case R.id.dr_bt_search://搜索
                String trim = mEditText.getText().toString().trim();
                Intent searchIntent=new Intent(MainActivity.this,SearchActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("path",trim);
                bundle.putString("uriPath", SEARCH_URL+trim);
                searchIntent.putExtras(bundle);
                this.startActivity(searchIntent);
                break;
        }
    }
}
