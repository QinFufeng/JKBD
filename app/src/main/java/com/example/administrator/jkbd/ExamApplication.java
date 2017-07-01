package com.example.administrator.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.administrator.jkbd.bean.Examination;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.bean.Result;
import com.example.administrator.jkbd.biz.ExamBiz;
import com.example.administrator.jkbd.biz.IExamBiz;
import com.example.administrator.jkbd.utils.OkHttpUtils;
import com.example.administrator.jkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication  extends Application{
    Examination examination;
    List<Question> mexamList;
    private static ExamApplication instance;
    IExamBiz biz;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        biz=new ExamBiz();
        initData();
    }
    public static ExamApplication getInstance(){
        return instance;
    }
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                 biz.beginExam();
            }
        }).start();

    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public List<Question> getMexamList() {
        return mexamList;
    }

    public void setMexamList(List<Question> mexamList) {
        this.mexamList = mexamList;
    }
}
