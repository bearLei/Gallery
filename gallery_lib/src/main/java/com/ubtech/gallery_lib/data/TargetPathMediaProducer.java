package com.ubtech.gallery_lib.data;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import com.ubtech.gallery_lib.Constant;
import com.ubtech.gallery_lib.bean.MediaBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2020/7/16
 * desc: 获取指定目录下的媒体资源
 */
public class TargetPathMediaProducer extends BaseMediaProducer {
    private static final String TAG = "TargetPathMediaProducer";
    private MediaPlayer mediaPlayer;

    @Override
    public List<MediaBean> getImageList(Context context, String path) {
        File file = verifyFileSuccess(path);
        if (null == file) {
            return null;
        }
        File[] fileList = filterImage(file);
        return produce(context, fileList);
    }

    @Override
    public List<MediaBean> getVideoList(Context context, String path) {
        File file = verifyFileSuccess(path);
        if (null == file) {
            return null;
        }
        File[] fileList = filterVideo(file);
        return produce(context, fileList);
    }

    @Override
    public List<MediaBean> getAllMedia(Context context, String path) {
        File file = verifyFileSuccess(path);
        if (null == file) {
            return null;
        }
        File[] fileList = filterMedia(file);
        return produce(context, fileList);
    }

    private File verifyFileSuccess(String path) {
        if (TextUtils.isEmpty(path)) {
            Log.d(TAG, "路径不能为空");
            return null;
        }
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) return null;
        return file;
    }

    private List<MediaBean> produce(Context context, File[] fileList) {
        List<MediaBean> mediaBeanList = new ArrayList<>();
        for (File file : fileList) {
            MediaBean mediaBean = new MediaBean();
            mediaBean.setOriginalPath(file.getAbsolutePath());
            mediaBean.setCreateDate(file.lastModified());
            mediaBean.setThumbnailBigPath(createThumbnailBigFileName(context, file.getAbsolutePath()).getAbsolutePath());
            mediaBean.setThumbnailSmallPath(createThumbnailSmallFileName(context, file.getAbsolutePath()).getAbsolutePath());
            if (isImage(file)) {
                mediaBean.setMimeType(Constant.MEDIA_TYPE_IMG);
            } else if (isVideo(file)) {
                mediaBean.setMimeType(Constant.MEDIA_TYPE_VIDEO);
                mediaBean.setDuration(getDuration(file));

            }
            mediaBeanList.add(mediaBean);
        }
        return mediaBeanList;
    }


    private File[] filterImage(File fileDir) {
        if (fileDir == null) return null;
        File[] files = fileDir.listFiles((dir, name) -> {
            if (TextUtils.isEmpty(name)) return false;
            if (name.endsWith(".png")
                    || name.endsWith(".jpg")
                    || name.endsWith(".PNG")
                    || name.endsWith(".JPG")
            ) {
                return true;
            }
            return false;
        });
        return files;
    }

    private File[] filterVideo(File fileDir) {
        if (fileDir == null) return null;
        File[] files = fileDir.listFiles((dir, name) -> {
            if (TextUtils.isEmpty(name)) return false;
            if (name.endsWith(".mp4")
                    || name.endsWith(".MP4")
            ) {
                return true;
            }
            return false;
        });
        return files;
    }

    private File[] filterMedia(File fileDir) {
        if (fileDir == null) return null;
        File[] files = fileDir.listFiles((dir, name) -> {
            if (TextUtils.isEmpty(name)) return false;
            if (name.endsWith(".mp4")
                    || name.endsWith(".MP4")
                    || name.endsWith(".png")
                    || name.endsWith(".jpg")
                    || name.endsWith(".PNG")
                    || name.endsWith(".JPG")
            ) {
                return true;
            }
            return false;
        });
        return files;
    }

    private boolean isVideo(File file) {
        String absolutePath = file.getAbsolutePath();
        return absolutePath.endsWith(".mp4") || absolutePath.endsWith(".MP4");
    }

    private boolean isImage(File file) {
        String absolutePath = file.getAbsolutePath();
        return absolutePath.endsWith(".png") || absolutePath.endsWith(".PNG") || absolutePath.endsWith(".jpg") || absolutePath.endsWith(".JPG");
    }

    private long getDuration(File file) {
        long duration = 0;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            duration = mediaPlayer.getDuration();
            Log.d(TAG, "路径：--->" + file.getAbsolutePath() + "\n时长：--->" + duration);
        } catch (IOException e) {
            Log.d(TAG, "异常："+e.getMessage());
            e.printStackTrace();
        }
        return duration;
    }

}
