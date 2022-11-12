package com.ex.score.nine.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ex.score.nine.R;
import com.ex.score.nine.domain.models.AnswersModelT;
import com.ex.score.nine.domain.models.Scores;

import java.util.ArrayList;

public class AdapterAnswar extends RecyclerView.Adapter<AdapterAnswar.ViewHolder> {

    private final Context context;
    ArrayList<AnswersModelT> answerArrayList;
    PassTheAnswer passTheAnswer;

    public AdapterAnswar
            (Context context, ArrayList<AnswersModelT> scoresArrayList,PassTheAnswer passTheAnswer) {
        this.context = context;
        this.answerArrayList = scoresArrayList;
        this.passTheAnswer = passTheAnswer;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_answar, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        fillText(holder, position);
        actionListenerToAnswar(holder,position,context);
    }

    private void actionListenerToAnswar(ViewHolder holder, int position, Context context) {
        holder.answer_cont_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerArrayList.get(position).getCorrectOrNo())
                {
                    passTheAnswer.onClick(true,answerArrayList.get(position));
                    holder.answer_cont_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.correct_answar_bg));
                    holder.choose_con_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.circl_bg_solid));
                    holder.choose_tv.setTextColor(context.getResources().getColor(R.color.splash_screen_gradint_1));

                }else{
                    passTheAnswer.onClick(false,answerArrayList.get(position));
                    holder.answer_cont_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.incorrect_answar_bg));
                    holder.choose_con_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.circl_bg_solid));
                    holder.choose_tv.setTextColor(context.getResources().getColor(R.color.splash_screen_gradint_1));
//                    for (int i=0;i<answerArrayList.size();i++)
//                    {
//                        Log.i("TAG","answerArrayList.get(position).getCorrectOrNo(): "+answerArrayList.get(i).getCorrectOrNo());
//                        Log.i("TAG","answerArrayList.get(position).getAnswer(): "+answerArrayList.get(i).getAnswer());
//                        Log.i("TAG","answerArrayList.get(position).getChara(): "+answerArrayList.get(i).getChara());
//                        Log.i("TAG","i: "+String.valueOf(i));
//
//                        if (answerArrayList.get(i).getCorrectOrNo()==true)
//                        {
//                            holder.answer_cont_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.correct_after_incorrect_answar_bg));
//                            holder.choose_con_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.correct_after_incorrect_answar_bg_solid));
//                            holder.choose_tv.setTextColor(context.getResources().getColor(R.color.white));
//                        }
//                    }
                }
                holder.choose_con_rl.setBackground(ContextCompat.getDrawable(context, R.drawable.circl_bg_solid));
                holder.choose_tv.setTextColor(context.getResources().getColor(R.color.splash_screen_gradint_1));
                holder.answer_tv.setTextColor(context.getResources().getColor(R.color.white));
            }
        });
    }

    public interface PassTheAnswer{
        //ArrayList<AnswersModelT> answerArrayList;
        void onClick(Boolean answer,AnswersModelT answersModelT);
    }

    private void fillText(ViewHolder holder, int position) {
        holder.choose_tv.setText(answerArrayList.get(position).getChara());
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