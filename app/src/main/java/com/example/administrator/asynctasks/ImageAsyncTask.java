package com.example.administrator.asynctasks;

import android.os.AsyncTask;

import com.example.administrator.intefaces.GetByteCallback;
import com.example.administrator.utils.HttpUtils;

/**
 * Created by Administrator on 2016/11/13.
 */
public class ImageAsyncTask extends AsyncTask<String,Void,byte[]>{
    private GetByteCallback mCallback;

    public ImageAsyncTask(GetByteCallback callback) {
        mCallback = callback;
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
            mCallback.getbytes(datas);
        }
    }
}
