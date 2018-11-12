package com.pilab.maxu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Objects;

public class RegActivity extends AppCompatActivity {
    private CheckBox cb_stu;
    private CheckBox cb_teacher;
    private Button bt_real_reg;
    private EditText et_reg_username;
    private EditText et_reg_password;
    private final String TAG = "REG:\t";
    String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        cb_stu = findViewById(R.id.cb_stu);
        cb_teacher = findViewById(R.id.cb_teacher);
        bt_real_reg = findViewById(R.id.bt_real_reg);
        et_reg_username = findViewById(R.id.et_reg_username);
        et_reg_password = findViewById(R.id.et_reg_password);
        initCheckBox();
        initBt();
    }

    void initBt() {
        bt_real_reg.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                final String username = String.valueOf(et_reg_username.getText());
                final String role = String.valueOf(getStateCheckBox());
                String password = null;
                // 获取加密后的密码
                try {
                    password = Base64.getEncoder().encodeToString(String.valueOf(et_reg_password.getText()).getBytes("utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "onClick: " + username + "\t" + password + "\t" + role);

                final String Password = password;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = doReg(username, Password, role);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                if (Objects.equals(result, "0")) {
                    Toast.makeText(getApplicationContext(), "用户名已存在，请检查！", Toast.LENGTH_SHORT).show();
                } else if (Objects.equals(result, "1")) {
                    Intent intent = new Intent(RegActivity.this, LoginActivity.class);
                    startActivity(intent);
                }


            }
        });

    }

    void initCheckBox() {
        cb_stu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_stu.isChecked()) {
                    cb_teacher.setChecked(false);
                } else {
                    cb_teacher.setChecked(true);
                }
            }
        });
        cb_teacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_teacher.isChecked()) {
                    cb_stu.setChecked(false);
                } else {
                    cb_stu.setChecked(true);
                }
            }
        });
    }

    String getStateCheckBox() {
        if (cb_stu.isChecked()) {
            return "stu";
        } else {
            return "teacher";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    String doReg(String username, String password, String role) throws IOException {

        String path = "http://139.199.20.151:5000/do_register";
//        String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"role\":\"" + role + "\"}";
//        Log.i(TAG, "doLogin: " + path);
//        Log.i(TAG, "doReg: " + json);
// 2016011390
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//1这里设置请求方式要写为大写
        conn.setRequestMethod("POST");
//设置响应时长
        conn.setConnectTimeout(5000);
//2设置http请求数据的类型为表单类型
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        String data = "username=" + URLEncoder.encode(username, "utf-8") + "&password=" + URLEncoder.encode(password, "utf-8") + "&role=" + URLEncoder.encode(role, "utf-8");
//3设置给服务器写的数据的长度
        conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
//4指定要给服务器写数据
        conn.setDoOutput(true);
//5开始向服务器写数据
        conn.getOutputStream().write(data.getBytes());
        int code = conn.getResponseCode();
        String result = "";
        if (code == 200) {
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            is.close();

//注意:这里回流的编码默认是"utf-8"的

            result = baos.toString();

        }
        return result;


    }
}
