package com.ubtech.gallery_lib.view;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ubtech.gallery_lib.Constant;
import com.ubtech.gallery_lib.R;
import com.ubtech.gallery_lib.adapter.GalleryAdapter;
import com.ubtech.gallery_lib.bean.MediaBean;
import com.ubtech.gallery_lib.bean.MediaResponse;

import java.util.ArrayList;

/**
 * Created by lei on 2020/7/9
 * desc:
 */
public class VideoFragment extends GalleryBaseFragment {

    private GalleryAdapter adapter;
    private ArrayList<MediaBean> mData;
    private RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video;
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        subscribeUI();
        requestData();
    }

    private void subscribeUI() {
        LiveData<MediaResponse> videoLiveData = viewModel.getVideoLiveData();
        if (null != videoLiveData) {
            videoLiveData.observe(getViewLifecycleOwner(), it -> {
                if (Constant.CODE_SUCCESS == it.getCode()) {
                    Log.d("leix", "获取视频成功：" + it.getData().size());
                    mData.clear();
                    mData.addAll(it.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("leix", "获取视频失败：" + it.getCode());
                }
            });
        }
    }

    private void initView() {
        recyclerView = mRootView.findViewById(R.id.recycleView);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 7);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        mData = new ArrayList<>();
        adapter = new GalleryAdapter(getActivity(), mData);
    }
    private void requestData(){
        viewModel.getVideoList(getActivity(), String.valueOf(Integer.MIN_VALUE),0,100);
    }
}
