package com.example.administrator.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.asynctasks.ImageAsyncTask;
import com.example.administrator.homework_teacyclope.R;
import com.example.administrator.intefaces.GetByteCallback;
import com.example.administrator.utils.MyLruCache;
import com.example.administrator.utils.SdCardUtils;

import java.io.File;
import java.util.List;

import beens.HeadaLineBeen;

/**
 * Created by Administrator on 2016/11/13.
 */
public class MyBaseAdapter extends BaseAdapter {
    private Context mContext;
    private  List<HeadaLineBeen.DataBean> datas;
    private MyLruCache mLruCache;

    public MyBaseAdapter(Context context, List<HeadaLineBeen.DataBean> datas) {
        this.mContext = context;
        this.datas=datas;
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        mLruCache=new MyLruCache(maxSize);
    }

    @Override
    public int getCount() {
        return datas!=null?datas.size():0;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret=null;
        ViewHolder holder=null;
        if(convertView!=null){
            ret=convertView;
            holder= (ViewHolder) ret.getTag();
        }else {
            ret= LayoutInflater.from(mContext).inflate(R.layout.headline_item,parent,false);
            holder=new ViewHolder();
            holder.mImageView= (ImageView) ret.findViewById(R.id.image_show);
            holder.titleText= (TextView) ret.findViewById(R.id.titleText);
            holder.souceText= (TextView) ret.findViewById(R.id.souceText);
            holder.timeText= (TextView) ret.findViewById(R.id.timeText);
            ret.setTag(holder);
        }

        holder.titleText.setText(datas.get(position).getTitle());
        holder.timeText.setText(datas.get(position).getCreate_time());
        holder.souceText.setText(datas.get(position).getSource());

        String imagePath = datas.get(position).getWap_thumb();

        Log.i("TAG", "----------->适配器中imagepath------------:" +imagePath);
        Bitmap lunchBitmap = getLunch(imagePath);
        if (lunchBitmap != null) {
            holder.mImageView.setImageBitmap(lunchBitmap);
        }else {
            holder.mImageView.setTag(imagePath);
            getNetImage(imagePath,holder.mImageView);
        }

        return ret;
    }

    private void getNetImage(final String imagePath, final ImageView imageView) {
        new ImageAsyncTask(new GetByteCallback(){
            @Override
            public void getbytes(byte[] bytes) {
                String  tag = (String) imageView.getTag();
                if (tag.equals(imagePath)) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);

                    //一级缓存
                    mLruCache.put(imagePath.replaceAll("/",""),bitmap);

                    //二级缓存
                    String  root=mContext.getExternalCacheDir().getAbsolutePath();
                    SdCardUtils.saveFile(bytes,root,imagePath.replaceAll("/",""));
                }
            }
        }).execute(imagePath);

    }

    private Bitmap getLunch(String imagePath) {
        imagePath=imagePath.replace("/","");
        Bitmap bitmap = mLruCache.get(imagePath);
        if (bitmap != null) {
            return bitmap;
        }else {
            String root = mContext.getExternalCacheDir().getAbsolutePath();
            String fileName=root+ File.separator+imagePath;
            byte[] byteFromFile = SdCardUtils.getByteFromFile(fileName);
            if (byteFromFile != null) {
                Bitmap bitmapSd = BitmapFactory.decodeByteArray(byteFromFile, 0, byteFromFile.length);
                mLruCache.put(imagePath,bitmapSd);
                return bitmapSd;
            }
        }
        return null;
    }

    public class ViewHolder{
        private ImageView mImageView;
        private TextView titleText,souceText,timeText;
    }
}
