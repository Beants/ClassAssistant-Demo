package com.pilab.maxu.Teacher;

import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
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
import java.util.Base64;
import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherInfoFragment extends Fragment {
    TextView tv_info_username;
    EditText et_info_password_old;
    EditText et_info_password_new;
    Button bt_changePassword;
    private String username;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_info, container, false);
        // 关联view
        tv_info_username = view.findViewById(R.id.tv_info_username);
        et_info_password_new = view.findViewById(R.id.et_info_password_new);
        et_info_password_old = view.findViewById(R.id.et_info_password_old);
        bt_changePassword = view.findViewById(R.id.bt_changePassword);
        // 获取username
        username = "2016011390";
        //TODO：测试 一会恢复下面两行代码
//        Bundle bundle = getActivity().getIntent().getExtras();
//        username = bundle.getString("username");
        Log.i(TAG, "onCreateView: " + username);
        tv_info_username.setText(username);

//        setHasOptionsMenu(true);
        initButton();


        return view;

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_calls, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_call) {
//            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
//                    .show();
//        }
//        return true;
//    }

    void initButton() {
        bt_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final doPost doPost = new doPost();
                final String[] result = {""};

                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        try {
                            String password_old = Base64.getEncoder().encodeToString(String.valueOf(et_info_password_old.getText()).getBytes("utf-8"));
                            String password_new = Base64.getEncoder().encodeToString(String.valueOf(et_info_password_new.getText()).getBytes("utf-8"));
                            doPost.doPost("http://139.199.20.151:5000/" + "change_password", "username=" + URLEncoder.encode(username, "utf-8") + "&password=" + URLEncoder.encode(password_old, "utf-8") + "&newpassword=" + URLEncoder.encode(password_new, "utf-8"), new doPost.PostCallBack() {
                                @Override
                                public void getDataSuccess(final String result) {
                                    Log.i(TAG, "run: result" + result);
                                    new Thread(new Runnable() {
                                        public void run() {
                                            final Toast toast;
                                            Log.i(TAG, "run: result" + result);
                                            Looper.prepare();
                                            if (Objects.equals(result, "1")) {
                                                toast = Toast.makeText(getActivity(), "修改密码成功! \n" + result, Toast.LENGTH_SHORT);
                                            } else {
                                                toast = Toast.makeText(getActivity(), "修改密码失败，请检查输入！\n", Toast.LENGTH_SHORT);
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
                                            Looper.prepare();
                                            final Toast toast = Toast.makeText(getActivity(), "修改密码失败，请检查输入！\n" + e, Toast.LENGTH_SHORT);
                                            toast.show();
                                            Looper.loop();
                                        }
                                    }).start();

                                }
                            });
                            et_info_password_new.setText("");
                            et_info_password_old.setText("");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }
}
