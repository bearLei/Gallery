package com.ubtech.gallery_lib.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ubtech.gallery_lib.R;
import com.ubtech.gallery_lib.bean.MediaBean;
import com.ubtech.gallery_lib.rxjob.Job;
import com.ubtech.gallery_lib.rxjob.RxJob;
import com.ubtech.gallery_lib.rxjob.job.ImageThmbnailJobCreate;

import java.io.File;

/**
 * Created by lei on 2020/7/17
 * desc:
 */
public class GalleryViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View view;
    private ImageView icon;

    public GalleryViewHolder( Context context,@NonNull View itemView) {
        super(itemView);
        this.context = context;
        this.view = itemView;
        icon = view.findViewById(R.id.icon);
    }

    public void setData(MediaBean bean, int position){
        if (null != bean){
            Log.d("leix","当前媒体资源："+bean.toString());
            String thumbnailBigPath = bean.getThumbnailBigPath();
            String thumbnailSmallPath = bean.getThumbnailSmallPath();
            if (!new File(thumbnailBigPath).exists() || !new File(thumbnailSmallPath).exists()){
                Job job = new ImageThmbnailJobCreate(context,bean).create();
                RxJob.getDefault().addJob(job);
            }
            Glide.with(context).load(bean.getOriginalPath()).into(icon);
        }
    }
}
