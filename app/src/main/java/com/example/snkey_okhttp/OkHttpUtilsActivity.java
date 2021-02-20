package com.example.snkey_okhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

public class OkHttpUtilsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OkHttpUtilsActivity";
    private Button btn_get, btn_post, file_upload, file_download;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_utils);

        initView();


    }

    private void initView() {
        btn_get = findViewById(R.id.btn_get);
        btn_post = findViewById(R.id.btn_post);
        file_upload = findViewById(R.id.file_upload);
        file_download = findViewById(R.id.file_download);
        tv_content = findViewById(R.id.tv_content);

        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
        file_upload.setOnClickListener(this);
        file_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                get();
                break;
            case R.id.btn_post:
                post();
                break;
            case R.id.file_upload:
                uplaod();
                break;
            case R.id.file_download:
                download();
                break;
        }
    }

    private void get() {
        OkHttpUtils
                .get()
                .url(API.GET)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //UI线程
                        tv_content.setText(response);


                    }
                });
    }

    private void post() {
        OkHttpUtils
                .post()
                .url(API.POST)
                .addParams("age", "33")
                .addParams("email", "jurassic2@gmail.com")
                .addParams("name", "Jurassic Park2")
                .addParams("id", "6")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG, "onResponse:" + response);
                        //UI线程
                        tv_content.setText(response);
                    }
                });
    }

    private void download() {
        //动态权限申请
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(OkHttpUtilsActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(OkHttpUtilsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {
            OkHttpUtils
                    .get()
                    .url(API.PICTURE_CAT)
                    .build()
                    .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "cat.jpg") {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.d(TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            Log.d(TAG, "onResponse: " + response.toString());
                            tv_content.setText("文件路径:" + response.getAbsolutePath() + "文件名:" + response.getName());
                        }
                    });
        }
    }

    private void uplaod() {
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(OkHttpUtilsActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(OkHttpUtilsActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        } else {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "cat.jpg");
            OkHttpUtils
                    .post()
                    .url(API.ANIMAL_DETECT)
                    .addFile("cat", file.getName(), file)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                        }
                    });
        }
    }
}
