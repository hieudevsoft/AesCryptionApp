package com.devapp.aesencryptionjava.model;

import java.io.Serializable;

public class Result implements Serializable {
    private String data;
    private String time;

    public Result(String data, String time) {
        this.data = data;
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
