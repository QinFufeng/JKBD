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
    TextView tExamination,tvTitle,tvop1,tvop2,tvop3,tvop4,tvLoad;
    ImageView im;
    IExamBiz biz;
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
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        biz=new ExamBiz();
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        layoutLoading=(LinearLayout)findViewById(R.id.layout_loading);
        tExamination=(TextView) findViewById(R.id.exam_t);
        tvTitle=(TextView) findViewById(R.id.tv_title);
        tvop1=(TextView) findViewById(R.id.tv_op1);
        tvop2=(TextView) findViewById(R.id.tv_op2);
        tvop3=(TextView) findViewById(R.id.tv_op3);
        tvop4=(TextView) findViewById(R.id.tv_op4);
        im=(ImageView) findViewById(R.id.tv_im);
        tvLoad=(TextView)findViewById(R.id.tv_load);
    }

    private void initData() {
        if(iER && iQR){
            if(iE && iQ) {
                layoutLoading.setVisibility(View.GONE);
                Examination examination = ExamApplication.getInstance().getExamination();
                if (examination != null) {
                    showData(examination);
                }
                List<Question> mexamList = ExamApplication.getInstance().getMexamList();
                if (mexamList != null) {
                    showExam(mexamList);
                }
            }
            else {
                 tvLoad.setText("下载失败，点击重新下载");
            }
        }

    }

    private void showExam(List<Question> mexamList) {
        Question exam=mexamList.get(0);
        if(exam!=null){
            tvTitle.setText(exam.getQuestion());
            tvop1.setText(exam.getItem1());
            tvop2.setText(exam.getItem2());
            tvop3.setText(exam.getItem3());
            tvop4.setText(exam.getItem4());
            Picasso.with(ExamActivity.this).load(exam.getUrl()).into(im);
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
