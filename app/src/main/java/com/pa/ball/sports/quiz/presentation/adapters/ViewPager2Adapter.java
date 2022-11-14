package com.pa.ball.sports.quiz.presentation.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pa.ball.sports.quiz.R;
import com.pa.ball.sports.quiz.data.ListResponse;
import com.pa.ball.sports.quiz.domain.models.Ads;


public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {
    private Context ctx;

    PassAdsDetails passAdsDetails;

    public ViewPager2Adapter(Context ctx, PassAdsDetails passAdsDetails) {
        this.ctx = ctx;
        this.passAdsDetails = passAdsDetails;
    }

    // This method returns our layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.images_holder, parent, false);
        return new ViewHolder(view);
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // This will set the images in imageview
        //holder.images.setImageResource(images[position]);
        //
        Glide.with(holder.images)
                .load(ListResponse.adsArrayList.get(position).getImage_path())
                .fitCenter()
                .into(holder.images);

        final int ppppp = position;
        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passAdsDetails.onClickedAdsDetails(ListResponse.adsArrayList.get(ppppp));
            }
        });
    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return ListResponse.adsArrayList.size();
    }

    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.images);
        }
    }

    public interface PassAdsDetails {
        void onClickedAdsDetails(Ads adsDetails);
    }
}
