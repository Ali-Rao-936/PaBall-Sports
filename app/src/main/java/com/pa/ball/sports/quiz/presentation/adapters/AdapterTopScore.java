package com.pa.ball.sports.quiz.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.pa.ball.sports.quiz.R;
import com.pa.ball.sports.quiz.domain.models.Scores;

import java.util.ArrayList;

public class AdapterTopScore extends RecyclerView.Adapter<AdapterTopScore.ViewHolder> {

    private final Context context;
    ArrayList<Scores> scoresArrayList;

    public AdapterTopScore
            (Context context, ArrayList<Scores> scoresArrayList) {
        this.context = context;
        this.scoresArrayList = scoresArrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_top_score, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        fillText(holder, position);
    }


    private void fillText(ViewHolder holder, int position) {
        holder.soccer_po.setText(String.valueOf(position + 1));
        holder.soccer_name.setText(scoresArrayList.get(position).getName());
        holder.soccer_score.setText(scoresArrayList.get(position).getScore() + " " + context.getResources().getString(R.string.points));
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
        TextView soccer_po, soccer_name, soccer_score;
        RelativeLayout soccer_cover_rl, space;

        @SuppressLint("WrongViewCast")
        public ViewHolder(View itemView) {
            super(itemView);
            soccer_po = itemView.findViewById(R.id.soccer_po);
            soccer_score = itemView.findViewById(R.id.soccer_score);
            soccer_name = itemView.findViewById(R.id.soccer_name);
            soccer_cover_rl = itemView.findViewById(R.id.soccer_cover_rl);
        }
    }
}