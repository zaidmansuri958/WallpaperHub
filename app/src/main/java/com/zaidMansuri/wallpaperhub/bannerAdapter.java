package com.zaidMansuri.wallpaperhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class bannerAdapter extends FirebaseRecyclerAdapter<TopbannerModal, bannerAdapter.viewholder> {
    Context context;
    public bannerAdapter(@NonNull FirebaseRecyclerOptions<TopbannerModal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull TopbannerModal model) {
        Glide.with(holder.image.getContext()).load(model.getImage()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdRequest adRequest = new AdRequest.Builder().build();
                InterstitialAd.load(context, "ca-app-pub-9829502421511765/9148165779", adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                interstitialAd.show((Activity)context);
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                            }
                        });
                Intent intent = new Intent(holder.image.getContext(), Show_catagory.class);
                intent.putExtra("catagory_name", model.getName());
                holder.image.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_banner_layout, parent, false);
        return new viewholder(inflater);
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView image;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.bannerImage);
        }
    }
}
