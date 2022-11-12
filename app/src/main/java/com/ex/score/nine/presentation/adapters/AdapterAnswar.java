package com.ex.score.nine.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ex.score.nine.R;
import com.ex.score.nine.domain.models.Scores;

import java.util.ArrayList;

public class AdapterAnswar extends RecyclerView.Adapter<AdapterAnswar.ViewHolder> {

    private final Context context;
    ArrayList<Scores> scoresArrayList;

    public AdapterAnswar
            (Context context, ArrayList<Scores> scoresArrayList) {
        this.context = context;
        this.scoresArrayList = scoresArrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_answar, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        fillText(holder, position);
    }


    private void fillText(ViewHolder holder, int position) {
        //holder.soccer_name.setText(scoresArrayList.get(position).getName());
        //holder.soccer_score.setText(scoresArrayList.get(position).getScore() + " " + context.getResources().getString(R.string.points));
    }


    @Override
    public int getItemCount() {
        return scoresArrayList.size();
    }


    public void filterList(ArrayList<Scores> filterNames) {
        this.scoresArrayList = filterNames;
        notifyDataSetChanged();
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