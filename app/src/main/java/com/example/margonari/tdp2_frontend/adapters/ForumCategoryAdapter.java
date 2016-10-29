package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.ForumCategory;

import java.util.ArrayList;

/**
 * Created by Margonari on 28/10/2016.
 */

public class ForumCategoryAdapter extends RecyclerView
        .Adapter<ForumCategoryAdapter
        .ForumCategoryHolder> {
    private static String LOG_TAG = "ForumCategoryAdapter";
    private ArrayList<ForumCategory> mDataset;
    private static ForumCategoryAdapter.MyClickListener myClickListener;

    public static class ForumCategoryHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView category_title;
        TextView category_description;
        Context context;

        public ForumCategoryHolder(View itemView) {
            super(itemView);
            category_title = (TextView) itemView.findViewById(R.id.week_number);
            category_description = (TextView) itemView.findViewById(R.id.unit_course_name);

            context = itemView.getContext();
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ForumCategoryAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public ForumCategoryAdapter(ArrayList<ForumCategory> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ForumCategoryAdapter.ForumCategoryHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.foum_category, parent, false);

        ForumCategoryAdapter.ForumCategoryHolder dataObjectHolder = new ForumCategoryAdapter.ForumCategoryHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(ForumCategoryAdapter.ForumCategoryHolder holder, int position) {
        holder.category_title.setText(mDataset.get(position).getTitle());
        holder.category_description.setText(mDataset.get(position).getDescription());
    }

    public void addItem(ForumCategory forumCategory, int index) {
        mDataset.add(index, forumCategory);
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