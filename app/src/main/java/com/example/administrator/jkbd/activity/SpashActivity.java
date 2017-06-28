package com.example.administrator.jkbd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.jkbd.R;

/**
 * Created by wind on 2017/6/27.
 */

public class SpashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        countDownTimer.start();

    }
    CountDownTimer countDownTimer=new CountDownTimer(3000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent intent=new Intent(SpashActivity.this,MainActivity.class);
            startActivity(intent) ;
            finish();
            }
    };
}
