package com.example.administrator.jkbd.biz;

import com.example.administrator.jkbd.bean.Question;

/**
 * Created by wind on 2017/7/1.
 */

public interface IExamBiz {
    void beginExam();
    Question getQuestion();
    void nextQuestion();
    void preQuestion();
    void commitExam();
}
