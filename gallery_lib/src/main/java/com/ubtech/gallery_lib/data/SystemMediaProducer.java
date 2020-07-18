package com.ubtech.gallery_lib.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.ubtech.gallery_lib.bean.MediaBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2020/7/16
 * desc: 获取系统目录下的媒体资源
 */
public class SystemMediaProducer extends BaseMediaProducer {

    private static final String TAG = "SystemMediaProducer";


    @Override
    public List<MediaBean> getImageList(Context context, String bucketId, int page, int limit) {
        int offset = (page - 1) * limit;
        List<MediaBean> mediaBeanList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        List<String> projection = new ArrayList<>();
        projection.add(MediaStore.Images.Media._ID);
        projection.add(MediaStore.Images.Media.TITLE);
        projection.add(MediaStore.Images.Media.DATA);
        projection.add(MediaStore.Images.Media.BUCKET_ID);
        projection.add(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        projection.add(MediaStore.Images.Media.MIME_TYPE);
        projection.add(MediaStore.Images.Media.DATE_ADDED);
        projection.add(MediaStore.Images.Media.DATE_MODIFIED);
        projection.add(MediaStore.Images.Media.LATITUDE);
        projection.add(MediaStore.Images.Media.LONGITUDE);
        projection.add(MediaStore.Images.Media.ORIENTATION);
        projection.add(MediaStore.Images.Media.SIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            projection.add(MediaStore.Images.Media.WIDTH);
            projection.add(MediaStore.Images.Media.HEIGHT);
        }
        String selection = null;
        String[] selectionArgs = null;
        if (!TextUtils.equals(bucketId, String.valueOf(Integer.MIN_VALUE))) {
            selection = MediaStore.Images.Media.BUCKET_ID + "=?";
            selectionArgs = new String[]{bucketId};
        }
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection.toArray(new String[projection.size()]), selection,
                selectionArgs, MediaStore.Images.Media.DATE_ADDED + " DESC LIMIT " + limit + " OFFSET " + offset);
        if (cursor != null) {
            int count = cursor.getCount();
            if (count > 0) {
                cursor.moveToFirst();
                do {
                    MediaBean mediaBean = parseImageCursorAndCreateThumbnailImage(context, cursor);
                    if (mediaBean != null) {
                        mediaBeanList.add(mediaBean);
                    }
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return mediaBeanList;
    }

    @Override
    public List<MediaBean> getVideoList(Context context, String bucketId, int page, int limit) {
        int offset = (page - 1) * limit;
        List<MediaBean> mediaBeanList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        List<String> projection = new ArrayList<>();
        projection.add(MediaStore.Video.Media._ID);
        projection.add(MediaStore.Video.Media.TITLE);
        projection.add(MediaStore.Video.Media.DATA);
        projection.add(MediaStore.Video.Media.BUCKET_ID);
        projection.add(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        projection.add(MediaStore.Video.Media.MIME_TYPE);
        projection.add(MediaStore.Video.Media.DATE_ADDED);
        projection.add(MediaStore.Video.Media.DATE_MODIFIED);
        projection.add(MediaStore.Video.Media.LATITUDE);
        projection.add(MediaStore.Video.Media.LONGITUDE);
        projection.add(MediaStore.Video.Media.SIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            projection.add(MediaStore.Video.Media.WIDTH);
            projection.add(MediaStore.Video.Media.HEIGHT);
        }
        String selection = null;
        String[] selectionArgs = null;
        if (!TextUtils.equals(bucketId, String.valueOf(Integer.MIN_VALUE))) {
            selection = MediaStore.Video.Media.BUCKET_ID + "=?";
            selectionArgs = new String[]{bucketId};
        }

        Cursor cursor = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection.toArray(new String[projection.size()]), selection,
                selectionArgs, MediaStore.Video.Media.DATE_ADDED + " DESC LIMIT " + limit + " OFFSET " + offset);
        if (cursor != null) {
            int count = cursor.getCount();
            if (count > 0) {
                cursor.moveToFirst();
                do {
                    MediaBean mediaBean = parseVideoCursorAndCreateThumbnailImage(context, cursor);
                    mediaBeanList.add(mediaBean);
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return mediaBeanList;
    }

    /**
     * 解析图片信息并且创建图片的缩略图
     * @param context
     * @param cursor
     * @return
     */
    private MediaBean parseImageCursorAndCreateThumbnailImage(Context context, Cursor cursor) {
        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
        String originalPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        if (TextUtils.isEmpty(originalPath) || size <= 0 || !new File(originalPath).exists()) {
            return null;
        }
        long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
        String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
        String bucketDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
        String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
        long createDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
        long modifiedDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
        MediaBean mediaBean = new MediaBean();
        mediaBean.setId(id);
        mediaBean.setTitle(title);
        mediaBean.setOriginalPath(originalPath);
        mediaBean.setBucketId(bucketId);
        mediaBean.setBucketDisplayName(bucketDisplayName);
        mediaBean.setMimeType(mimeType);
        mediaBean.setCreateDate(createDate);
        mediaBean.setModifiedDate(modifiedDate);
        mediaBean.setThumbnailBigPath(createThumbnailBigFileName(context, originalPath).getAbsolutePath());
        mediaBean.setThumbnailSmallPath(createThumbnailSmallFileName(context, originalPath).getAbsolutePath());
        int width = 0, height = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            width = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
            height = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
        } else {
            try {
                ExifInterface exifInterface = new ExifInterface(originalPath);
                width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
                height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
            } catch (IOException e) {
            }
        }
        mediaBean.setWidth(width);
        mediaBean.setHeight(height);
        double latitude = cursor.getDouble(cursor.getColumnIndex(MediaStore.Images.Media.LATITUDE));
        mediaBean.setLatitude(latitude);
        double longitude = cursor.getDouble(cursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE));
        mediaBean.setLongitude(longitude);
        int orientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION));
        mediaBean.setOrientation(orientation);
        long length = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
        mediaBean.setLength(length);
        return mediaBean;
    }

    /**
     * 解析视频信息并且创建缩略图
     * @param context
     * @param cursor
     * @return
     */
    private  MediaBean parseVideoCursorAndCreateThumbnailImage(Context context, Cursor cursor) {
        MediaBean mediaBean = new MediaBean();
        long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID));
        mediaBean.setId(id);
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
        mediaBean.setTitle(title);
        String originalPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        mediaBean.setOriginalPath(originalPath);
        String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));
        mediaBean.setBucketId(bucketId);
        String bucketDisplayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
        mediaBean.setBucketDisplayName(bucketDisplayName);
        String mimeType = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
        mediaBean.setMimeType(mimeType);
        long createDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));
        mediaBean.setCreateDate(createDate);
        long modifiedDate = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
        mediaBean.setModifiedDate(modifiedDate);
        long length = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
        mediaBean.setLength(length);

        //创建缩略图文件
        mediaBean.setThumbnailBigPath(createThumbnailBigFileName(context, originalPath).getAbsolutePath());
        mediaBean.setThumbnailSmallPath(createThumbnailSmallFileName(context, originalPath).getAbsolutePath());
        int width = 0, height = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            width = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.WIDTH));
            height = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.HEIGHT));
        } else {
            try {
                ExifInterface exifInterface = new ExifInterface(originalPath);
                width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
                height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
            } catch (IOException e) {

            }
        }
        mediaBean.setWidth(width);
        mediaBean.setHeight(height);
        double latitude = cursor.getDouble(cursor.getColumnIndex(MediaStore.Video.Media.LATITUDE));
        mediaBean.setLatitude(latitude);
        double longitude = cursor.getDouble(cursor.getColumnIndex(MediaStore.Video.Media.LONGITUDE));
        mediaBean.setLongitude(longitude);
        return mediaBean;
    }

}
