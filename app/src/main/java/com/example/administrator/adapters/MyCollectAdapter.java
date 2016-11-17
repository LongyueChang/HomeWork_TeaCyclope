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

import beens.CollectBeen;

/**
 * Created by Administrator on 2016/11/15.
 */
public class MyCollectAdapter extends BaseAdapter {
    private Context mContext;
    private  List<CollectBeen> datas;
    private MyLruCache mLruCache;

    public MyCollectAdapter(Context context, List<CollectBeen> datas) {
        mContext = context;
        this.datas = datas;
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
        if (convertView != null) {
            ret=convertView;
            holder= (ViewHolder) ret.getTag();
        }else {
            ret= LayoutInflater.from(mContext).inflate(R.layout.collect_item,parent,false);
            holder=new ViewHolder();
            holder.title= (TextView) ret.findViewById(R.id.collect_title_adper);
            holder.source= (TextView) ret.findViewById(R.id.collect_source_adper);
            holder.create_time= (TextView) ret.findViewById(R.id.collect_time_adper);
            holder.mImageView= (ImageView) ret.findViewById(R.id.collect_img_adper);
            ret.setTag(holder);
        }
            holder.title.setText(datas.get(position).getTitle());
            holder.source.setText(datas.get(position).getSource());
            holder.create_time.setText(datas.get(position).getCreate_time());
            final String imagPath=datas.get(position).getImg();

        Bitmap catchBitmap = getCatch(imagPath);
        if (catchBitmap != null) {
            holder.mImageView.setImageBitmap(catchBitmap);
        }else {
            holder.mImageView.setTag(imagPath);
            getNetImage(imagPath,holder.mImageView);
        }
        return ret;
    }

    private void getNetImage(final String imagPath, final ImageView imageView) {
        new ImageAsyncTask(new GetByteCallback() {
            @Override
            public void getbytes(byte[] bytes) {
                if (bytes != null&&imagPath.equals(imageView.getTag())) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);

                    mLruCache.put(imagPath.replaceAll("/",""),bitmap);

                    String root=mContext.getExternalCacheDir().getAbsolutePath();
                    SdCardUtils.saveFile(bytes,root,imagPath.replaceAll("/",""));
                }
            }
        }).execute(imagPath);


    }

    private Bitmap getCatch(String imagPath) {
        imagPath = imagPath.replaceAll("/", "");
        Bitmap bitmap = mLruCache.get(imagPath);
        if (bitmap != null) {
            return bitmap;
        }else {
            String root = mContext.getExternalCacheDir().getAbsolutePath();
            String fileName=root+ File.separator+imagPath;
            byte[] byteFromFile = SdCardUtils.getByteFromFile(fileName);
            if (byteFromFile != null) {
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(byteFromFile, 0, byteFromFile.length);
                mLruCache.put(imagPath,bitmap1);
                return bitmap1;
            }
        }
        return null;
    }


    public class ViewHolder{
        private ImageView mImageView;
        private TextView title,source,create_time;
    }
}
