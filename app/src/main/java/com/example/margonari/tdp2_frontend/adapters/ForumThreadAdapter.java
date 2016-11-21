package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.ForumThread;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
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
    private Context mCOntext;


    public static class ForumThreadHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        ImageView thread_author_pic;
        TextView thread_author;
        TextView thread_replies;
        TextView thread_title;
        TextView thread_date;

        Context context;

        public ForumThreadHolder(View itemView) {
            super(itemView);
            thread_author_pic = (ImageView) itemView.findViewById(R.id.forum_thread_author_pic);
            thread_author = (TextView) itemView.findViewById(R.id.forum_thread_author);
            thread_replies = (TextView) itemView.findViewById(R.id.forum_thread_replies);
            thread_title = (TextView) itemView.findViewById(R.id.forum_thread_title);
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

    public ForumThreadAdapter(ArrayList<ForumThread> myDataset, Context context) {
        mDataset = myDataset;
        mCOntext= context;

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
    public void onBindViewHolder(final ForumThreadAdapter.ForumThreadHolder holder, int position) {
        holder.thread_author.setText(mDataset.get(position).getAuthor().getName());
        Picasso.with(mCOntext).load( R.drawable.profile_pic_user).into(holder.thread_author_pic);

        Picasso.Builder builder = new Picasso.Builder(this.mCOntext);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                exception.printStackTrace();
            }
        });
        builder.build().load(mDataset.get(position).getAuthor_image()).into(

                new Target() {
                    @Override
                    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){

                        holder.thread_author_pic.setImageBitmap(bitmap);

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {


                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Picasso.with(mCOntext).load( R.drawable.profile_pic_user).into(holder.thread_author_pic);

                    }
                }
        );





        //TODO Nombre de autor e imagen de autor
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