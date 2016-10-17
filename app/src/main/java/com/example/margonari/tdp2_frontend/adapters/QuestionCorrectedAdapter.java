package com.example.margonari.tdp2_frontend.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Question;

import java.util.ArrayList;

/**
 * Created by Margonari on 17/10/2016.
 */

public class QuestionCorrectedAdapter extends RecyclerView
        .Adapter<QuestionCorrectedAdapter
        .QuestionCorrectedHolder> {
    private static String LOG_TAG = "QuestionCorrectedAdapter";
    private ArrayList<Question> mDataset;
    public ArrayList<Integer> mQuestionResults;

    public static class QuestionCorrectedHolder extends RecyclerView.ViewHolder {
        TextView question;
        RadioGroup question_group;
        RadioButton option_1;
        RadioButton option_2;
        RadioButton option_3;
        RadioButton option_4;
        CardView cardView;
        Context context;


        public QuestionCorrectedHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.question);
            question_group = (RadioGroup) itemView.findViewById(R.id.question_radio_group);

            option_1 = (RadioButton) itemView.findViewById(R.id.question_option_1);
            option_2 = (RadioButton) itemView.findViewById(R.id.question_option_2);
            option_3 = (RadioButton) itemView.findViewById(R.id.question_option_3);
            option_4 = (RadioButton) itemView.findViewById(R.id.question_option_4);

            cardView = (CardView) itemView.findViewById(R.id.cv_evaluation_question);
            context = itemView.getContext();
        }

    }

    public QuestionCorrectedAdapter(ArrayList<Question> myDataset, ArrayList<Integer> questionResults) {
        mDataset = myDataset;
        mQuestionResults = questionResults;
    }

    @Override
    public QuestionCorrectedAdapter.QuestionCorrectedHolder onCreateViewHolder(ViewGroup parent,
                                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.evaluation_question, parent, false);


        QuestionCorrectedAdapter.QuestionCorrectedHolder dataObjectHolder = new QuestionCorrectedAdapter.QuestionCorrectedHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(QuestionCorrectedAdapter.QuestionCorrectedHolder holder, int position) {
        holder.question.setText(mDataset.get(position).getQuestion());
        holder.option_1.setText(mDataset.get(position).getAnswers()[0].getAnswer());
        holder.option_2.setText(mDataset.get(position).getAnswers()[1].getAnswer());
        holder.option_3.setText(mDataset.get(position).getAnswers()[2].getAnswer());
        holder.option_4.setText(mDataset.get(position).getAnswers()[3].getAnswer());
        if (mQuestionResults.get(position) == 0) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FF4444"));
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#44FF44"));
        }
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