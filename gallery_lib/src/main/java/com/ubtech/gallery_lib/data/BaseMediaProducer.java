package com.ubtech.gallery_lib.data;

import android.content.Context;

import com.ubtech.gallery_lib.bean.MediaBean;
import com.ubtech.gallery_lib.util.FilenameUtils;
import com.ubtech.gallery_lib.util.StorageUtils;

import java.io.File;
import java.util.List;

/**
 * Created by lei on 2020/7/16
 * desc:
 */
public class BaseMediaProducer implements IMediaProduce {
    @Override
    public List<MediaBean> getImageList(Context context, String path) {
        return null;
    }

    @Override
    public List<MediaBean> getVideoList(Context context, String path) {
        return null;
    }

    @Override
    public List<MediaBean> getAllMedia(Context context, String path) {
        return null;
    }

    @Override
    public List<MediaBean> getImageList(Context context, String bucketId, int page, int limit) {
        return null;
    }

    @Override
    public List<MediaBean> getVideoList(Context context, String bucketId, int page, int limit) {
        return null;
    }

    @Override
    public List<MediaBean> getAllMedia(Context context, String bucketId, int page, int limit) {
        return null;
    }

    protected File createThumbnailBigFileName(Context context, String originalPath) {
        File storeFile = StorageUtils.getCacheDirectory(context);
        return new File(storeFile, "big_" + FilenameUtils.getName(originalPath));
    }

    protected File createThumbnailSmallFileName(Context context, String originalPath) {
        File storeFile = StorageUtils.getCacheDirectory(context);
        return new File(storeFile, "small_" + FilenameUtils.getName(originalPath));
    }
}
