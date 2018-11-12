package com.pilab.maxu.Teacher;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.pilab.maxu.R;

public class TeacherActivity extends AppCompatActivity {

    private static final String TAG = "public class TeacherActivity extends AppCompatActivity :";
    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem ti_teacher_info;
    TabItem ti_teacher_addclass;
    TabItem ti_teacher_addsign;
    TabItem ti_teacher_history;
    public String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
//        bundle.putString("username", "2016011390");
        //TODO:一会恢复下面的代码
        bundle = intent.getExtras();
        if (bundle != null) {
            username = bundle.getString("username");
            Log.i(TAG, "onCreate: username" + username);
        }

        final Toolbar toolbar = findViewById(R.id.toolbar_teather);
        setSupportActionBar(toolbar);


        tabLayout = findViewById(R.id.tl_teacher);
        viewPager = findViewById(R.id.vp_teacher);
        ti_teacher_info = findViewById(R.id.ti_teacher_info);
        ti_teacher_addclass = findViewById(R.id.ti_teacher_addclass);
        ti_teacher_addsign = findViewById(R.id.ti_teacher_addsign);
        ti_teacher_history = findViewById(R.id.ti_teacher_history);


        //设置TabLayout的模式
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //设置viewPage
        teacherPageAdapter pageAdapter = new teacherPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(pageAdapter);
        //关联ViewPager和TabLayout


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(TeacherActivity.this,
                            R.color.colorAccent));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(TeacherActivity.this,
                            R.color.colorAccent));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(TeacherActivity.this,
                                R.color.colorAccent));
                    }
                } else if (tab.getPosition() == 2) {

                    toolbar.setBackgroundColor(ContextCompat.getColor(TeacherActivity.this,
                            android.R.color.darker_gray));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(TeacherActivity.this,
                            android.R.color.darker_gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(TeacherActivity.this,
                                android.R.color.darker_gray));
                    }
                } else {
                    toolbar.setBackgroundColor(ContextCompat.getColor(TeacherActivity.this,
                            R.color.colorPrimary));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(TeacherActivity.this,
                            R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(TeacherActivity.this,
                                R.color.colorPrimaryDark));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }
}
