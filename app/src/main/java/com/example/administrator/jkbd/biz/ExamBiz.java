package com.example.administrator.jkbd.biz;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.dao.ExamDao;
import com.example.administrator.jkbd.dao.IExamDao;

import java.util.List;

/**
 * Created by wind on 2017/7/1.
 */

public class ExamBiz implements IExamBiz {
    IExamDao dao;
    int examIndex=0;
    List<Question> examList=null;
    public ExamBiz(){
        this.dao=new ExamDao();
    }
    @Override
    public void beginExam() {
        examIndex=0;
        dao.loadExamIofo();
        dao.loadQuestionList();
    }

    @Override
    public Question getQuestion() {
        examList=ExamApplication.getInstance().getMexamList();
        if(examList!=null){
            return examList.get(examIndex);
        }
        else
        {
            return null;
        }
    }
    @Override
    public Question getQuestion(int index) {
        examList=ExamApplication.getInstance().getMexamList();
        examIndex=index;
        if(examList!=null){
            return examList.get(examIndex);
        }
        else
        {
            return null;
        }
    }

    @Override
    public Question nextQuestion() {
        if(examList!=null && examIndex<examList.size()-1){
            examIndex++;
            return examList.get(examIndex);
        }
        else
        {
            return null;
        }
    }

    @Override
    public Question preQuestion() {
        if(examList!=null && examIndex>0){
            examIndex--;
            return examList.get(examIndex);
        }
        else
        {
            return null;
        }
    }

    @Override
    public int commitExam() {
        int s=0;
        for (Question exam : examList) {
            String userA=exam.getUserAnswer();
            if(userA!=null && !userA.equals("")){
                if(exam.getAnswer().equals(userA)){
                    s++;
                }
            }
        }
        return s;
    }

    @Override
    public String getExamIndex() {
        return (examIndex+1)+".";
    }
}
