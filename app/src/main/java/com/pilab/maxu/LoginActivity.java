package com.pilab.maxu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pilab.maxu.Teacher.TeacherActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Objects;

import okhttp3.MediaType;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private static String TAG = "tag";
    private EditText et_usermane;
    private EditText et_password;
    private Button bt_reg;
    private Button bt_login;
    String result = "00";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_usermane = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        bt_reg = findViewById(R.id.bt_reg);
        bt_login = findViewById(R.id.bt_login);
        initBt();
    }

    void initBt() {
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = doLogin();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                while (Objects.equals(result, "00")) {
                    Log.i(TAG, "onClick: 正在等待登陆信息返回");

                }


                if (Objects.equals(result, "0")) {
                    Toast.makeText(LoginActivity.this, "请检查学号/工号或者密码是否输入正确！", Toast.LENGTH_SHORT).show();
                } else if (Objects.equals(result, "stu")) {
                    Toast.makeText(LoginActivity.this, "您好，**同学！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(LoginActivity.this, StuActivity.class);
                    startActivity(intent);
                } else if (Objects.equals(result, "teacher")) {
                    Toast.makeText(LoginActivity.this, "您好，**老师！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(LoginActivity.this, TeacherActivity.class);
                    intent.putExtra("username", String.valueOf(et_usermane.getText()));

                    startActivity(intent);
                } else {
                    Log.i(TAG, "onClick: result[0]: " + result);
                }

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    String doLogin() throws IOException {
        String username = String.valueOf(et_usermane.getText());
        String password = Base64.getEncoder().encodeToString(String.valueOf(et_password.getText()).getBytes("utf-8"));
        String path = "http://139.199.20.151:5000/" + "do_login";
        String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        Log.i(TAG, "doLogin: " + path);
        Log.i(TAG, "doLogin: " + json);
// 2016011390
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//1这里设置请求方式要写为大写
        conn.setRequestMethod("POST");
//设置响应时长
        conn.setConnectTimeout(5000);
//2设置http请求数据的类型为表单类型
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        String data = "username=" + URLEncoder.encode(username, "utf-8") + "&password=" + URLEncoder.encode(password, "utf-8");
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
        Log.i(TAG, "doLogin: " + result);
        return result;


    }
}





