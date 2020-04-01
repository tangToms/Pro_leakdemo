package com.example.pro_leakdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class ShowBitmapActivity extends Activity {
    private ImageView imageView;
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_showbitmap);
        mContext=ShowBitmapActivity.this;
        //ImageView组件
        imageView=findViewById(R.id.iv_img1);
        Bitmap bitmap=getBitmap1();
        imageView.setImageBitmap(bitmap);
    }
    //直接获取图片显示
    private Bitmap getBitmap(){
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.back);
        return  bitmap;
    }

    //设置压缩图片后显示
    private Bitmap getBitmap1(){
        //option设置
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.back,options);
        //获取图片比例
        int width= options.outWidth;
        int height=options.outHeight;
        //目标宽高
        int targetW=100;
        int targetH=200;
        //缩放比例
        int scaleW = width/targetW;
        int scaleH = height/targetH;
        //缩放比例
        options.inSampleSize= Math.max(scaleH,scaleW);
        //设置图片色彩,
        options.inPreferredConfig=Bitmap.Config.RGB_565;
        options.inJustDecodeBounds=false;
        bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.back,options);

        return  bitmap;
    }
}
