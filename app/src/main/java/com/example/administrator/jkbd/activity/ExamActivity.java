package com.example.administrator.jkbd.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Examination;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tExamination;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initData();
    }

    private void initView() {
        tExamination=(TextView) findViewById(R.id.exam_t);
    }

    private void initData() {
        Examination examination=ExamApplication.getInstance().getExamination();
        if(examination!=null){
            showData(examination);
        }
    }

    private void showData(Examination examination) {
        tExamination.setText(examination.toString());
    }
}
