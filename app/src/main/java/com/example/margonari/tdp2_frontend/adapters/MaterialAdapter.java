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
import com.example.margonari.tdp2_frontend.domain.Material;

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
    Context context;

    public static class MaterialHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView material_name;
        ImageView material_photo;
        ImageButton boton_adjuntos;
        Context context;


        public MaterialHolder(View itemView) {
            super(itemView);
            material_name = (TextView) itemView.findViewById(R.id.material_name);
            material_photo = (ImageView) itemView.findViewById(R.id.unit_material_pic);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            boton_adjuntos= (ImageButton) itemView.findViewById(R.id.material_download_button);

        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MaterialAdapter(ArrayList<Material> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
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
    public void onBindViewHolder(MaterialHolder holder, final int position) {
        holder.material_name.setText(mDataset.get(position).getName());
        if (mDataset.get(position).getType() == Material.EXAMEN) {
            holder.material_photo.setImageResource(R.drawable.ic_assignment_black_24dp);
        } else {
            holder.material_photo.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
        }
        holder.boton_adjuntos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mDataset.get(position).getFull_path()!=null){
                    ((MyCourseUnitActivity)context).downloadFile(mDataset.get(position).getFull_path(),mDataset.get(position).getName()+mDataset.get(position).getFile_extension());
                    Log.d("FILE PATH", mDataset.get(position).getFull_path());
                }
            }


    });
    }

    public void addItem(Material material, int index) {
        mDataset.add(index, material);
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