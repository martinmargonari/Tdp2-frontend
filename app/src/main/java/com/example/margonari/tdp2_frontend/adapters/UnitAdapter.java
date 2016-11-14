package com.example.margonari.tdp2_frontend.adapters;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Unit;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;

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
    private Context mCOntext;


    public static class UnitHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        RelativeLayout expand_unit;
        TextView unit_number;
        TextView unit_name;
        TextView unit_duration;
        ImageView expand_icon;
        ExpandableRelativeLayout expandable_description;
        TextView unit_description;

        Context context;


        public UnitHolder(View itemView) {
            super(itemView);
            expand_unit = (RelativeLayout) itemView.findViewById(R.id.expand_unit);
            unit_number = (TextView) itemView.findViewById(R.id.unit_number);
            unit_name = (TextView) itemView.findViewById(R.id.unit_name);
            unit_duration = (TextView) itemView.findViewById(R.id.unit_duration);
            expand_icon = (ImageView) itemView.findViewById(R.id.expand_unit_icon);
            unit_description = (TextView) itemView.findViewById(R.id.unit_description);
            expandable_description = (ExpandableRelativeLayout) itemView.findViewById(R.id.expandable_unit_description);

            context = itemView.getContext();

            final Animation rotateUnit = AnimationUtils.loadAnimation(context, R.anim.rerotate);

            expand_unit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandable_description.toggle();
                }
            });

            expandable_description.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    expand_icon.startAnimation(rotateUnit);
                    expand_icon.setImageResource(R.drawable.colapse);
                    expand_unit.setBackgroundResource(R.drawable.border_off);
                }

                @Override
                public void onPreClose() {
                    expand_icon.startAnimation(rotateUnit);
                    expand_icon.setImageResource(R.drawable.expand);
                    expand_unit.setBackgroundResource(R.drawable.border);
                }
            });




            Log.i(LOG_TAG, "Adding Listener");
            //itemView.setOnClickListener(this);
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
        holder.unit_number.setText(Integer.toString(position + 1));
        holder.unit_name.setText(mDataset.get(position).getName());
        holder.unit_description.setText(mDataset.get(position).getDescription());
        holder.unit_duration.setText("Duración estimada: " + mDataset.get(position).getDuration() + " min.");
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