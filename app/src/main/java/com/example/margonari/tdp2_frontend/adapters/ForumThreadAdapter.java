package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.ForumThread;

import java.util.ArrayList;

/**
 * Created by Margonari on 28/10/2016.
 */

public class ForumThreadAdapter extends RecyclerView
        .Adapter<ForumThreadAdapter
        .ForumThreadHolder> {
    private static String LOG_TAG = "ForumThreadAdapter";
    private ArrayList<ForumThread> mDataset;
    private static ForumThreadAdapter.MyClickListener myClickListener;

    public static class ForumThreadHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView thread_author;
        TextView thread_title;
        TextView thread_replies;
        TextView thread_date;

        Context context;

        public ForumThreadHolder(View itemView) {
            super(itemView);
            thread_author = (TextView) itemView.findViewById(R.id.forum_thread_author);
            thread_title = (TextView) itemView.findViewById(R.id.forum_thread_title);
            thread_replies = (TextView) itemView.findViewById(R.id.forum_thread_replies);
            thread_date = (TextView) itemView.findViewById(R.id.forum_thread_creation_date);

            context = itemView.getContext();
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ForumThreadAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public ForumThreadAdapter(ArrayList<ForumThread> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ForumThreadAdapter.ForumThreadHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forum_thread, parent, false);

        ForumThreadAdapter.ForumThreadHolder dataObjectHolder = new ForumThreadAdapter.ForumThreadHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(ForumThreadAdapter.ForumThreadHolder holder, int position) {
        holder.thread_author.setText(mDataset.get(position).getAuthor_id());
        holder.thread_title.setText(mDataset.get(position).getTitle());
        holder.thread_replies.setText(mDataset.get(position).getReply_count());
        holder.thread_date.setText(mDataset.get(position).getCreated_at());
    }

    public void addItem(ForumThread forumThread, int index) {
        mDataset.add(index, forumThread);
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