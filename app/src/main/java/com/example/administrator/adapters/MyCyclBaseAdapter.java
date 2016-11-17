package com.example.administrator.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import beens.CycleopediaBeen;

/**
 * Created by Administrator on 2016/11/14.
 */
public class MyCyclBaseAdapter extends BaseAdapter {
    private Context mContext;
    private List<CycleopediaBeen.DataBean> cycDatas;
    private MyLruCache mLruCache;

    public MyCyclBaseAdapter(Context context, List<CycleopediaBeen.DataBean> cycDatas) {
        mContext = context;
        this.cycDatas = cycDatas;

        int maxSize = (int) Runtime.getRuntime().maxMemory();
        mLruCache=new MyLruCache(maxSize);
    }

    @Override
    public int getCount() {
        return cycDatas!=null?cycDatas.size():0;
    }

    @Override
    public Object getItem(int position) {
        return cycDatas.get(position);
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

        holder.titleText.setText(cycDatas.get(position).getTitle());
        holder.timeText.setText(cycDatas.get(position).getCreate_time());
        holder.souceText.setText(cycDatas.get(position).getSource());

        String imagePath = cycDatas.get(position).getWap_thumb();


        Bitmap catchBitmap = getCatch(imagePath);

        if (catchBitmap != null) {
            holder.mImageView.setImageBitmap(catchBitmap);
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
                String tag = (String) imageView.getTag();
                if (bytes != null&&tag.equals(imagePath)) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);

                    mLruCache.put(imagePath.replaceAll("/",""),bitmap);
                    String root=mContext.getExternalCacheDir().getAbsolutePath();
                    SdCardUtils.saveFile(bytes,root,imagePath.replaceAll("/",""));
                }
            }
        }).execute(imagePath);
    }

    private Bitmap getCatch(String imagePath) {
        imagePath=imagePath.replaceAll("/","");
        Bitmap bitmap = mLruCache.get(imagePath);
        if (bitmap != null) {
            return bitmap;
        }else {
            String root = mContext.getExternalCacheDir().getAbsolutePath();
            String fileName=root+ File.separator+imagePath;
            byte[] byteFromFile = SdCardUtils.getByteFromFile(fileName);
            if (byteFromFile != null) {
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(byteFromFile, 0, byteFromFile.length);
                mLruCache.put(imagePath,bitmap1);
                return bitmap1;
            }
        }
        return null;
    }

    public class ViewHolder{
        private ImageView mImageView;
        private TextView titleText,souceText,timeText;
    }



}
