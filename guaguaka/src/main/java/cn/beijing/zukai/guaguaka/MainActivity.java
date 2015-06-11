package cn.beijing.zukai.guaguaka;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cn.beijing.zukai.guaguaka.view.Guaguaka;


public class MainActivity extends ActionBarActivity {
    Guaguaka guaguaka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guaguaka = (Guaguaka) findViewById(R.id.guaguaka);
        guaguaka.setmListener(new Guaguaka.OnGuaguakaCompleteListener() {
            @Override
            public void complete() {
                Toast.makeText(getApplicationContext(), "用户已经刮得差不多了", Toast.LENGTH_SHORT).show();
            }
        });
        guaguaka.setText("Android");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
