package com.example.snkey_okhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OkhttpActivity";
    private Button btn_get, btn_post;
    private TextView tv_result;
    private OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        initView();
    }

    private void initView() {
        btn_get = findViewById(R.id.btn_get);
        btn_post = findViewById(R.id.btn_post);
        tv_result = findViewById(R.id.tv_result);
        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                get(API.GET);
                break;
            case R.id.btn_post:
                post(API.POST);
                break;
        }

    }

    /**
     * 异步get请求
     *
     * @param url
     */
    private void get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText(result);
                    }
                });

            }
        });
    }

    /**
     * 提交表单
     *
     * @param url
     */
    private void post(String url) {
        RequestBody requestBody = new FormBody.Builder()
                .add("age", "32")
                .add("email", "jurassic@gmail.com")
                .add("name", "Jurassic Park")
                .add("id", "5")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                final String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_result.setText(result);
                    }
                });

            }
        });

    }
}
