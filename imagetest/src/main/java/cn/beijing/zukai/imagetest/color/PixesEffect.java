package cn.beijing.zukai.imagetest.color;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import cn.beijing.zukai.imagetest.R;
import cn.beijing.zukai.imagetest.util.ImageUtil;

/**
 * Created by zukai on 2015/06/12.
 */
public class PixesEffect extends Activity{
    private ImageView imageView1,imageView2,imageView3,imageView4;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pixex_layout);

        imageView1 = (ImageView) findViewById(R.id.image1);
        imageView2 = (ImageView) findViewById(R.id.image2);
        imageView3 = (ImageView) findViewById(R.id.image3);
        imageView4 = (ImageView) findViewById(R.id.image4);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.test3);
//        bm1 = ImageUtil.handleImageNegative(bitmap);
        imageView1.setImageBitmap(bitmap);
        imageView2.setImageBitmap(ImageUtil.handleImageNegative(bitmap));
        imageView3.setImageBitmap(ImageUtil.handleImagePixesOldPhote(bitmap));
        imageView4.setImageBitmap(ImageUtil.handleImagePixesRelief(bitmap));
    }
}
