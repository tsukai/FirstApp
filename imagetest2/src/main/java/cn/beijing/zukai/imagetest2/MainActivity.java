package cn.beijing.zukai.imagetest2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.beijing.zukai.imagetest2.activaty.ImageMatrixTest;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void matrix(View view){
        startActivity(new Intent(this,ImageMatrixTest.class));
    }
}
