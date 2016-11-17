package com.example.administrator.homework_teacyclope;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.asynctasks.WebAsyncTask;
import com.example.administrator.fragments.MyFragment;
import com.example.administrator.intefaces.GetByteCallback;
import com.example.administrator.utils.MySQLiteOpenHelper;

import beens.WebBeen;

import static urls.Urls.CONTENT_URL;

public class WebActivity extends AppCompatActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String mKey;
    private String mUrl;
    private WebBeen mDataBean;
    private String mWap_content;
    private TextView titleText, titleTme;
    private String mWeiboUrl;
    private String mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();

        initData();


    }


    private void initData() {
        mWebView.getSettings().setJavaScriptEnabled(true);

        Bundle extras = getIntent().getExtras();
        mImg = extras.getString("img");
        mKey = extras.getString("key");
        Log.i("TAG", "--------------------->mkey是多少:" + mKey);
        mUrl = CONTENT_URL + mKey;
        Log.i("TAG", "----------->mUri:" + mUrl);
        mWebView.setWebViewClient(new WebViewClient());

        new WebAsyncTask(this, new GetByteCallback(){
            @Override
            public void getbytes(byte[] bytes) {
                if (bytes != null) {
                    mDataBean = JSON.parseObject(new String(bytes), WebBeen.class);
                    mWap_content = mDataBean.getData().getWap_content();
                    Log.i("TAG", "----------->wap_content:" + mWap_content);
                    mWeiboUrl = mDataBean.getData().getWeiboUrl();
                    titleText.setText(mDataBean.getData().getTitle());
                    titleTme.setText(mDataBean.getData().getCreate_time());

                    mWebView.loadDataWithBaseURL(null, mWap_content,"text/html","utf-8",null);
                }
            }
        }).execute(mUrl);
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
        mProgressBar = (ProgressBar) findViewById(R.id.proGressBar);
        titleText = (TextView) findViewById(R.id.wb_title);
        titleTme = (TextView) findViewById(R.id.wb_title_time);
    }

    public void wbClick(View view) {
        MySQLiteOpenHelper mySqlHelper = new MySQLiteOpenHelper(this);
        SQLiteDatabase db = mySqlHelper.getReadableDatabase();
        switch (view.getId()) {
            case R.id.wb_collect:
                ContentValues values=new ContentValues();
                values.put("title",mDataBean.getData().getTitle());
                values.put("web_thumb",mImg);
                values.put("create_time",mDataBean.getData().getCreate_time());
                values.put("source",mDataBean.getData().getSource());
                values.put("item_id",mDataBean.getData().getId());
                db.insert("mySqatle", null, values);

                Cursor cursor = db.rawQuery("select * from mySqatle", null);
                String item_id=null;
                while (cursor.moveToNext()){
                    item_id = cursor.getString(cursor.getColumnIndex("item_id"));
                }
                cursor.close();
                db.close();

                if (item_id.equals(mDataBean.getData().getId())) {
                    Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.wb_back:
                Intent intent = new Intent();
                intent.setClass(this, MyFragment.class);
                getBaseContext().startActivity(intent);
                break;

            case R.id.wb_share:
                Toast.makeText(this, "分享点击", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
