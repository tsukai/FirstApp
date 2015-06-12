package cn.beijing.zukai.imagetest.color;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import cn.beijing.zukai.imagetest.R;

/**
 * Created by zukai on 2015/06/12.
 */
public class MatrixColor extends Activity {
    private ImageView mImageView;
    private GridLayout mGroup;
    private Bitmap bitmap;
    private int mEtWidth, mEtHeight;
    private EditText[] mEts = new EditText[20];
    private float[] mColorMatrix = new float[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_color);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fedora2);
        mImageView = (ImageView) findViewById(R.id.iv_image1);
        mGroup = (GridLayout) findViewById(R.id.group);
        mImageView.setImageBitmap(bitmap);

        mGroup.post(new Runnable() {
            @Override
            public void run() {
                mEtWidth = mGroup.getWidth() / 5;
                mEtHeight = mGroup.getHeight() / 4;
                addEts();
                initMatrix();
            }
        });
    }

    private void addEts() {
        for (int i = 0; i < 20; i++) {
            EditText et = new EditText(MatrixColor.this);
            mEts[i] = et;
            mGroup.addView(et, mEtWidth, mEtHeight);
        }
    }

    private void initMatrix() {
        for (int i = 0; i < 20; i++) {
            if (i % 6 == 0) {
                //mColorMatrix[i] = 1;
                mEts[i].setText(String.valueOf(1));
            } else {
                //mColorMatrix[i] = 0;
                mEts[i].setText(String.valueOf(0));
            }
        }
    }

    private void getMatrix() {
        for (int i = 0; i < 20; i++) {
            mColorMatrix[i] = Float.valueOf(mEts[i].getText().toString());
        }
    }

    public void setImageMatix() {
        Bitmap bm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(mColorMatrix);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        mImageView.setImageBitmap(bm);

    }

    public void btnChange(View view) {
        getMatrix();
        setImageMatix();
    }

    public void btnRevert(View view) {
        initMatrix();
        getMatrix();
        setImageMatix();
    }

}
