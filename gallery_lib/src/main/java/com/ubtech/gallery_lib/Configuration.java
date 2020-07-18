package com.ubtech.gallery_lib;

import java.io.Serializable;

/**
 * Created by lei on 2020/7/9
 * desc:
 */
public class Configuration implements Serializable {
    private String customLoadFilePath;


    public String getCustomLoadFilePath() {
        return customLoadFilePath;
    }

    public void setCustomLoadFilePath(String customLoadFilePath) {
        this.customLoadFilePath = customLoadFilePath;
    }
}
