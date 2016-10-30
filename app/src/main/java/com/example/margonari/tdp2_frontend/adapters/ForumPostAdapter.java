package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.ForumPost;

import java.util.ArrayList;

/**
 * Created by Margonari on 30/10/2016.
 */

public class ForumPostAdapter extends RecyclerView
        .Adapter<ForumPostAdapter
        .ForumPostHolder> {
    private static String LOG_TAG = "ForumPostAdapter";
    private ArrayList<ForumPost> mDataset;

    public static class ForumPostHolder extends RecyclerView.ViewHolder {
        TextView post_author;
        TextView post_content;

        Context context;

        public ForumPostHolder(View itemView) {
            super(itemView);
            post_author = (TextView) itemView.findViewById(R.id.forum_post_author);
            post_content = (TextView) itemView.findViewById(R.id.forum_post_content);

            context = itemView.getContext();
        }
    }

    public ForumPostAdapter(ArrayList<ForumPost> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ForumPostAdapter.ForumPostHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forum_post, parent, false);

        ForumPostAdapter.ForumPostHolder dataObjectHolder = new ForumPostAdapter.ForumPostHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(ForumPostAdapter.ForumPostHolder holder, int position) {
        holder.post_author.setText(mDataset.get(position).getAuthor_id());
        holder.post_content.setText(mDataset.get(position).getContent());
    }

    public void addItem(ForumPost forumThread, int index) {
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
}
