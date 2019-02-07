package com.danabek.loftmoney;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity","OnCreate");

        MainPagesAdapter adapter = new MainPagesAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","OnDestroy");
    }
}
