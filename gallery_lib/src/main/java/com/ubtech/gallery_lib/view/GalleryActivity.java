package com.ubtech.gallery_lib.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ubtech.gallery_lib.Configuration;
import com.ubtech.gallery_lib.R;
import com.ubtech.gallery_lib.bean.MediaBean;
import com.ubtech.gallery_lib.data.IMediaProduce;
import com.ubtech.gallery_lib.rxjob.Job;
import com.ubtech.gallery_lib.rxjob.RxJob;
import com.ubtech.gallery_lib.rxjob.job.ImageThmbnailJobCreate;
import com.ubtech.gallery_lib.viewmodel.GalleryViewModel;

import java.io.File;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_CONFIGURATION = "Configuration";
    public static final String MODE_WHOLE = "mode_whole";
    public static final String MODE_IMAGE = "mode_image";
    public static final String MODE_VIDEO = "mode_video";

    private ImageView mWholeIcon, mImageIcon, mVideoIcon;
    private Configuration configuration;
    private IMediaProduce mediaProduce;
    private GalleryViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        findViewById(R.id.back_icon).setOnClickListener(this);
        mWholeIcon = findViewById(R.id.whole);
        mImageIcon = findViewById(R.id.image);
        mVideoIcon = findViewById(R.id.video);
        mWholeIcon.setOnClickListener(this);
        mVideoIcon.setOnClickListener(this);
        mImageIcon.setOnClickListener(this);
        changeMode(MODE_WHOLE);
        parseConfiguration();
    }

    private void parseConfiguration() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            configuration = (Configuration) extras.getSerializable(EXTRA_CONFIGURATION);
            if (null == configuration) {
                Log.d("leix", "配置文件不能为空");
                finish();
            }
            registerViewModel();
            Log.d("leix","GalleryActivity对象："+viewModel.toString());
            viewModel.setConfiguration(configuration);
        }
    }

    private void registerViewModel(){
        viewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
    }

    private void changeMode(String mode) {
        Fragment curFragment = getSupportFragmentManager().findFragmentByTag(mode);
        if (curFragment != null && curFragment.isVisible()) {
            return;
        }
        if (MODE_WHOLE.equals(mode)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, WholeFragment.newInstance())
                    .commit();
            mWholeIcon.setSelected(true);
            mImageIcon.setSelected(false);
            mVideoIcon.setSelected(false);
        } else if (MODE_IMAGE.equals(mode)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, ImageFragment.newInstance())
                    .commit();
            mWholeIcon.setSelected(false);
            mImageIcon.setSelected(true);
            mVideoIcon.setSelected(false);
        } else if (MODE_VIDEO.equals(mode)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, VideoFragment.newInstance())
                    .commit();
            mWholeIcon.setSelected(false);
            mImageIcon.setSelected(false);
            mVideoIcon.setSelected(true);
        }
    }



    private void thmbnial(MediaBean mediaBean) {
        Log.d("leix", "开始压缩：" + System.currentTimeMillis());
        if (!new File(mediaBean.getThumbnailBigPath()).exists() || !new File(mediaBean.getThumbnailSmallPath()).exists()) {
            Job job = new ImageThmbnailJobCreate(this, mediaBean).create();
            RxJob.getDefault().addJob(job);
        }
        Log.d("leix", "结束压缩：" + System.currentTimeMillis());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_icon) {
            finish();
        } else if (id == R.id.whole) {
            changeMode(MODE_WHOLE);
        } else if (id == R.id.image) {
            changeMode(MODE_IMAGE);
        } else if (id == R.id.video) {
            changeMode(MODE_VIDEO);
        }
    }
}
