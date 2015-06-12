package cn.beijing.zukai.newsimooc.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import cn.beijing.zukai.newsimooc.R;
import cn.beijing.zukai.newsimooc.adapter.NewsAdapter;

/**
 * Created by zukai on 2015/06/12.
 */
public class ImageLoader {
    private ImageView mImageView;
    private String mUrl;
    //创建cache
    private LruCache<String,Bitmap> mCaches;
    private ListView mListView;
    private Set<ImageAsyncTask> mTask;
    public ImageLoader(ListView listView){
        mListView = listView;
        mTask = new HashSet<ImageAsyncTask>();
        //获取最大可用内存
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        mCaches = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
    }

    public void addBitmapToCache(String key,Bitmap value){
        if(getBitmapFromCache(key) == null) {
            mCaches.put(key, value);
        }
    }

    public Bitmap getBitmapFromCache(String key){
        return mCaches.get(key);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mImageView.getTag().equals(mUrl)) {
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };
    public void showImageByThread(final ImageView imageView,final String url){
        mImageView = imageView;
        mUrl = url;
        new Thread(){
            @Override
            public void run() {
                Bitmap bitmap = getBitmapFromURL(url);
                //imageView.setImageBitmap(getBitmapFromURL(url));
                Message message = Message.obtain();
                message.obj = bitmap;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    public Bitmap getBitmapFromURL(String urlStr){
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
//            Thread.sleep(1000);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (InterruptedException e) {
            e.printStackTrace();
        }*/ finally {
            try {
                if(is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void loadImages(int start,int end){
        for (int i = start; i < end; i++) {
            String url = NewsAdapter.URLS[i];
            Bitmap bitmap = getBitmapFromCache(url);
            if(bitmap == null) {
                ImageAsyncTask task = new ImageAsyncTask(url);
                task.execute(url);
                mTask.add(task);
                //new ImageAsyncTask(mImageView, url).execute(url);
            }else{
                ImageView imageView  = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void showImageByAsyncTask(ImageView imageView,String url){
       /* mImageView = imageView;
        mUrl = url;*/
        Bitmap bitmap = getBitmapFromCache(url);
        if(bitmap == null) {
//            new ImageAsyncTask( url).execute(url);
            imageView.setImageResource(R.mipmap.ic_launcher);
        }else{
            imageView.setImageBitmap(bitmap);
        }
    }

    public void cancelAllTask() {
        if(mTask != null) {
            for(ImageAsyncTask task : mTask){
                task.cancel(false);
            }
        }
    }

    private class ImageAsyncTask extends AsyncTask<String,Void,Bitmap>{
//        private ImageView imageView;
        private String mUrl;
        public ImageAsyncTask( String url){
//            this.imageView = (ImageView) mListView.findViewWithTag(url);
            mUrl = url;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromURL(url);
            if(bitmap != null){
                addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
            if(imageView.getTag().equals(mUrl)) {
                imageView.setImageBitmap(bitmap);
            }
            mTask.remove(this);
        }
    }
}
