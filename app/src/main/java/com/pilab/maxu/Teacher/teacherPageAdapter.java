package com.pilab.maxu.Teacher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class teacherPageAdapter extends FragmentPagerAdapter {
    private static final String TAG = "hahhahahhahhah";
    private int numOfTabs;
    private List<String> list_title = new ArrayList<>();

    {
        list_title.add("个人信息");
        list_title.add("增加班级");
        list_title.add("增加签到");
        list_title.add("签到历史");

    }


    teacherPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_title.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.i(TAG, "getItem: " + "case 0");
                return new TeacherInfoFragment();
            case 1:
                Log.i(TAG, "getItem: " + "case 1");

                return new AddClassFragment();
            case 2:
                Log.i(TAG, "getItem: " + "case 2");

                return new AddSignFragment();
            case 3:
                Log.i(TAG, "getItem: " + "case 3");

                return new SignHistoryFragment();
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return numOfTabs;
    }
}
