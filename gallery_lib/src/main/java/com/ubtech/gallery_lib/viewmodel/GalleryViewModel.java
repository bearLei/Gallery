package com.ubtech.gallery_lib.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ubtech.gallery_lib.Configuration;
import com.ubtech.gallery_lib.Constant;
import com.ubtech.gallery_lib.bean.MediaBean;
import com.ubtech.gallery_lib.bean.MediaResponse;
import com.ubtech.gallery_lib.data.IMediaProduce;
import com.ubtech.gallery_lib.data.SystemMediaProducer;
import com.ubtech.gallery_lib.data.TargetPathMediaProducer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lei on 2020/7/17
 * desc:
 */
public class GalleryViewModel extends ViewModel {

    private static final String TAG = "GalleryViewModel";

    private IMediaProduce mediaProduce;
    private Configuration configuration;
    private MutableLiveData<MediaResponse> mImageLiveData = new MutableLiveData<>();
    private MutableLiveData<MediaResponse> mVideoLiveData = new MutableLiveData<>();
    private MutableLiveData<MediaResponse> mAllMediaLiveData = new MutableLiveData<>();


    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        mediaProduce = new SystemMediaProducer();
        if (configuration.getCustomLoadFilePath().isEmpty()) {
        } else {
            mediaProduce = new TargetPathMediaProducer();
        }
    }

    public void getImageList(Context context, String bucketId, int page, int limit) {
        Observable.create((ObservableOnSubscribe<List<MediaBean>>) e -> {
            List<MediaBean> imageList = mediaProduce.getImageList(context, configuration.getCustomLoadFilePath());
            Log.d(TAG, "图片资源：" + imageList.size());
            e.onNext(imageList);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<MediaBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MediaBean> mediaBeans) {
                        mImageLiveData.setValue(new MediaResponse(Constant.CODE_SUCCESS, "", mediaBeans));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mImageLiveData.setValue(new MediaResponse(Constant.CODE_FAIL, "", null));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getVideoList(Context context, String bucketId, int page, int limit) {
        Observable.create((ObservableOnSubscribe<List<MediaBean>>) e -> {
            List<MediaBean> videoList = mediaProduce.getVideoList(context,configuration.getCustomLoadFilePath());
            Log.d(TAG, "视频资源：" + videoList.size());
            e.onNext(videoList);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<MediaBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MediaBean> mediaBeans) {
                        mVideoLiveData.setValue(new MediaResponse(Constant.CODE_SUCCESS, "", mediaBeans));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mVideoLiveData.setValue(new MediaResponse(Constant.CODE_FAIL, "", null));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getAllMedia(Context context, String bucketId, int page, int limit) {
        Observable.create((ObservableOnSubscribe<List<MediaBean>>) e -> {
            List<MediaBean> allMedia = mediaProduce.getAllMedia(context, configuration.getCustomLoadFilePath());
            Log.d(TAG, "媒体资源：" + allMedia.size());
            e.onNext(allMedia);
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<MediaBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MediaBean> mediaBeans) {
                        mAllMediaLiveData.setValue(new MediaResponse(Constant.CODE_SUCCESS, "", mediaBeans));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAllMediaLiveData.setValue(new MediaResponse(Constant.CODE_FAIL, "", null));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public LiveData<MediaResponse> getImageLiveData() {
        return mImageLiveData;
    }

    public LiveData<MediaResponse> getVideoLiveData() {
        return mVideoLiveData;
    }

    public LiveData<MediaResponse> getAllMediaLiveData() {
        return mAllMediaLiveData;
    }
}
