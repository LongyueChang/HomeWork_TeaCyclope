package com.example.administrator.homework_teacyclope;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.adapters.MySearchAdapter;
import com.example.administrator.asynctasks.MyAsyncTask;
import com.example.administrator.intefaces.GetByteCallback;

import java.util.ArrayList;
import java.util.List;

import beens.SearchBeen;

public class SearchActivity extends AppCompatActivity {
    private TextView mTitle;
    private String mPath;
    private String mUriPath;
    private ListView mListView;
    private MySearchAdapter adapter;
    private List<SearchBeen.DataBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();//初始化

        initData();//获取数据源

        initListView();
    }

    private void initListView() {
        adapter=new MySearchAdapter(SearchActivity.this,datas);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent=new Intent(SearchActivity.this, WebActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("img",datas.get(position).getWap_thumb());
                bundle.putString("key",datas.get(position).getId());
                intent.putExtras(bundle);
                SearchActivity.this.startActivity(intent);
            }
        });

    }


    private void initData() {
        Bundle extras = getIntent().getExtras();
        mPath = extras.getString("path");
        mUriPath = extras.getString("uriPath");
        datas=new ArrayList<>();
        new MyAsyncTask(this, new GetByteCallback() {
            @Override
            public void getbytes(byte[] bytes) {
                if (bytes != null) {
                    SearchBeen searchBeen = JSON.parseObject(new String(bytes), SearchBeen.class);
                    List<SearchBeen.DataBean> data = searchBeen.getData();
                    datas.addAll(data);
                    adapter.notifyDataSetChanged();
                }
            }
        }).execute(mUriPath);

    }

    private void initView() {
        mTitle= (TextView) findViewById(R.id.search_text);
        mListView= (ListView) findViewById(R.id.search_listView);
    }

    public void collOnclick(View view) {



    }
}
