package cn.beijing.zukai.imagetest2.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import cn.beijing.zukai.imagetest2.R;

/**
 * Created by zukai on 2015/06/14.
 */
public class ImageMatrixTest extends View {
    private Bitmap bitmap;
    private Matrix matrix;
    public ImageMatrixTest(Context context) {
        super(context);
    }

    public ImageMatrixTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageMatrixTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView(){
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        
    }
}
