package com.ubtech.gallery_lib.data;

import android.content.Context;

import com.ubtech.gallery_lib.bean.MediaBean;

import java.util.List;

/**
 * Created by lei on 2020/7/16
 * desc:
 */
public interface IMediaProduce {
    List<MediaBean> getImageList(Context context, String path);

    List<MediaBean> getVideoList(Context context, String path);

    List<MediaBean> getAllMedia(Context context, String path);


    List<MediaBean> getImageList(Context context, String bucketId, int page, int limit);

    List<MediaBean> getVideoList(Context context, String bucketId, int page, int limit);

    List<MediaBean> getAllMedia(Context context, String bucketId, int page, int limit);

}
