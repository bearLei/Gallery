package com.ubtech.gallery_lib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ubtech.gallery_lib.view.GalleryActivity;

/**
 * Created by lei on 2020/7/9
 * desc:
 */
public class GalleryInstance {
    private static GalleryInstance mInstance;
    private Configuration configuration;
    private GalleryInstance() {
        configuration = new Configuration();
    }

    public static GalleryInstance getInstance() {
        if (null == mInstance) {
            synchronized (GalleryInstance.class) {
                if (null == mInstance) {
                    mInstance = new GalleryInstance();
                }
            }
        }
        return mInstance;
    }

    public GalleryInstance setCustomLoadFilePath(String path){
        configuration.setCustomLoadFilePath(path);
        return this;
    }

    public void openGalley(Context context){
        Intent intent = new Intent(context, GalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GalleryActivity.EXTRA_CONFIGURATION,configuration);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
