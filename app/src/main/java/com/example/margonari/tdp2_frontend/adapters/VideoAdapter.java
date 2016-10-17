package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Video;

import java.util.ArrayList;

/**
 * Created by Margonari on 17/10/2016.
 */

public class VideoAdapter extends RecyclerView
        .Adapter<VideoAdapter
        .VideoHolder> {
    private static String LOG_TAG = "VideoAdapter";
    private ArrayList<Video> mDataset;
    private static VideoAdapter.MyClickListener myClickListener;

    public static class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView video_name;
        ImageView video_photo;
        Context context;

        public VideoHolder(View itemView) {
            super(itemView);
            video_name = (TextView) itemView.findViewById(R.id.material_name);
            video_photo = (ImageView) itemView.findViewById(R.id.unit_material_pic);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(VideoAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public VideoAdapter(ArrayList<Video> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public VideoAdapter.VideoHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.material_unit, parent, false);

        VideoAdapter.VideoHolder dataObjectHolder = new VideoAdapter.VideoHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.VideoHolder holder, int position) {
        holder.video_name.setText(mDataset.get(position).getName());
        holder.video_photo.setImageResource(R.drawable.ic_slideshow_black_24dp);
    }

    public void addItem(Video Video, int index) {
        mDataset.add(index, Video);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
