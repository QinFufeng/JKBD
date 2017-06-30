package com.example.administrator.jkbd.bean;

import java.util.List;

/**
 * Created by wind on 2017/6/29.
 */

public class Result {
    /**
     * error_code : 0
     * reason : ok
     * result : [{"id":1,"question":"这个标志是何含义？","answer":"4","item1":"小型车车道","item2":"小型车专用车道","item3":"多乘员车辆专用车道","item4":"机动车车道","explains":"此为机动车车道，比多乘员车辆专用车道少俩人。","url":"http://images.juheapi.com/jztk/c1c2subject1/1.jpg"}]
     */

    private int error_code;
    private String reason;
    private List<Question> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Question> getResult() {
        return result;
    }

    public void setResult(List<Question> result) {
        this.result = result;
    }


}
