package com.ubtech.gallery_lib.rxjob.job;

import android.content.Context;

import com.ubtech.gallery_lib.bean.MediaBean;
import com.ubtech.gallery_lib.rxjob.Job;
import com.ubtech.gallery_lib.util.BitmapUtils;

import java.io.File;

public class ImageThmbnailJob implements Job {

    private final MediaBean mediaBean;
    public ImageThmbnailJob(Context context, JobParams params) {
        this.mediaBean = (MediaBean) params.getRequestData();
    }

    @Override
    public Result onRunJob() {
        String originalPath = mediaBean.getOriginalPath();
        File bigThumFile = new File(mediaBean.getThumbnailBigPath());
        File smallThumFile = new File(mediaBean.getThumbnailSmallPath());
        if (!bigThumFile.exists()) {
            BitmapUtils.createThumbnailBig(bigThumFile, originalPath);
        }
        if (!smallThumFile.exists()) {
            BitmapUtils.createThumbnailSmall(smallThumFile, originalPath);
        }
        Result result = Result.SUCCESS;
        result.setResultData(mediaBean);
        return result;
    }
}
