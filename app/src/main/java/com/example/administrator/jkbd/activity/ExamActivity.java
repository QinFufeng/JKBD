package com.example.administrator.jkbd.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Examination;
import com.example.administrator.jkbd.bean.Question;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tExamination,tvTitle,tvop1,tvop2,tvop3,tvop4;
    ImageView im;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initData();
    }

    private void initView() {
        tExamination=(TextView) findViewById(R.id.exam_t);
        tvTitle=(TextView) findViewById(R.id.tv_title);
        tvop1=(TextView) findViewById(R.id.tv_op1);
        tvop2=(TextView) findViewById(R.id.tv_op2);
        tvop3=(TextView) findViewById(R.id.tv_op3);
        tvop4=(TextView) findViewById(R.id.tv_op4);
        im=(ImageView) findViewById(R.id.tv_im);
    }

    private void initData() {
        Examination examination=ExamApplication.getInstance().getExamination();
        if(examination!=null){
            showData(examination);
        }
        List<Question> mexamList = ExamApplication.getInstance().getMexamList();
        if(mexamList!=null){
            showExam(mexamList);
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
}
