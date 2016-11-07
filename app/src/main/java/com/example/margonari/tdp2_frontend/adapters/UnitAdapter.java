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
        TextView unit_name;
        TextView unit_description;
        LinearLayout collapsablelayout;
        RelativeLayout expand_unit;
        ExpandableRelativeLayout unit_expandable;
        ImageView unit_photo;
        Button btnExpandable;

        Context context;


        public UnitHolder(View itemView) {
            super(itemView);
            //unit_name = (TextView) itemView.findViewById(R.id.unit_name);
            unit_description = (TextView) itemView.findViewById(R.id.unit_description);
            //expand_unit = (RelativeLayout) itemView.findViewById(R.id.expand_unit);
            unit_expandable = (ExpandableRelativeLayout) itemView.findViewById(R.id.expandable_unit_description);
            btnExpandable = (Button) itemView.findViewById(R.id.expandableButton);
            //collapsablelayout = (LinearLayout) itemView.findViewById(R.id.collapsable);

            //collapsablelayout.setVisibility(View.GONE);

            btnExpandable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        unit_expandable.toggle();
                }
            });

            //unit_photo = (ImageView) itemView.findViewById(R.id.unit_photo);
            context = itemView.getContext();


            Log.i(LOG_TAG, "Adding Listener");
            //itemView.setOnClickListener(this);
        }

        private void expand()
        {
            collapsablelayout.setVisibility(View.VISIBLE);

            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            collapsablelayout.measure(widthSpec, heightSpec);

            ValueAnimator mAnimator = slideAnimator(0, collapsablelayout.getMeasuredHeight());
            mAnimator.start();
        }

        private void collapse() {
            int finalHeight = collapsablelayout.getHeight();

            ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    //Height=0, but it set visibility to GONE
                    collapsablelayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }

            });
            mAnimator.start();
        }

        private ValueAnimator slideAnimator(int start, int end)
        {

            ValueAnimator animator = ValueAnimator.ofInt(start, end);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //Update Height
                    int value = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = collapsablelayout.getLayoutParams();
                    layoutParams.height = value;
                    collapsablelayout.setLayoutParams(layoutParams);
                }
            });
            return animator;
        }


        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }

        /*private void addExpandables(View itemView) {
            unit_expandable = (ExpandableLinearLayout) itemView.findViewById(R.id.expandable_unit_description);
            expand_unit = (RelativeLayout) itemView.findViewById(R.id.expand_unit);
            expand_unit.setOnClickListener(new View.OnClickListener() {
                toggle();
            }

            final ImageView unitButton = (ImageView) itemView.findViewById(R.id.expand_unit_icon);
            final Animation rotateButton = AnimationUtils.loadAnimation(context, R.anim.rerotate);

            unit_expandable.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    unitButton.startAnimation(rotateButton);
                    unitButton.setImageDrawable(context.getResources().getDrawable(R.drawable.colapse));
                }

                @Override
                public void onPreClose() {
                    unitButton.startAnimation(rotateButton);
                    unitButton.setImageDrawable(context.getResources().getDrawable(R.drawable.expand));
                }
            });
        }*/
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
        //holder.unit_name.setText(mDataset.get(position).getName());
        holder.unit_description.setText(mDataset.get(position).getName());
        holder.btnExpandable.setText(mDataset.get(position).getName());
        //String urlImage = holder.context.getResources().getString(R.string.imagesURL) + mDataset.get(position).getCourse_id() + "." + mDataset.get(position).getFile_extension();
        //Picasso.with(holder.context).load(urlImage).into(holder.unit_photo);


        //holder.unit_photo.setImageResource(mDataset.get(position).getPhoto_id());
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