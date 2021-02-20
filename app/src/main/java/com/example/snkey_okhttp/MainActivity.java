package com.example.snkey_okhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button btn_okhttp, btn_okhttp_utils, btn_okhttp_go;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btn_okhttp = findViewById(R.id.btn_okhttp);
        btn_okhttp_utils = findViewById(R.id.btn_okhttp_utils);
        btn_okhttp_go = findViewById(R.id.btn_okhttp_go);

        btn_okhttp.setOnClickListener(this);
        btn_okhttp_utils.setOnClickListener(this);
        btn_okhttp_go.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_okhttp:
                intent = new Intent(this, OkhttpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_okhttp_utils:
                intent = new Intent(this, OkHttpUtilsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_okhttp_go:
                intent = new Intent(this, OkhttpGoActivity.class);
                startActivity(intent);
                break;

        }
    }


}
