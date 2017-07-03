package com.example.administrator.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Examination;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.biz.ExamBiz;
import com.example.administrator.jkbd.biz.IExamBiz;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tExamination,tvTitle,tvop1,tvop2,tvop3,tvop4,tvLoad,tvNo;
    ImageView im;
    IExamBiz biz;
    ProgressBar dialog;
    LinearLayout layoutLoading;
    boolean iE=false;
    boolean iQ=false;

    boolean iER=false;
    boolean iQR=false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast=new LoadExamBroadcast();
        mLoadQuestionBroadcast=new LoadQuestionBroadcast();
        setListener();
        initView();
        biz=new ExamBiz();
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tvLoad.setText("下载数据中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        dialog=(ProgressBar)findViewById(R.id.load_dialog);
        layoutLoading=(LinearLayout)findViewById(R.id.layout_loading);
        tExamination=(TextView) findViewById(R.id.exam_t);
        tvTitle=(TextView) findViewById(R.id.tv_title);
        tvNo= (TextView) findViewById(R.id.tv_exam_no);
        tvop1=(TextView) findViewById(R.id.tv_op1);
        tvop2=(TextView) findViewById(R.id.tv_op2);
        tvop3=(TextView) findViewById(R.id.tv_op3);
        tvop4=(TextView) findViewById(R.id.tv_op4);
        im=(ImageView) findViewById(R.id.tv_im);
        tvLoad=(TextView)findViewById(R.id.tv_load);
        layoutLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void initData() {
        if(iER && iQR){
            if(iE && iQ) {
                layoutLoading.setVisibility(View.GONE);
                Examination examination = ExamApplication.getInstance().getExamination();
                if (examination != null) {
                    showData(examination);
                }
                showExam(biz.getQuestion());
            }
            else {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                 tvLoad.setText("下载失败，点击重新下载");
            }
        }

    }

    private void showExam(Question exam) {
        Log.e("showExam","showExam,exam="+exam);
        if(exam!=null){
            tvNo.setText(biz.getExamIndex());
            tvTitle.setText(exam.getQuestion());
            tvop1.setText(exam.getItem1());
            tvop2.setText(exam.getItem2());
            tvop3.setText(exam.getItem3());
            tvop4.setText(exam.getItem4());
            if(exam.getUrl()!=null && !exam.getUrl().equals("")) {
                im.setVisibility(View.VISIBLE);
                Picasso.with(ExamActivity.this).load(exam.getUrl()).into(im);
            }else{
                im.setVisibility(View.GONE);
            }
        }
    }



    private void showData(Examination examination) {
        tExamination.setText(examination.toString());
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if(mLoadExamBroadcast!=null){
            unregisterReceiver(mLoadExamBroadcast);
        }
        if(mLoadQuestionBroadcast!=null){
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }

    public void preExam(View view) {
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        showExam(biz.nextQuestion());
    }

    class LoadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean i=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("mLoadExamBroadcast","mLoadExamBroadcast,isSuccess="+i);
            if(i){
                iE=true;
            }
            iER=true;
            initData();
        }
    }
    class LoadQuestionBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            boolean i=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("mLoadExamBroadcast","mLoadExamBroadcast,isSuccess="+i);
            if(i){
                iQ=true;
            }
            iQR=true;
            initData();
        }
    }
}
