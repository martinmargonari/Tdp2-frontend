package com.example.margonari.tdp2_frontend.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Unit;

import java.util.ArrayList;


/**
 * Created by Margonari on 23/09/2016.
 */
public class UnitAdapter extends RecyclerView
        .Adapter<UnitAdapter
        .UnitHolder> {
    private static String LOG_TAG = "UnitAdapter";
    private ArrayList<Unit> mDataset;
    private static MyClickListener myClickListener;

    public static class UnitHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView unit_name;
        ImageView unit_photo;

        public UnitHolder(View itemView) {
            super(itemView);
            unit_name = (TextView) itemView.findViewById(R.id.unit_name);
            unit_photo = (ImageView) itemView.findViewById(R.id.unit_photo);
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

    public UnitAdapter(ArrayList<Unit> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public UnitHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_unit, parent, false);

        UnitHolder dataObjectHolder = new UnitHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(UnitHolder holder, int position) {
        holder.unit_name.setText(mDataset.get(position).getName());
        holder.unit_photo.setImageResource(mDataset.get(position).getPhoto_id());
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