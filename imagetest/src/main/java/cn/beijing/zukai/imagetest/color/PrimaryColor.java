package cn.beijing.zukai.imagetest.color;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import cn.beijing.zukai.imagetest.R;
import cn.beijing.zukai.imagetest.util.ImageUtil;

/**
 * Created by zukai on 2015/06/12.
 */
public class PrimaryColor extends Activity implements SeekBar.OnSeekBarChangeListener{
    private ImageView mImageView;
    private SeekBar seekHue;
    private SeekBar seekSaturation;
    private SeekBar seekScale;
    private static int MAX_VALUE = 255;
    private static int MID_VALUE = 127;
    private float mHue,mSaturation,mScale;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_color);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.fedora1);

        mImageView = (ImageView) findViewById(R.id.iv_image);
        seekHue = (SeekBar) findViewById(R.id.seek_hue);
        seekSaturation = (SeekBar) findViewById(R.id.seek_saturation);
        seekScale = (SeekBar) findViewById(R.id.seek_scale);

        seekHue.setOnSeekBarChangeListener(this);
        seekSaturation.setOnSeekBarChangeListener(this);
        seekScale.setOnSeekBarChangeListener(this);

        seekHue.setMax(MAX_VALUE);
        seekSaturation.setMax(MAX_VALUE);
        seekScale.setMax(MAX_VALUE);
        seekHue.setProgress(MID_VALUE);
        seekSaturation.setProgress(MID_VALUE);
        seekScale.setProgress(MID_VALUE);

        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.seek_hue :
                mHue = (progress - MID_VALUE) * 1.0F / MID_VALUE * 180;
                break;
            case R.id.seek_saturation :
                mSaturation = progress * 1.0F / MID_VALUE;
                break;
            case R.id.seek_scale :
                mScale = progress * 1.0F / MID_VALUE;
                break;
        }
        mImageView.setImageBitmap(ImageUtil.handleImageEffect(bitmap,mHue,mSaturation,mScale));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
