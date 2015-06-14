package cn.beijing.zukai.imagetest.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by zukai on 2015/06/12.
 */
public class ImageUtil {
    /**
     * @param bitmap
     * @param hue        色相
     * @param saturation 饱和度
     * @param scale      亮度
     * @return
     */
    public static Bitmap handleImageEffect(Bitmap bitmap, float hue, float saturation, float scale) {
        Bitmap bm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix scaleMatrix = new ColorMatrix();
        scaleMatrix.setScale(scale, scale, scale, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(scaleMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bm;
    }

    /**
     * 底片效果
     * @param bitmap
     * @return
     */
    public static Bitmap handleImageNegative(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] oldPixes = new int[width*height];
        int color;
        int r,g,b,a;
        Bitmap bm = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        bitmap.getPixels(oldPixes, 0, width, 0, 0, width, height);
        int[] newPixes = new int[width*height];
        for (int i = 0; i < width * height; i++) {
            color = oldPixes[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);
            r = 255 - r;
            g = 255 - g;
            b = 255 - b;
//            a = 255 - a;
            if(r > 255) r = 255;
            if(r < 0) r = 0;
            if(g > 255) g = 255;
            if(g < 0) g = 0;
            if(b > 255) b = 255;
            if(b < 0) b = 0;
//            if(a > 255) a = 255;
//            if(a < 0) a = 0;

            newPixes[i] = Color.argb(a,r,g,b);
        }
        bm.setPixels(newPixes, 0, width, 0, 0, width, height);
        return bm;
    }

    /**
     * 老照片
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixesOldPhote(Bitmap bm){
        Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0;
        int r,g,b,a,r1,g1,b1;
        int oldPixes[] = new int[width*height];
        int newPixes[] = new int[width*height];
        bm.getPixels(oldPixes, 0, width, 0, 0, width, height);
        for (int i = 0; i < width * height; i++) {
            color = oldPixes[i];
            a = Color.alpha(color);
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);

            r1 = (int)(0.393 * r + 0.769 * g + 0.189 * b);
            g1 = (int)(0.349 * r + 0.686 * g + 0.168 * b);
            b1 = (int)(0.272 * r + 0.534 * g + 0.131 * b);

            if(r1 > 255) r1 = 255;
            if(r1 < 0) r1 = 0;
            if(g1 > 255) g1 = 255;
            if(g1 < 0) g1 = 0;
            if(b1 > 255) b1 = 255;
            if(b1 < 0) b1 = 0;

            newPixes[i] = Color.argb(a,r1,g1,b1);
        }
        bitmap.setPixels(newPixes,0,width,0,0,width,height);
        return bitmap;
    }

    /**
     * 浮雕效果
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixesRelief(Bitmap bm){
        Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0,colorBefore = 0;
        int r,g,b,a,r1,g1,b1;
        int oldPixes[] = new int[width*height];
        int newPixes[] = new int[width*height];
        bm.getPixels(oldPixes, 0, width, 0, 0, width, height);
        for (int i = 1; i < width * height; i++) {
            colorBefore = oldPixes[i - 1];
            a = Color.alpha(colorBefore);
            r = Color.red(colorBefore);
            g = Color.green(colorBefore);
            b = Color.blue(colorBefore);

            color = oldPixes[i];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);
            r = (r - r1 + 127);
            g = (g - g1 + 127);
            b = (b - b1 + 127);
            if(r > 255) r = 255;
            if(r < 0) r = 0;
            if(g > 255) g = 255;
            if(g < 0) g = 0;
            if(b > 255) b = 255;
            if(1 < 0) b = 0;
            newPixes[i] = Color.argb(a,r,g,b);
        }
        bitmap.setPixels(newPixes,0,width,0,0,width,height);
        return bitmap;
    }
}
