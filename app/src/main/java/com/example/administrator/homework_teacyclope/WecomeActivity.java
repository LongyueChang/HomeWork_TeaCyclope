package com.example.administrator.homework_teacyclope;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

//TODO 开机界面
public class WecomeActivity extends AppCompatActivity {
    private ImageView wecomeImage;
    private TextView wecomeText;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    wecomeText.setText(mNum+"秒");
                    break;

                case 1:
                    wecomeText.setText(mNum+"秒");
                    break;
            }
        }
    };
    private Timer mTime;
    private int mNum=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wecome);

        initView();//初始化控件

        initAnimation();//动画展示
    }

    private void initAnimation() {
        ScaleAnimation scaleAnimation=new ScaleAnimation(1,1.2f,1,1.2f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(3000);
        scaleAnimation.setFillAfter(true);
        wecomeImage.startAnimation(scaleAnimation);

        SharedPreferences sp=getSharedPreferences("appConfig",MODE_PRIVATE);
        boolean isFist = sp.getBoolean("isFist", true);
        if (isFist){
            mTime = new Timer();
            mTime.schedule(new TimerTask() {
                @Override
                public void run() {
                    mNum--;
                    mHandler.sendEmptyMessage(0);
                    if(mNum==1){
                        WecomeActivity.this.startActivity(new Intent(WecomeActivity.this,AnimationActivity.class));
                        WecomeActivity.this.finish();
                    }
                }
            },1000,1000);


        }else {
//            mHandler.sendEmptyMessageDelayed(1,3000);
            mTime = new Timer();
            mTime.schedule(new TimerTask() {
                @Override
                public void run() {
                    mNum--;
                    mHandler.sendEmptyMessage(1);
                    if(mNum==1){
                        WecomeActivity.this.startActivity(new Intent(WecomeActivity.this,MainActivity.class));
                        WecomeActivity.this.finish();
                    }
                }
            },1000,1000);

        }

    }

    private void initView() {
        wecomeImage= (ImageView) findViewById(R.id.wecome_image);
        wecomeText= (TextView) findViewById(R.id.wecome_time);
    }
}
