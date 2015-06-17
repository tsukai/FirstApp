package cn.beijing.zukai.scanbarcode.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.beijing.zukai.scanbarcode.bitmap.BitmapLuminanceSource;

/**
 * Created by zukai on 2015/06/17.
 */
public class CameraManage implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Activity activity;
    SurfaceView surfaceView;
    private ImageView imageView;
    private Timer timer;
    private TimerTask timerTask;
    private Camera.AutoFocusCallback mAutoFocusCallback;
    private Camera.PreviewCallback previewCallback;

    public CameraManage(Activity ac,ImageView iv,SurfaceView sv){
        activity = ac;
        imageView = iv;
        surfaceView = sv;
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        autoFocusSet();
        timer = new Timer();
        timerTask = new CameraTimerTask();
        timer.schedule(timerTask,0,500);
    }

    public void autoFocusSet() {
        mAutoFocusCallback = new Camera.AutoFocusCallback(){

            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if(success){
                    camera.setOneShotPreviewCallback(previewCallback);
                }
            }
        };

        previewCallback = new Camera.PreviewCallback(){

            @Override
            public void onPreviewFrame(byte[] data, Camera arg1) {
                if(data != null){
                    Camera.Parameters params = camera.getParameters();
                    int imageFormat = params.getPreviewFormat();
                    Log.i("map","Image Format: "+imageFormat);
                    Log.i("CameraPreviewCallback","data length: "+data.length);
                    if(imageFormat == ImageFormat.NV21){
                        Bitmap image = null;
                        int w = params.getPreviewSize().width;
                        int h = params.getPreviewSize().height;
                        Rect rect = new Rect(0,0,w,h);
                        YuvImage img = new YuvImage(data,ImageFormat.NV21,w,h,null);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        if(img.compressToJpeg(rect,100,baos)){
                            image = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
                            image = adjusstPhotoRotation(image, 90);
                            imageView.setImageBitmap(image);
                            Drawable d = imageView.getDrawable();
                            BitmapDrawable bd = (BitmapDrawable)d;
                            Bitmap bm = bd.getBitmap();
                            String str = BitmapLuminanceSource.getResult(bm);
                            if(!str.equals("empty")){
                                Toast.makeText(activity.getApplicationContext(),str,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        };
    }

    private Bitmap adjusstPhotoRotation(Bitmap image, final int i) {
        Matrix mx = new Matrix();
        mx.setRotate(i,(float)image.getWidth() / 2,(float) image.getHeight() / 2 );
        try {
            Bitmap bm = Bitmap.createBitmap(image,0,0,image.getWidth(),image.getHeight());
            return bm;
        }catch (OutOfMemoryError e){
        }
        return null;
    }

    class CameraTimerTask extends  TimerTask{

        @Override
        public void run() {
            if(camera != null){
                camera.autoFocus(mAutoFocusCallback);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initCamera(holder);
    }

    private void initCamera(SurfaceHolder holder) {
        camera = Camera.open();
        if(camera == null){
            return;
        }
        Camera.Parameters params = camera.getParameters();
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        params.setPreviewSize(display.getWidth(),display.getHeight());
        camera.setParameters(params);
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.setDisplayOrientation(90);
        camera.startPreview();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        previewCallback = null;
        mAutoFocusCallback = null;
    }
}
