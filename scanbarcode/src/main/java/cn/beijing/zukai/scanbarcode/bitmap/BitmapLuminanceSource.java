package cn.beijing.zukai.scanbarcode.bitmap;

import android.graphics.Bitmap;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by zukai on 2015/06/17.
 */
public class BitmapLuminanceSource extends LuminanceSource {
    private byte[] bitmapPixels;

    protected BitmapLuminanceSource(Bitmap bitmap) {
        super(bitmap.getWidth(), bitmap.getHeight());
        int data[] = new int[bitmap.getWidth()*bitmap.getHeight()];
        this.bitmapPixels = new byte[bitmap.getWidth()*bitmap.getHeight()];
        bitmap.getPixels(data,0,getWidth(),0,0,getWidth(),getHeight());
        for (int i = 0; i < data.length; i++) {
            this.bitmapPixels[i] = (byte)data[i];
        }
    }

    @Override
    public byte[] getRow(int i, byte[] bytes) {
        System.arraycopy(bitmapPixels, i * getWidth(), bytes, 0, getWidth());
        return bytes;
    }

    @Override
    public byte[] getMatrix() {
        return bitmapPixels;
    }

    static public String getResult(Bitmap bitmap){
        MultiFormatReader formatReader = new MultiFormatReader();
        LuminanceSource source = new BitmapLuminanceSource(bitmap);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap  binaryBitmap = new BinaryBitmap(binarizer);
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        Result result = null;
        try {
            result = formatReader.decode(binaryBitmap,  hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        if(result == null){
            return "empty";
        }
        return result.toString();
    }
}
