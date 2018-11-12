package com.pilab.maxu.Teacher;

import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pilab.maxu.R;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class AddClassFragment extends Fragment {
    EditText et_ac_classname;
    TextView tv_ac_username;
    String username;
    String classname;
    Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_class, container, false);

        et_ac_classname = view.findViewById(R.id.et_ac_classname);
        tv_ac_username = view.findViewById(R.id.tv_ac_username);
        button = view.findViewById(R.id.bt_ac);
        username = "2016011390";
        //TODO：测试 一会恢复下面两行代码
//        Bundle bundle = getActivity().getIntent().getExtras();
//        username = bundle.getString("username");
        tv_ac_username.setText(username);
        initButton();
        return view;
    }

    void initButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final doPost doPost = new doPost();
                classname = String.valueOf(et_ac_classname.getText());
                final String[] result = {""};
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        try {
                            doPost.doPost("http://139.199.20.151:5000/" + "new_class", "classaname=" + URLEncoder.encode(classname, "utf-8") + "&username=" + URLEncoder.encode(username, "utf-8"), new doPost.PostCallBack() {
                                @Override
                                public void getDataSuccess(final String result) {
                                    new Thread(new Runnable() {
                                        public void run() {
                                            final Toast toast;
                                            Log.i(TAG, "run: result" + result);
                                            if (Looper.myLooper() == null) {
                                                Looper.prepare();
                                            }
                                            if (Objects.equals(result, "1")) {
                                                toast = Toast.makeText(getActivity(), "创建班级成功" + result, Toast.LENGTH_SHORT);
                                                et_ac_classname.setText("");

                                            } else {
                                                toast = Toast.makeText(getActivity(), "创建班级失败，请检查输入！\n", Toast.LENGTH_SHORT);
                                                et_ac_classname.setText("");

                                            }
                                            toast.show();
                                            Looper.loop();
                                        }
                                    }).start();
                                }

                                @Override
                                public void getDataFailed(final Exception e) {
                                    new Thread(new Runnable() {
                                        public void run() {
                                            Log.w(TAG, "run: " + e);
                                            if (Looper.myLooper() == null) {
                                                Looper.prepare();
                                            }
                                            final Toast toast = Toast.makeText(getActivity(), "创建班级失败，请检查输入！\n" + e, Toast.LENGTH_SHORT);
                                            toast.show();
                                            et_ac_classname.setText("");

                                            Looper.loop();
                                        }
                                    }).start();

                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
//
    }

}
