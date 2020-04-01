package com.example.pro_leakdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=findViewById(R.id.btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTask();
            }
        });

        button2=findViewById(R.id.btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMessage();
            }
        });

        button3=findViewById(R.id.btn3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInit();
            }
        });

        button4=findViewById(R.id.btn4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建MyToast实例，将Context上下文赋给静态变量,
                // 当Activity关闭时，静态变量保持Context引用，导致内存泄漏
         //             MyToast myToast= new MyToast(MainActivity.this);
                //使用Application的上下文，当退出Application，就销毁上下文
                MyToast myToast= new MyToast(getApplicationContext());
                myToast.showToast();
            }
        });

        //加载bitmap
        button5=findViewById(R.id.btn5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowBitmapActivity.class);
                startActivity(intent);
            }
        });

        //Asynctask页面
        button6=findViewById(R.id.btn6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AsyncTascActivity.class);
                startActivity(intent);
            }
        });


        //加载ViewStub
        ViewStub viewStub=findViewById(R.id.vs1);
        //两种方法能让ViewStub加载显示页面
        View view= viewStub.inflate();
        //viewStub.setVisibility(View.VISIBLE);
    }

    //执行耗时操作
    public void startTask(){
        MyThread myThread=new MyThread(MainActivity.this);
        myThread.start();
    }
    //修改为静态内部类，不再隐式持有外部类引用
    private static class MyThread extends  Thread{
        //通过弱引用获取Activity中非静态字段、方法
        WeakReference<MainActivity> weakReference=null;
        public MyThread(MainActivity mainActivity){
            weakReference=new WeakReference<MainActivity>(mainActivity);
        }
        //获取外部类对象
        public void getOutObj(){
            //获取外部类对象
            MainActivity mainActivity=weakReference.get();
            //获取成功,通过外部类对象获取属性、方法
            if (mainActivity!=null){

            }
        }
        @Override
        public void run() {
            for(int i=0;i<100;i++){
                //耗时操作
                SystemClock.sleep(1000);
            }
        }
    }

    //调用发送message
    private void startMessage(){
        MyHandler myHandler=new MyHandler();
        Message message=Message.obtain();
        message.what=1;
        message.obj="message from";
        //直接发送消息
//        myHandler.sendMessage(message);
        //发送消息，延时处理,如果直接退出，会出现内存泄漏
        myHandler.sendMessageDelayed(message,5000);
    }
    //Handler内部类
    private class  MyHandler extends Handler{
        //处理Message
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String message= (String) msg.obj;
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    //MainActivity中静态内部类实例
    private static  MyCls myCls = null;
    //开始初始化
    private void startInit(){
        myCls=new MyCls();
    }
    //非静态内部类，在外部类拥有静态实例
    private class MyCls{
        //构造方法
        public MyCls(){
        }
    }

    //java中操作数据为px,需要转换dp为px
    //将dp转换为像素
    public static int dp2px(Context context, int dp){
        //获取密度
        final float  scale = context.getResources().getDisplayMetrics().density;
        //通过密度进行换算
        return (int)(dp*scale+0.5f);
    }

}
