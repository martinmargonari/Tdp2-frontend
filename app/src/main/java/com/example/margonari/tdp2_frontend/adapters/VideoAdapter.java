package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.activities.MyCourseUnitActivity;
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
    private Context context;

    public static class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView video_name;
        ImageView video_photo;
        ImageButton download_button;
        Context context;

        public VideoHolder(View itemView) {
            super(itemView);
            video_name = (TextView) itemView.findViewById(R.id.material_name);
            video_photo = (ImageView) itemView.findViewById(R.id.unit_material_pic);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            download_button = (ImageButton) itemView.findViewById(R.id.material_download_button);

        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(VideoAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public VideoAdapter(ArrayList<Video> myDataset,Context context) {
        mDataset = myDataset;
        this.context = context;

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
    public void onBindViewHolder(VideoAdapter.VideoHolder holder, final int position) {
        holder.video_name.setText(mDataset.get(position).getName());

        holder.video_photo.setImageResource(R.drawable.ic_slideshow_black_24dp);
        holder.download_button.setImageResource(R.drawable.ic_file_download_black_24dp);

        holder.download_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mDataset.get(position).getFull_path()!=null){
                    ((MyCourseUnitActivity)context).downloadFile(mDataset.get(position).getFull_path(),mDataset.get(position).getName()+"."+mDataset.get(position).getVideo_extension());
                    Log.d("FILE PATH", mDataset.get(position).getFull_path());
                }
            }

        });
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
