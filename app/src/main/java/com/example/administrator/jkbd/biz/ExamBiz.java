package com.example.administrator.jkbd.biz;

import com.example.administrator.jkbd.dao.ExamDao;
import com.example.administrator.jkbd.dao.IExamDao;

/**
 * Created by wind on 2017/7/1.
 */

public class ExamBiz implements IExamBiz {
    IExamDao dao;
    public ExamBiz(){
        this.dao=new ExamDao();
    }
    @Override
    public void beginExam() {
        dao.loadExamIofo();
        dao.loadQuestionList();
    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}
