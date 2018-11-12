package com.pilab.maxu.Teacher;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.pilab.maxu.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class SignHistoryFragment extends Fragment {
    doPost doPost = new doPost();
    String username;
    private List<Sign> mData;
    ListView listView;
    String Result = "1";
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        mData = new LinkedList<>();
        getData();
        while (Objects.equals(Result, "1")) {
            Log.i(TAG, "onStart: SignHistoryFragment 等待信息返回。");
        }
        Log.i(TAG, "getDataSuccess: get_sign " + Result);

        String[] list = Result.split("&");
        Sign sign;
        String[] l;
        Log.i(TAG, "onStart: list len" + list.length);
        for (int i = 0; i < list.length; i++) {
            Log.i(TAG, "onStart: " + list[i]);
            l = list[i].split("10101010");
            Log.i(TAG, "onStart: " + l.length);
            Log.i(TAG, "onStart: sign" + l[0] + " " + l[3] + " " + l[2] + " " + l[1]);

            sign = new Sign(l[0], l[3], l[2], l[1]);
            Log.i(TAG, "onStart: sign" + l[0] + " " + l[3] + " " + l[2] + " " + l[1]);
            Log.i(TAG, "onStart: " + sign.getClassname());
            mData.add(sign);
        }
        Log.i(TAG, "onStart: " + mData.toString());
        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), (LinkedList<Sign>) mData);
        listView.setAdapter(listViewAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_history, container, false);
        textView = view.findViewById(R.id.tv_his_ansyc);
        listView = view.findViewById(R.id.history_listview);
        username = "2016011390";
        Log.i(TAG, "getDataSuccess: " + Result);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                onStart();
                Looper.loop();

            }
        });


        return view;
    }


    void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doPost.doPost("http://139.199.20.151:5000/" + "get_sign", "username=" + URLEncoder.encode(username, "utf-8"), new doPost.PostCallBack() {
                        @Override
                        public void getDataSuccess(String result) {
                            Result = result;
                            Log.i(TAG, "getDataSuccess: get_sign " + Result);


                        }

                        @Override
                        public void getDataFailed(Exception e) {
                            e.printStackTrace();
                            Log.i(TAG, "getDataFailed: get_sign 失败");
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


}
