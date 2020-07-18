package com.ubtech.gallery_lib.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ubtech.gallery_lib.viewmodel.GalleryViewModel;

/**
 * Created by lei on 2020/7/9
 * desc:
 */
public abstract class GalleryBaseFragment extends Fragment {

    protected GalleryViewModel viewModel;
    protected View mRootView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    public abstract int getLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initViewModel();
        super.onActivityCreated(savedInstanceState);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(getActivity()).get(GalleryViewModel.class);
        Log.d("leix","fragment对象："+this.toString()+"--->"+viewModel.toString());
    }
}
