package com.ubtech.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.ubtech.gallery_lib.GalleryInstance;
import com.ubtech.gallery_lib.view.GalleryActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.open_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = getFilesDir(MainActivity.this)+File.separator+"media/";
                File file = new File(path);
                if (!file.exists()){
                    file.mkdirs();
                }
                GalleryInstance.getInstance().setCustomLoadFilePath(path).openGalley(MainActivity.this);
            }
        });
    }


    public String getFilesDir(Context context) {
        File externalFilesDir = context.getExternalFilesDir(null);
        String filesDir;

        if (externalFilesDir != null) {
            filesDir = externalFilesDir.getAbsolutePath();
        } else {
            filesDir = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + "Android"
                    + File.separator + "data"
                    + File.separator + context.getPackageName()
                    + File.separator + "files";
        }
        return filesDir;

    }
}
