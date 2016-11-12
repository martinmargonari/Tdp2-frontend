package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.activities.MyCourseActivity;
import com.example.margonari.tdp2_frontend.activities.MyCourseParentActivity;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.ForumPost;
import com.example.margonari.tdp2_frontend.domain.Unit;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Created by Margonari on 23/09/2016.
 */
public class MyCourseUnitAdapter extends RecyclerView
        .Adapter<MyCourseUnitAdapter
        .UnitHolder> {
    private static String LOG_TAG = "MyCourseUnitAdapter";
    private ArrayList<Unit> mDataset;
    private static MyClickListener myClickListener;
    private Context mContext;

    public static class UnitHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView week_number;
        TextView unit_name;
        ImageView unit_photo;
        TextView week_limit;
        Context context;

        public UnitHolder(View itemView) {
            super(itemView);
            week_number = (TextView) itemView.findViewById(R.id.week_number);
            unit_name = (TextView) itemView.findViewById(R.id.unit_course_name);
            unit_photo = (ImageView) itemView.findViewById(R.id.unit_course_photo);
            week_limit = (TextView) itemView.findViewById(R.id.week_limit);

            context = itemView.getContext();
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

    public MyCourseUnitAdapter(ArrayList<Unit> myDataset,Context context) {
        mDataset = myDataset;
        mContext=context;


    }

    @Override
    public UnitHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_course_unit, parent, false);

        UnitHolder dataObjectHolder = new UnitHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final UnitHolder holder, int position) {
        holder.unit_name.setText(mDataset.get(position).getName());
        //String urlImage = holder.context.getResources().getString(R.string.imagesURL) + mDataset.get(position).getCourse_id() + "." + mDataset.get(position).getFile_extension();
        String urlImage = holder.context.getResources().getString(R.string.imagesURL) + mDataset.get(position).getCourse_id() + "." + mDataset.get(position).getFile_extension();
        Picasso.with(holder.context).load(urlImage).into(holder.unit_photo);
        //TODO Agregar Descripcion de la unidad

        String urlImage_unity=null;
        String urlImage_course=null;

        Course course=((MyCourseParentActivity)mContext).getCourseFullData();
        if((course)!=null && course.getUnities()!=null){
            Unit unit= course.getUnities().get(position);
            if (unit!=null & unit.getFull_image()!=null)
            urlImage_unity = unit.getFull_image();

        } else{
            urlImage_course = course.getFull_image();

        }

        if(urlImage_unity!=null) {
            Picasso.with(holder.context).load(urlImage_unity).into(holder.unit_photo);
            Log.d("URL_IMAGE_UNIT", urlImage_unity);

        }else if(urlImage_course!=null){
            Log.d("URL_IMAGE_COURSE", urlImage_course);
            Picasso.with(holder.context).load(urlImage_course).into(holder.unit_photo);
        }
        holder.week_number.setText("SEMANA " + Integer.toString(position+1));
        holder.week_limit.setText(mDataset.get(position).getExam_deadline().substring(0,10));
    }

    public void addItem(Unit unit, int index) {
        mDataset.add(index, unit);
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