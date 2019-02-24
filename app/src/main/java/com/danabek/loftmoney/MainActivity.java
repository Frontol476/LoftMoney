package com.danabek.loftmoney;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    private static final String TAG = "MainActivity";

    private ViewPager pager;
    private TabLayout tabs;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "OnCreate");

        MainPagesAdapter adapter = new MainPagesAdapter(getSupportFragmentManager(), this);
        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        pager.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                for (Fragment fragment : fragments) {
                    if (fragment instanceof ItemsFragment && fragment.getUserVisibleHint()) {
                        ((ItemsFragment) fragment).onFabClick();

                    }
                }
            }
        });

        pager.addOnPageChangeListener(new PageChangeListener());
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        actionMode = mode;
        fab.hide();
        tabs.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_grey_blue));
    }

    @Override
    public void onSupportActionModeFinished(@NonNull androidx.appcompat.view.ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        actionMode = null;
        fab.show();
        tabs.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "OnDestroy");
    }

    private class PageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            if (actionMode != null) {
                actionMode.finish();
            }
            switch (position) {
                case MainPagesAdapter.PAGE_INCOMES:
                case MainPagesAdapter.PAGE_EXPENSES:
                    fab.show();
                    break;
                case MainPagesAdapter.PAGE_BALANCE:
                    fab.hide();
                    break;
            }

        }
    }

}
