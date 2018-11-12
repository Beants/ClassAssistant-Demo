package com.pilab.maxu.Teacher;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pilab.maxu.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class AddSignFragment extends Fragment {
    Spinner spinner;
    RadioGroup radioGroup;
    // start  代表签到已经开始
    // end    没有签到
    String sign = "start";
    TextView textView;
    String username;
    String classname = "";
    Button bt;
    final doPost doPost = new doPost();
    View view;
    final List[] result_list = {new ArrayList()};


    @Override
    public void onStart() {
        super.onStart();
        getclass(view);
        Log.i(TAG, "onStart: ");
        radioGroup.removeAllViews();
        for (int i = 0; i < result_list[0].size(); i++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText((String) result_list[0].get(i));
            radioGroup.addView(radioButton);
            if (i == 0) {
                radioButton.setChecked(true);
            }
        }
    }


    //start_sign
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_sign, container, false);
        radioGroup = view.findViewById(R.id.radioGroup);
        username = "2016011390";
        textView = view.findViewById(R.id.teacher_sign_code);
        bt = view.findViewById(R.id.sign);

//        getclass(view);
//        while (result_list[0].size() == 0) {
//            Log.i(TAG, "onCreateView: AddSignFragment" + "等待数据返回");
//        }
//        for (int i = 0; i < result_list[0].size(); i++) {
//            RadioButton radioButton = new RadioButton(getContext());
//            radioButton.setText((String) result_list[0].get(i));
//            radioGroup.addView(radioButton);
//            if (i == 0) {
//                radioButton.setChecked(true);
//            }
//        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) view.findViewById(radioGroup.getCheckedRadioButtonId());
                classname = radioButton.getText().toString();
                Log.i(TAG, "onCheckedChanged: 当前选中" + classname);
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(sign, "end") && !Objects.equals(classname, "")) {
                    startSign();
                } else if (Objects.equals(sign, "start") && !Objects.equals(classname, "")) {
                    endSign();

                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getclass(view);
                while (result_list[0].size() == 0) {
                    Log.i(TAG, "onCreateView: " + "等待数据返回");
                }
                radioGroup.removeAllViews();
                for (int i = 0; i < result_list[0].size(); i++) {
                    RadioButton radioButton = new RadioButton(getContext());
                    radioButton.setText((String) result_list[0].get(i));
                    radioGroup.addView(radioButton);
                    if (i == 0) {
                        radioButton.setChecked(true);
                    }
                }
            }
        });


        return view;
    }

    private void startSign() {
        sign = "start";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    doPost.doPost("http://139.199.20.151:5000/" + "start_sign", "classaname=" + URLEncoder.encode(classname, "utf-8"), new doPost.PostCallBack() {
                        @Override
                        public void getDataSuccess(final String result) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(result);
                                    bt.setText("结束签到");
                                }
                            }).start();
                        }

                        @Override
                        public void getDataFailed(Exception e) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    private void endSign() {
        sign = "end";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doPost.doPost("http://139.199.20.151:5000/" + "end_sign", "classaname=" + URLEncoder.encode(classname, "utf-8"), new doPost.PostCallBack() {
                        @Override
                        public void getDataSuccess(final String result) {
                            if (Objects.equals(result, "1")) {
                                new Thread(new Runnable() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void run() {
                                        textView.setText("0000");
                                        bt.setText("开始签到");
                                    }
                                }).start();
                            } else {
                                getDataFailed(new Exception());
                            }
                        }

                        @Override
                        public void getDataFailed(Exception e) {

                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    void getclass(final View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doPost.doPost("http://139.199.20.151:5000/" + "get_class", "username=" + URLEncoder.encode(username, "utf-8"), new doPost.PostCallBack() {
                        @Override
                        public void getDataSuccess(String result) {

                            Log.i(TAG, "getDataSuccess: " + result);
                            result_list[0] = Arrays.asList(result.split(" "));
                            Log.i(TAG, "getDataSuccess: " + result_list[0].size());

                        }

                        @Override
                        public void getDataFailed(Exception e) {
                            Log.i(TAG, "getDataSuccess: " + e);
                            if (Looper.myLooper() == null) {
                                Looper.prepare();
                            }
                            Toast.makeText(getActivity(), "请求失败，请重新加载数据！", Toast.LENGTH_SHORT).show();
                            Looper.loop();


                        }
                    });
                } catch (
                        UnsupportedEncodingException e)

                {
                    e.printStackTrace();
                }


            }
        }).

                start();
    }


}
