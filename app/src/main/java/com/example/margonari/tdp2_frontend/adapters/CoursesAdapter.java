package com.example.margonari.tdp2_frontend.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Course;

import java.util.ArrayList;


/**
 * Created by Margonari on 18/09/2016.
 */
public class CoursesAdapter extends RecyclerView
        .Adapter<CoursesAdapter
        .CourseHolder> {
    private static String LOG_TAG = "CoursesAdapter";
    private ArrayList<Course> mDataset;
    private static MyClickListener myClickListener;

    public static class CourseHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView course_name;
        TextView course_description;
        ImageView course_photo;

        public CourseHolder(View itemView) {
            super(itemView);
            course_name = (TextView) itemView.findViewById(R.id.course_name);
            course_description = (TextView) itemView.findViewById(R.id.course_description);
            course_photo = (ImageView) itemView.findViewById(R.id.course_photo);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public CoursesAdapter(ArrayList<Course> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public CourseHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        CourseHolder dataObjectHolder = new CourseHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(CourseHolder holder, int position) {
        holder.course_name.setText(mDataset.get(position).getName());
        holder.course_description.setText(mDataset.get(position).getDescription());
        holder.course_photo.setImageResource(mDataset.get(position).getPhoto_id());
    }

    public void addItem(Course course, int index) {
        mDataset.add(index, course);
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