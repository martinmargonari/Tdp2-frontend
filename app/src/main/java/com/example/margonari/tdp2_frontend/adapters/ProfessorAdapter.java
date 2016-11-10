package com.example.margonari.tdp2_frontend.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Professor;

import java.util.ArrayList;

/**
 * Created by Margonari on 23/09/2016.
 */
public class ProfessorAdapter extends RecyclerView
        .Adapter<ProfessorAdapter
        .ProfessorHolder> {
    private static String LOG_TAG = "ProfessorAdapter";
    private ArrayList<Professor> mDataset;
    private static MyClickListener myClickListener;

    public static class ProfessorHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView professor_name;
        TextView professor_type;
        //ImageView Professor_photo;

        public ProfessorHolder(View itemView) {
            super(itemView);
            professor_name = (TextView) itemView.findViewById(R.id.professor_name);
            professor_type = (TextView) itemView.findViewById(R.id.professor_type);
            //Professor_photo = (ImageView) itemView.findViewById(R.id.professor_photo);
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

    public ProfessorAdapter(ArrayList<Professor> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ProfessorHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_professor, parent, false);

        ProfessorHolder dataObjectHolder = new ProfessorHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(ProfessorHolder holder, int position) {
        holder.professor_name.setText(mDataset.get(position).getFullName());
        holder.professor_type.setText(mDataset.get(position).getType());
        System.out.println("TIPO: " + mDataset.get(position).getType());
        //holder.professor_photo.setImageResource(mDataset.get(position).getPhoto_id());
    }

    public void addItem(Professor Professor, int index) {
        mDataset.add(index, Professor);
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
