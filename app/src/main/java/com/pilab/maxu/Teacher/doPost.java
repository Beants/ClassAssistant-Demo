package com.pilab.maxu.Teacher;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by andersen on 2018/11/1.
 */

public class doPost {

    public interface PostCallBack {
        void getDataSuccess(String result);

        void getDataFailed(Exception e);
    }

    void doPost(String path, String data, PostCallBack postCallBack) {
        try {
            Log.i(TAG, "doPost: " + path);
            Log.i(TAG, "doPost: " + data);
// 2016011390
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//1这里设置请求方式要写为大写
            conn.setRequestMethod("POST");
//设置响应时长
            conn.setConnectTimeout(5000);
//2设置http请求数据的类型为表单类型
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
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
                Log.i(TAG, "doPost: " + result);
                postCallBack.getDataSuccess(result);
            } else {
                postCallBack.getDataFailed(new Exception("resultCode=" + code));
            }
        } catch (Exception e) {
            postCallBack.getDataFailed(e);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    String dopost(String path, String data) throws IOException {


        Log.i(TAG, "doLogin: " + path);
// 2016011390
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//1这里设置请求方式要写为大写
        conn.setRequestMethod("POST");
//设置响应时长
        conn.setConnectTimeout(5000);
//2设置http请求数据的类型为表单类型
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
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

        } else {

        }
        return result;
    }
}
