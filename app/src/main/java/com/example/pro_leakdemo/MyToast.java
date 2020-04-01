package com.example.pro_leakdemo;

import android.content.Context;
import android.widget.Toast;

public class MyToast {
    //私有静态实例
    private static Context mContext=null;
    public MyToast(Context context){
        mContext=context;
    }
    public void showToast(){
        Toast.makeText(mContext,"show toast",Toast.LENGTH_LONG).show();
    }
}
