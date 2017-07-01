package com.example.administrator.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.administrator.jkbd.bean.Examination;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.bean.Result;
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

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initData();
    }
    public static ExamApplication getInstance(){
        return instance;
    }
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils<Examination> utils=new OkHttpUtils<>(getApplicationContext());
                String u="http://101.251.196.90:8080/JztkServer/examInfo";
                utils.url(u)
                        .targetClass(Examination.class)
                        .execute(new OkHttpUtils.OnCompleteListener<Examination>() {
                            @Override
                            public void onSuccess(Examination result) {
                                Log.e("main","result="+result);
                                examination=result;
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);

                            }
                        });
                OkHttpUtils<String> utils2=new OkHttpUtils<>(instance);
                String u2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils2.url(u2)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String jsonStr) {
                        Result result = ResultUtils.getListResultFromJson(jsonStr);
                        if(result!=null && result.getError_code()==0)
                        {
                            List<Question> list =result.getResult();
                            if(list!=null && list.size()>0){
                                mexamList=list;
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                         Log.e("main","error="+error);
                    }
                });
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
