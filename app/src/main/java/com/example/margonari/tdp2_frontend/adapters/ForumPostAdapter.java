package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.activities.MyCourseForumThreadPostsActivity;
import com.example.margonari.tdp2_frontend.domain.AttachFile;
import com.example.margonari.tdp2_frontend.domain.ForumPost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Margonari on 30/10/2016.
 */

public class ForumPostAdapter extends RecyclerView
        .Adapter<ForumPostAdapter
        .ForumPostHolder> {
    private static String LOG_TAG = "ForumPostAdapter";
    private ArrayList<ForumPost> mDataset;
    private Context mCOntext;

    public static class ForumPostHolder extends RecyclerView.ViewHolder {
        ImageView post_author_pic;
        TextView post_author;
        TextView post_content;
        TextView post_attachment;
        TextView post_date;

        Context context;

        public ForumPostHolder(View itemView) {
            super(itemView);
            post_author_pic = (ImageView) itemView.findViewById(R.id.forum_post_author_pic);
            post_author = (TextView) itemView.findViewById(R.id.forum_post_author);
            post_content = (TextView) itemView.findViewById(R.id.forum_post_content);
            post_attachment = (TextView) itemView.findViewById(R.id.forum_post_attachment);
            post_date = (TextView) itemView.findViewById(R.id.forum_post_creation_date);

            context = itemView.getContext();

        }
    }

    public ForumPostAdapter(ArrayList<ForumPost> myDataset, Context context) {
        mCOntext= context;
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
    public void onBindViewHolder(final ForumPostAdapter.ForumPostHolder holder, final int position) {
        holder.post_author_pic.setImageDrawable(holder.context.getDrawable(R.drawable.com_facebook_profile_picture_blank_portrait));
        Picasso.with(mCOntext).load( mDataset.get(position).getAuthor_image()).into(holder.post_author_pic);

        holder.post_author.setText(mDataset.get(position).getAuthor_name());
        holder.post_content.setText(mDataset.get(position).getContent());
        Log.d("AuthorImageUrl", mDataset.get(position).getAuthor_image()); //Aca recibis la imagen
        if(mDataset.get(position).getAttachments().length == 0) {
            holder.post_attachment.setVisibility(View.GONE);
        }else {
            holder.post_attachment.setText(mDataset.get(position).getAttachments()[0].getName());
        }
        holder.post_attachment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AttachFile[] arrayOfAttachments = mDataset.get(position).getAttachments();
                if(mDataset.get(position).getAttachments().length!=0) {
                    ((MyCourseForumThreadPostsActivity)mCOntext).downloadFile(mDataset.get(position).getAttachments()[0].getFile_path(),mDataset.get(position).getAttachments()[0].getName());
                }

            }
        });

        holder.post_date.setText(mDataset.get(position).getCreated_at());
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
