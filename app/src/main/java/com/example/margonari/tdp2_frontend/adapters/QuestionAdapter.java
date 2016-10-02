package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Question;

import java.util.ArrayList;

/**
 * Created by Margonari on 02/10/2016.
 */

public class QuestionAdapter extends RecyclerView
        .Adapter<QuestionAdapter
        .QuestionHolder> {
    private static String LOG_TAG = "QuestionAdapter";
    private ArrayList<Question> mDataset;

    public static class QuestionHolder extends RecyclerView.ViewHolder {
        TextView question;
        RadioButton option_1;
        RadioButton option_2;
        RadioButton option_3;
        RadioButton option_4;
        Context context;

        public QuestionHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.question);
            option_1 = (RadioButton) itemView.findViewById(R.id.question_option_1);
            option_2 = (RadioButton) itemView.findViewById(R.id.question_option_2);
            option_3 = (RadioButton) itemView.findViewById(R.id.question_option_3);
            option_4 = (RadioButton) itemView.findViewById(R.id.question_option_4);

            context = itemView.getContext();
        }

    }

    public QuestionAdapter(ArrayList<Question> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public QuestionAdapter.QuestionHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.evaluation_question, parent, false);

        QuestionAdapter.QuestionHolder dataObjectHolder = new QuestionAdapter.QuestionHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.QuestionHolder holder, int position) {
        holder.question.setText(mDataset.get(position).getQuestion());
        holder.option_1.setText(mDataset.get(position).getOption(0));
        holder.option_2.setText(mDataset.get(position).getOption(1));
        holder.option_3.setText(mDataset.get(position).getOption(2));
        holder.option_4.setText(mDataset.get(position).getOption(3));

    }

    public void addItem(Question Question, int index) {
        mDataset.add(index, Question);
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
