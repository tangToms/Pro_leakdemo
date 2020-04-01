package com.example.pro_leakdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import static java.lang.Runtime.getRuntime;

//图片加载缓存类
//使用LruCache类缓存
public class ImageCache {
    private static ImageCache ImageInstance=null;
    private static LruCache<String,Bitmap> lruCache;
    //私有构造
    private ImageCache(){
        //获取运行时最大内存
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        //创建LRU缓存对象,key:图片名路径,value:图片Bitmap对象
        lruCache=new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //返回当前bitmap所占空间大小
                int byteCount=value.getByteCount();
                return byteCount;
            }
        };
    }
    //提供获取实例方法
    public static ImageCache getInstance(){
        if (ImageInstance==null){
            synchronized (ImageCache.class){
                if (ImageInstance==null){
                    ImageInstance=new ImageCache();
                }
            }
        }
        return  ImageInstance;
    }

    public Bitmap getBitmap(String imageName){
        Bitmap bitmap = lruCache.get(imageName);
        if (bitmap!=null){
            return bitmap;
        }
        //如果LRU缓存中没有，直接读取文件，返回
        bitmap= BitmapFactory.decodeFile(imageName);
        //并且将加载的图片放入缓存
        lruCache.put(imageName,bitmap);
        return  bitmap;
    }
}
