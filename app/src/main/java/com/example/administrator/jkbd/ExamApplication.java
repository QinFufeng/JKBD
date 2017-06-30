package com.example.administrator.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.administrator.jkbd.bean.Examination;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.utils.OkHttpUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication  extends Application{
    Examination examination;
    List<Question> me;
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
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public List<Question> getMe() {
        return me;
    }

    public void setMe(List<Question> me) {
        this.me = me;
    }
}
