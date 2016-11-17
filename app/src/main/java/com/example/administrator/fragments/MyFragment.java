package com.example.administrator.fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.adapters.MyBaseAdapter;
import com.example.administrator.adapters.MyConAdapter;
import com.example.administrator.adapters.MyCyclBaseAdapter;
import com.example.administrator.adapters.MyDataAdapter;
import com.example.administrator.adapters.MyOperAdapter;
import com.example.administrator.adapters.MyPagerAdapter;
import com.example.administrator.asynctasks.HeadImageAsyncTask;
import com.example.administrator.asynctasks.MyAsyncTask;
import com.example.administrator.homework_teacyclope.R;
import com.example.administrator.homework_teacyclope.WebActivity;
import com.example.administrator.intefaces.GetArrayImageCallback;
import com.example.administrator.intefaces.GetByteCallback;
import com.example.administrator.utils.MySQLiteHistryHelper;
import com.example.administrator.utils.SdCardUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import beens.ConsultBeen;
import beens.CycleopediaBeen;
import beens.Databeen;
import beens.HeadaLineBeen;
import beens.OperateBeen;

import static urls.Urls.BASE_URL;
import static urls.Urls.CONSULT_TYPE;
import static urls.Urls.CYCLOPEDIA_TYPE;
import static urls.Urls.DATA_TYPE;
import static urls.Urls.HEADERIMAGE_URL;
import static urls.Urls.HEADLINE_TYPE;
import static urls.Urls.HEADLINE_URL;
import static urls.Urls.OPERATE_TYPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    private TextView empty;
    private ListView mListView;
    private boolean isDown=false;
    private List<HeadaLineBeen.DataBean> datas=new ArrayList<>();
    private List<CycleopediaBeen.DataBean> cycDatas=new ArrayList<>();;
    private List<ConsultBeen.DataBean> conDatas=new ArrayList<>();
    private List<OperateBeen.DataBean> operDatas=new ArrayList<>();
    private List<Databeen.DataBean> dataDatas=new ArrayList<>();
    private int page=1;
    private ViewPager mViewPager;
    private List<ImageView> imageData=new ArrayList<>();
    private BaseAdapter mAdapter,cyclAdapter,conAdapter,operAdapter,dataAdapter;
    private PagerAdapter pAdapter;
    private int index;
    private Button mButton;
    private Animation mAnimation;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_my, container, false);
        Bundle arguments = getArguments();
        index = arguments.getInt("index");

        initView(ret);//初始化控件

        initListView();//设置适配器

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListView.setSelection(0);
            }
        });

        loadData(page);//加载数据


        return ret;
    }


    private void initListView() {

        Log.i("TAG", "----------->index:" + index);
        switch (index){
            case 0:
                View ret= LayoutInflater.from(getContext()).inflate(R.layout.pager_item,null,false);
                mViewPager= (ViewPager) ret.findViewById(R.id.hend_Viewpager);
                pAdapter = new MyPagerAdapter(imageData);
                mViewPager.setAdapter(pAdapter);

                mListView.addHeaderView(ret);
                mAdapter = new MyBaseAdapter(getContext(),datas);

                mListView.setAdapter(mAdapter);
                mListView.setEmptyView(empty);

                //Todo 点击条目跳转到webView里面
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        Intent intent=new Intent(getContext(), WebActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("img",datas.get(position).getWap_thumb());
                        bundle.putString("key",datas.get(position).getId());
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);

                        //TODO 创建历史记录数据库
                        MySQLiteHistryHelper histryHelper=new MySQLiteHistryHelper(getContext());
                        SQLiteDatabase db = histryHelper.getReadableDatabase();
                        ContentValues values=new ContentValues();
                        values.put("title",datas.get(position).getTitle());
                        values.put("web_thumb",datas.get(position).getWap_thumb());
                        values.put("create_time",datas.get(position).getCreate_time());
                        values.put("source",datas.get(position).getSource());
                        values.put("item_id",datas.get(position).getId());
                        db.insert("myHistory",null,values);
                    }
                });

                //TODO 长按监听
                mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                        AlertDialog.Builder dialog=new AlertDialog.Builder(getContext());
                        dialog.setTitle("提示");
                        dialog.setMessage("要删除吗?");
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"取消成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                                mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.translate);
                                mAnimation.setFillAfter(true);
                                mAnimation.setDuration(2000);
                                mAnimation.setRepeatMode(Animation.REVERSE);
                                mListView.getChildAt(position).startAnimation(mAnimation);

                                mAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                        datas.remove(position-1);
                                        mAdapter.notifyDataSetChanged();
                                        int childCount = mListView.getChildCount();

                                        AnimationSet set=new AnimationSet(true);
                                        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1,
                                                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                                        scaleAnimation.setDuration(2000);

                                        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1.0f);
                                        alphaAnimation.setDuration(2000);

                                        set.addAnimation(scaleAnimation);
                                        set.addAnimation(alphaAnimation);
                                        int currentTop = view.getTop();

                                        for (int i = 0; i < childCount; i++) {
                                            View itemView=mListView.getChildAt(i);
                                            if(itemView.getTop()>=currentTop){
                                                itemView.startAnimation(set);
                                            }
                                        }

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });


                            }
                        });


                        dialog.create().show();
                        return false;
                    }
                });


                //TODO 分页加载数据
                mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if(isDown&&scrollState==SCROLL_STATE_IDLE){
                            page++;
                            Log.i("TAG", "----------->page---------------:" +page);
                            loadData(page);
                        }
                    }
                    @Override
                    public void onScroll(AbsListView view,
                                         int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        if(totalItemCount!=0){
                            if(firstVisibleItem+visibleItemCount==totalItemCount){
                                isDown=true;
                            }else {
                                isDown=false;
                            }
                        }
                    }
                });
                break;

            case 1:
                cyclAdapter = new MyCyclBaseAdapter(getContext(),cycDatas);
                mListView.setAdapter(cyclAdapter);
                mListView.setEmptyView(empty);

                mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if(isDown&&scrollState==SCROLL_STATE_IDLE){
                            page++;
                            loadData(page);
                        }
                    }
                    @Override
                    public void onScroll(AbsListView view,
                                         int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        if(totalItemCount!=0){
                            if(firstVisibleItem+visibleItemCount==totalItemCount){
                                isDown=true;
                            }else {
                                isDown=false;
                            }
                        }
                    }
                });
                break;
            case 2:
                conAdapter=new MyConAdapter(getContext(),conDatas);
                mListView.setAdapter(conAdapter);
                mListView.setEmptyView(empty);
                mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if(isDown&&scrollState==SCROLL_STATE_IDLE){
                            page++;
                            loadData(page);
                        }
                    }
                    @Override
                    public void onScroll(AbsListView view,
                                         int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        if(totalItemCount!=0){
                            if(firstVisibleItem+visibleItemCount==totalItemCount){
                                isDown=true;
                            }else {
                                isDown=false;
                            }
                        }
                    }
                });
                break;

            case 3:
                operAdapter=new MyOperAdapter(getContext(),operDatas);
                mListView.setAdapter(operAdapter);
                mListView.setEmptyView(empty);
                mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if(isDown&&scrollState==SCROLL_STATE_IDLE){
                            page++;
                            loadData(page);
                        }
                    }
                    @Override
                    public void onScroll(AbsListView view,
                                         int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        if(totalItemCount!=0){
                            if(firstVisibleItem+visibleItemCount==totalItemCount){
                                isDown=true;
                            }else {
                                isDown=false;
                            }
                        }
                    }
                });
                break;

            case 4:
                dataAdapter=new MyDataAdapter(getContext(),dataDatas);
                mListView.setAdapter(dataAdapter);
                mListView.setEmptyView(empty);
                mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if(isDown&&scrollState==SCROLL_STATE_IDLE){
                            page++;
                            loadData(page);
                        }
                    }
                    @Override
                    public void onScroll(AbsListView view,
                                         int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        if(totalItemCount!=0){
                            if(firstVisibleItem+visibleItemCount==totalItemCount){
                                isDown=true;
                            }else {
                                isDown=false;
                            }
                        }
                    }
                });
                break;
        }
    }

    private void initView(View ret) {
        mListView= (ListView) ret.findViewById(R.id.listView);
        empty= (TextView) ret.findViewById(R.id.empty);
        mButton= (Button) ret.findViewById(R.id.up_page);
    }


    //加载数据
    private void loadData(final int pages){
        switch (index){
            case 0:
                String root=getContext().getExternalCacheDir().getAbsolutePath();
                String path = HEADLINE_URL + HEADLINE_TYPE + pages;
                final String fileName=root+ File.separator+path.replaceAll("/","");
                byte[] byteFromFile = SdCardUtils.getByteFromFile(fileName);
                if (byteFromFile != null) {
                    HeadaLineBeen headaLineBeen = JSON.parseObject(new String(byteFromFile), HeadaLineBeen.class);
                    datas.addAll(headaLineBeen.getData());
                    mAdapter.notifyDataSetChanged();
                }else {
                    new MyAsyncTask(getContext(), new GetByteCallback() {
                        @Override
                        public void getbytes(byte[] bytes) {
                            if (bytes != null) {
                                HeadaLineBeen headBeen = JSON.parseObject(new String(bytes), HeadaLineBeen.class);
                                datas.addAll(headBeen.getData());
                                Log.i("TAG", "----------->datas----------------:" + datas.size());
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }).execute(path);
                }

                new HeadImageAsyncTask(new GetArrayImageCallback() {
                    @Override
                    public void getCallback(List<Bitmap> datas) {
                        if (datas != null) {
                            for (int i = 0; i < datas.size(); i++) {
                                ImageView imageView=new ImageView(getContext());
                                imageView.setImageBitmap(datas.get(i));
                                imageData.add(imageView);
                                pAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }).execute(HEADERIMAGE_URL);
                break;

            case 1:
                final String root1=getContext().getExternalCacheDir().getAbsolutePath();
                String path1 = BASE_URL + CYCLOPEDIA_TYPE + pages;
                final String fileName1=root1+ File.separator+path1.replaceAll("/","");
                byte[] byteFromFile1 = SdCardUtils.getByteFromFile(fileName1);
                if (byteFromFile1 != null) {
                    HeadaLineBeen headaLineBeen = JSON.parseObject(new String(byteFromFile1), HeadaLineBeen.class);
                    datas.addAll(headaLineBeen.getData());
                    mAdapter.notifyDataSetChanged();
                }else {
                    new MyAsyncTask(getContext(),new GetByteCallback() {
                    @Override
                    public void getbytes(byte[] bytes) {
                        if (bytes != null) {
                            SdCardUtils.saveFile(bytes,root1,fileName1);
                            CycleopediaBeen cycleopediaBeen = JSON.parseObject(new String(bytes), CycleopediaBeen.class);
                            cycDatas.addAll(cycleopediaBeen.getData());
                            cyclAdapter.notifyDataSetChanged();

                        }
                    }
                }).execute(path1);
                }
                break;

            case 2:
                final String root2=getContext().getExternalCacheDir().getAbsolutePath();
                String path2 = BASE_URL + CONSULT_TYPE + pages;
                final String fileName2=root2+ File.separator+path2.replaceAll("/","");
                byte[] byteFromFile2 = SdCardUtils.getByteFromFile(fileName2);
                if (byteFromFile2 != null) {
                    HeadaLineBeen headaLineBeen = JSON.parseObject(new String(byteFromFile2), HeadaLineBeen.class);
                    datas.addAll(headaLineBeen.getData());
                    mAdapter.notifyDataSetChanged();
                }else {
                    new MyAsyncTask(getContext(), new GetByteCallback() {
                        @Override
                        public void getbytes(byte[] bytes) {
                            if (bytes != null) {
                                SdCardUtils.saveFile(bytes,root2,fileName2);
                                ConsultBeen consultBeen = JSON.parseObject(new String(bytes), ConsultBeen.class);
                                conDatas.addAll(consultBeen.getData());
                                conAdapter.notifyDataSetChanged();
                            }
                        }
                    }).execute(path2);
                }
                    break;


            case 3:
                final String root3=getContext().getExternalCacheDir().getAbsolutePath();
                String path3 = BASE_URL + OPERATE_TYPE + pages;
                final String fileName3=root3+ File.separator+path3.replaceAll("/","");
                byte[] byteFromFile3 = SdCardUtils.getByteFromFile(fileName3);
                if (byteFromFile3 != null) {
                    HeadaLineBeen headaLineBeen = JSON.parseObject(new String(byteFromFile3), HeadaLineBeen.class);
                    datas.addAll(headaLineBeen.getData());
                    mAdapter.notifyDataSetChanged();
                }else {
                    new MyAsyncTask(getContext(), new GetByteCallback() {
                        @Override
                        public void getbytes(byte[] bytes) {
                            if (bytes != null) {
                                SdCardUtils.saveFile(bytes,root3,fileName3);
                                OperateBeen operateBeen = JSON.parseObject(new String(bytes), OperateBeen.class);
                                operDatas.addAll(operateBeen.getData());
                                operAdapter.notifyDataSetChanged();
                            }
                        }
                    }).execute(path3);
                }
                break;

            case 4:

                final String root4=getContext().getExternalCacheDir().getAbsolutePath();
                String path4 = BASE_URL + OPERATE_TYPE + pages;
                final String fileName4=root4+ File.separator+path4.replaceAll("/","");
                byte[] byteFromFile4 = SdCardUtils.getByteFromFile(fileName4);
                if (byteFromFile4 != null) {
                    HeadaLineBeen headaLineBeen = JSON.parseObject(new String(byteFromFile4), HeadaLineBeen.class);
                    datas.addAll(headaLineBeen.getData());
                    mAdapter.notifyDataSetChanged();
                }else {

                    new MyAsyncTask(getContext(), new GetByteCallback() {
                        @Override
                        public void getbytes(byte[] bytes) {
                            if (bytes != null) {
                                SdCardUtils.saveFile(bytes, root4, fileName4);
                                Databeen databeen = JSON.parseObject(new String(bytes), Databeen.class);
                                dataDatas.addAll(databeen.getData());
                                dataAdapter.notifyDataSetChanged();
                            }
                        }
                    }).execute(BASE_URL + DATA_TYPE + pages);
                }
                break;
        }
    }


}
