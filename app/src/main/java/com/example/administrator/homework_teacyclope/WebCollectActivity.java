package com.example.administrator.homework_teacyclope;

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

public class WebCollectActivity extends AppCompatActivity {
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

//        initData();


    }


    private void initData() {
        mWebView.getSettings().setJavaScriptEnabled(true);

        Bundle extras = getIntent().getExtras();
        mKey = extras.getString("id");
        mUrl = CONTENT_URL + mKey;
        Log.i("TAG", "----------->mUri:" + mUrl);
        mWebView.setWebViewClient(new WebViewClient());

        new WebAsyncTask(this, new GetByteCallback() {
            @Override
            public void getbytes(byte[] bytes) {
                if (bytes != null) {
                    mDataBean = JSON.parseObject(new String(bytes), WebBeen.class);
                    mWap_content = mDataBean.getData().getWap_content();
                    Log.i("TAG", "----------->wap_content:" + mWap_content);
                    mWeiboUrl = mDataBean.getData().getWeiboUrl();
                    titleText.setText(mDataBean.getData().getTitle());
                    titleTme.setText(mDataBean.getData().getCreate_time());
                    mWebView.loadDataWithBaseURL(null, mWap_content, "text/html", "utf-8", null);
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
                db.execSQL("insert into webSaqt (di,title,source,wap_content,create_time," +
                        "author,weiboUrl,img) values(?,?,?,?,?,?,?,?) ",new String[]{mDataBean.getData().getId(),mDataBean.getData().getTitle(),
                        mDataBean.getData().getSource(),mDataBean.getData().getWap_content(),mDataBean.getData().getCreate_time(),
                        mDataBean.getData().getAuthor(),mDataBean.getData().getWeiboUrl(),mImg});

                Cursor cursor = db.rawQuery("select * from webSaqt where di=?",null);
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex("di"));
                    if (id.equals(mDataBean.getData().getId())) {
                        Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "收藏失败", Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
                db.close();
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
