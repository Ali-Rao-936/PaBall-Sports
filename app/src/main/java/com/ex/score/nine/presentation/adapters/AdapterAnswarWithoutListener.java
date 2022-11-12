package com.ex.score.nine.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ex.score.nine.R;
import com.ex.score.nine.domain.models.AnswersModelIncorrect;
import com.ex.score.nine.domain.models.AnswersModelT;

import java.util.ArrayList;

public class AdapterAnswarWithoutListener extends RecyclerView.Adapter<AdapterAnswarWithoutListener.ViewHolder> {

    //we will use this adapter when the user select incorrect answer and we want to show him
    //the correct answer
    private final Context context;
    ArrayList<AnswersModelIncorrect> answerArrayList;

    public AdapterAnswarWithoutListener
            (Context context, ArrayList<AnswersModelIncorrect> scoresArrayList) {
        this.context = context;
        this.answerArrayList = scoresArrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_answar, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        fillText(holder, position);
        fillBackGround(holder,context,position);
    }

    private void fillBackGround(ViewHolder holder, Context context, int position) {
        if (answerArrayList.get(position).getCheck() == 1)
        {
            holder.answer_cont_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.search_bg));
            holder.choose_con_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.normal_bg_solid));
            holder.choose_tv.setTextColor(context.getResources().getColor(R.color.white));
        }

        if (answerArrayList.get(position).getCheck() == 2)
        {
            holder.answer_cont_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.incorrect_answar_bg));
            holder.choose_con_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.circl_bg_solid));
            holder.choose_tv.setTextColor(context.getResources().getColor(R.color.splash_screen_gradint_1));
        }

        if (answerArrayList.get(position).getCheck() == 3)
        {
            holder.answer_cont_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.correct_after_incorrect_answar_bg));
            holder.choose_con_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.correct_after_incorrect_answar_bg_solid));
            holder.choose_tv.setTextColor(context.getResources().getColor(R.color.white));
        }
    }


    private void fillText(ViewHolder holder, int position) {
        holder.choose_tv.setText(answerArrayList.get(position).getChar());
        holder.answer_tv.setText(answerArrayList.get(position).getAnswer());
    }


    @Override
    public int getItemCount() {
        return answerArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView choose_tv, answer_tv;
        RelativeLayout answer_cont_rl, choose_con_rl;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            choose_tv = itemView.findViewById(R.id.choose_tv);
            answer_tv = itemView.findViewById(R.id.answer_tv);

            answer_cont_rl = itemView.findViewById(R.id.answer_cont_rl);
            choose_con_rl = itemView.findViewById(R.id.choose_con_rl);
        }
    }
}