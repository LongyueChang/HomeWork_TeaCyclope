package com.example.administrator.asynctasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.administrator.intefaces.GetArrayImageCallback;
import com.example.administrator.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import beens.ImageBeen;

/**
 * Created by Administrator on 2016/11/16.
 */

public class HeadImageAsyncTask extends AsyncTask<String,Void,List<Bitmap>> {
    private GetArrayImageCallback mCallback;
    private List<Bitmap> datas;

    public HeadImageAsyncTask(GetArrayImageCallback callback) {
        mCallback = callback;
    }

    @Override
    protected List<Bitmap> doInBackground(String... params) {
        byte[] bytes = HttpUtils.getBytes(params[0]);
        ImageBeen imageBeen = JSON.parseObject(new String(bytes), ImageBeen.class);
        datas=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String image = imageBeen.getData().get(i).getImage();
            byte[] bytes1 = HttpUtils.getBytes(image);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
            datas.add(bitmap);
            datas.size();
            Log.i("TAG", "----------->datas的长度:------------" +datas.size());
        }

        return datas;
    }

    @Override
    protected void onPostExecute(List<Bitmap> data) {
        super.onPostExecute(data);
        if (data != null) {
            mCallback.getCallback(data);
        };
    }
}
