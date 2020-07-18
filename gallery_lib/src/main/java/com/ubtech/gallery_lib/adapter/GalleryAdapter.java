package com.ubtech.gallery_lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ubtech.gallery_lib.R;
import com.ubtech.gallery_lib.bean.MediaBean;

import java.util.List;

/**
 * Created by lei on 2020/7/17
 * desc:
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

    private Context context;
    private List<MediaBean> mData;

    public GalleryAdapter(Context context, List<MediaBean> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_gallery_item, null, false);
        return new GalleryViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.setData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

}
