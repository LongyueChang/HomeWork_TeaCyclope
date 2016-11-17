package com.example.administrator.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.administrator.intefaces.GetByteCallback;
import com.example.administrator.utils.HttpUtils;

/**
 * Created by Administrator on 2016/11/15.
 */
public class WebAsyncTask extends AsyncTask<String,Void,byte[]>{
    private Context mContext;
    private GetByteCallback mCallback;
    private ProgressDialog mDialog;

    public WebAsyncTask(Context mContext,GetByteCallback callback) {
        this.mContext=mContext;
        mCallback = callback;
        mDialog=new ProgressDialog(mContext);
        mDialog.setTitle("提示");
        mDialog.setMessage("loading...");
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
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
        if (bytes != null) {
            mCallback.getbytes(bytes);
            mDialog.dismiss();
        }
    }
}
