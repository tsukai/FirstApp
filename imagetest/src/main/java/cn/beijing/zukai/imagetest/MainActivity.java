package cn.beijing.zukai.imagetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.beijing.zukai.imagetest.color.MatrixColor;
import cn.beijing.zukai.imagetest.color.PixesEffect;
import cn.beijing.zukai.imagetest.color.PrimaryColor;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void primary(View view) {
        startActivity(new Intent(this, PrimaryColor.class));
    }

    public void matrix(View view){
        startActivity(new Intent(this, MatrixColor.class));
    }

    public void pixes(View view){
        startActivity(new Intent(this, PixesEffect.class));
    }

}
