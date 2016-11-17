package com.example.administrator.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.homework_teacyclope.R;
import com.example.administrator.intefaces.GetByteCallback;
import com.example.administrator.utils.HttpUtils;

/**
 * Created by Administrator on 2016/11/13.
 */
public class MyAsyncTask extends AsyncTask<String,Void,byte[]>{

    private Context mContext;
    private GetByteCallback mCallBack;
    private ProgressDialog mDialog;

    public MyAsyncTask(Context context, GetByteCallback callBack) {
        mContext = context;
        mCallBack = callBack;
        mDialog=new ProgressDialog(mContext);
        mDialog.setTitle("提示信息");
        mDialog.setIcon(R.drawable.title_selector);
        mDialog.setMessage("正在加载...");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog.show();
    }

    @Override
    protected byte[] doInBackground(String... params) {
        byte[] bytes = HttpUtils.getBytes(params[0]);

        return bytes;
    }

    @Override
    protected void onPostExecute(byte[] datas) {
        super.onPostExecute(datas);
        if (datas != null) {
            mCallBack.getbytes(datas);
            mDialog.dismiss();
            Log.i("TAG", "----------->加载的数据mDataBeens:" +datas.length);
        }else {
            Log.i("TAG", "----------->加载的数据mDataBeens:数据为空<-------------");
        }
    }

}
