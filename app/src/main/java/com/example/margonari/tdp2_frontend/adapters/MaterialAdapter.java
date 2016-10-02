package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Material;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Margonari on 23/09/2016.
 */
public class MaterialAdapter extends RecyclerView
        .Adapter<MaterialAdapter
        .MaterialHolder> {
    private static String LOG_TAG = "MaterialAdapter";
    private ArrayList<Material> mDataset;
    private static MyClickListener myClickListener;

    public static class MaterialHolder extends RecyclerView.ViewHolder {
        TextView material_name;
        ImageView material_photo;
        Context context;

        public MaterialHolder(View itemView) {
            super(itemView);
            material_name = (TextView) itemView.findViewById(R.id.material_name);
            material_photo = (ImageView) itemView.findViewById(R.id.unit_material_pic);
            context = itemView.getContext();
        }

    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MaterialAdapter(ArrayList<Material> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MaterialHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.material_unit, parent, false);

        MaterialHolder dataObjectHolder = new MaterialHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(MaterialHolder holder, int position) {
        holder.material_name.setText(mDataset.get(position).getName());
        if (mDataset.get(position).getType() == Material.VIDEO) {
            holder.material_photo.setImageResource(R.drawable.ic_slideshow_black_24dp);
        } else {
            holder.material_photo.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
        }
    }

    public void addItem(Material Material, int index) {
        mDataset.add(index, Material);
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