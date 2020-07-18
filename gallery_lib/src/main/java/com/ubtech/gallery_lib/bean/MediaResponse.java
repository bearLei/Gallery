package com.ubtech.gallery_lib.bean;

import java.util.List;

/**
 * Created by lei on 2020/7/17
 * desc:
 */
public class MediaResponse {
    private int code;
    private String msg;
    private List<MediaBean> data;

    public MediaResponse(int code, String msg, List<MediaBean> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MediaBean> getData() {
        return data;
    }

    public void setData(List<MediaBean> data) {
        this.data = data;
    }
}
