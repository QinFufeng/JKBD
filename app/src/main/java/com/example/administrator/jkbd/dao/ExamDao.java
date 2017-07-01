package com.example.administrator.jkbd.dao;

import android.content.Intent;
import android.util.Log;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.bean.Examination;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.bean.Result;
import com.example.administrator.jkbd.utils.OkHttpUtils;
import com.example.administrator.jkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by wind on 2017/7/1.
 */

public class ExamDao implements IExamDao {
    @Override
    public void loadExamIofo() {
        OkHttpUtils<Examination> utils=new OkHttpUtils<>(ExamApplication.getInstance());
        String u="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(u)
                .targetClass(Examination.class)
                .execute(new OkHttpUtils.OnCompleteListener<Examination>() {
                    @Override
                    public void onSuccess(Examination result) {
                        Log.e("main","result="+result);
                        ExamApplication.getInstance().setExamination(result);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                        .putExtra(ExamApplication.LOAD_DATA_SUCCESS,true));
                        //examination=result;
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });
    }

    @Override
    public void loadQuestionList() {
        OkHttpUtils<String> utils2=new OkHttpUtils<>(ExamApplication.getInstance());
        String u2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils2.url(u2)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String jsonStr) {
                        boolean success=false;
                        Result result = ResultUtils.getListResultFromJson(jsonStr);
                        if(result!=null && result.getError_code()==0)
                        {
                            List<Question> list =result.getResult();
                            if(list!=null && list.size()>0){
                                ExamApplication.getInstance().setMexamList(list);

                                //mexamList=list;
                            }
                        }
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,true));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });
    }
}
