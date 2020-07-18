package com.ubtech.gallery_lib.rxjob.job;

import android.content.Context;

import com.ubtech.gallery_lib.bean.MediaBean;
import com.ubtech.gallery_lib.rxjob.Job;
import com.ubtech.gallery_lib.rxjob.JobProducer;


public class ImageThmbnailJobCreate implements JobProducer {

    private final MediaBean mediaBean;
    private final Context context;

    public ImageThmbnailJobCreate(Context context, MediaBean mediaBean) {
        this.context = context;
        this.mediaBean = mediaBean;
    }

    @Override
    public Job create() {
        Job.JobParams params = new Job.JobParams(mediaBean.getOriginalPath(), mediaBean);
        return new ImageThmbnailJob(context, params);
    }


}
