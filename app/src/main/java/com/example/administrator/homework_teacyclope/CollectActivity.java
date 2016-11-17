package com.example.administrator.homework_teacyclope;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.example.administrator.adapters.MyCollectAdapter;
import com.example.administrator.utils.MySQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import beens.CollectBeen;

public class CollectActivity extends AppCompatActivity {
    private ListView mListView;
    private MySQLiteOpenHelper mOpenHelper;
    private List<CollectBeen> datas=new ArrayList<>();
    private BaseAdapter mAdapter;
    private Cursor mCursor;
    private String mid;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initView();

        initData();//获取数据源

        initListView();//展示数据

    }

    private void initListView() {
        mAdapter = new MyCollectAdapter(this,datas);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CollectActivity.this, WebCollectActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",mid);
                intent.putExtras(bundle);
                CollectActivity.this.startActivity(intent);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(CollectActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否删除条目!");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MySQLiteOpenHelper helper=new MySQLiteOpenHelper(CollectActivity.this);
                        SQLiteDatabase db = helper.getReadableDatabase();
                        db.delete("mySqatle", "title=?", new String[]{datas.get(position).getTitle()});
                        datas.remove(position);
                        mAdapter.notifyDataSetChanged();

                        db.close();
                    }
                });
                builder.setNegativeButton("取消",null);

                builder.create().show();
                return true;
            }
        });
    }

    private void initData() {
        mOpenHelper=new MySQLiteOpenHelper(this);
        String path = getDatabasePath("mytea.db").getAbsolutePath();
        mDb = mOpenHelper.getReadableDatabase();
        mDb=SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = mDb.query("mySqatle", new String[]{
                        "title", "web_thumb", "create_time", "source", "item_id" },
                null, null, null, null, null);
        datas.clear();
        while (cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String web_thumb = cursor.getString(cursor.getColumnIndex("web_thumb"));
            String create_time = cursor.getString(cursor.getColumnIndex("create_time"));
            String source = cursor.getString(cursor.getColumnIndex("source"));
            String item_id = cursor.getString(cursor.getColumnIndex("item_id"));

            CollectBeen collectBeen=new CollectBeen();
            collectBeen.setCreate_time(create_time);
            collectBeen.setSource(source);
            collectBeen.setTitle(title);
            collectBeen.setImg(web_thumb);
            collectBeen.setId(item_id);
            datas.add(collectBeen);
        }
        cursor.close();
        mDb.close();
    }

    private void initView() {
        mListView= (ListView)findViewById(R.id.collect_listView);
    }

    public void collOnclick(View view) {
        switch (view.getId()){
            case R.id.collect_back://返回主界面
                Intent intent=new Intent(CollectActivity.this,MainActivity.class);
                this.startActivity(intent);
                this.finish();
                break;

            case R.id.collect_home://返回桌面
                Intent intent2=new Intent(Intent.ACTION_MAIN,null);
                intent2.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent2);
                break;
        }
    }
}
