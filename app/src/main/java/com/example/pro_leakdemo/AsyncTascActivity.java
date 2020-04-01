package com.example.pro_leakdemo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AsyncTascActivity extends Activity {
    private Button button;
    private TextView textView;
    private ProgressBar progressBar;
    private Context mContext;
    private MyAsyncTask myAsyncTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_asynctask);
        mContext=AsyncTascActivity.this;
        button=findViewById(R.id.btn1);
        textView=findViewById(R.id.tv1);
        progressBar=findViewById(R.id.pb1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAsyncTask=new MyAsyncTask();
                myAsyncTask.execute();
            }
        });
    }

    //内部类，需要避免出现内存泄漏，方式1：设置为静态内部类，弱引用传递外部类参数；方式2：不使用内部类，新建一个类；
    //AsyncTask传入三个参数
    //Params:后台任务需要参数
    //Progress:任务进度
    //Result:完成耗时操作后返回值
    private class MyAsyncTask extends AsyncTask<String,Integer,String>{
        //后台执行方法
        @Override
        protected String doInBackground(String... strings) {
            //后台执行耗时操作，返回
            for (int i=0;i<1000;i++){
                //调用onProgressUpdate
                publishProgress(i);
                SystemClock.sleep(100);
            }
            return "result string";
        }
        //后台任务执行之前执行逻辑
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //后台任务执行后执行逻辑
        @Override
        protected void onPostExecute(String s) {
            //更新UI界面
            textView.setText(s);
        }
        //当进度更新时调用
        @Override
        protected void onProgressUpdate(Integer... values) {
            //这里可以来更新UI界面
            progressBar.setMax(1000);
            progressBar.setProgress(values[0]);
        }
    }
}
