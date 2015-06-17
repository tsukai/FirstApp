package cn.beijing.zukai.scanbarcode;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.ImageView;

import cn.beijing.zukai.scanbarcode.camera.CameraManage;


public class MainActivity extends Activity {
    CameraManage cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cm = new CameraManage(this, (ImageView) findViewById(R.id.imageView),
                (SurfaceView) findViewById(R.id.preview_view));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
