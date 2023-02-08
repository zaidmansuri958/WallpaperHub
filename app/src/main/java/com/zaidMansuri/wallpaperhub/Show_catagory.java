package com.zaidMansuri.wallpaperhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Show_catagory extends AppCompatActivity {
    main_adapter main_adapter;
    RecyclerView recycle;
    TextView name;
    String catagory;
    ImageView back;
    FirebaseRecyclerOptions<main_model> option;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ShimmerFrameLayout simmer;
    LinearLayout simmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_catagory);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        catagory = intent.getStringExtra("catagory_name");
        name = findViewById(R.id.name);
        back = findViewById(R.id.back);
        simmer=findViewById(R.id.shimmer_view_container);
        simmerLayout=findViewById(R.id.simmerLayout);
        name.setText(catagory);
        mAuth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        recycle = findViewById(R.id.recycle);
        GridLayoutManager layout = new GridLayoutManager(Show_catagory.this, 3);
        recycle.setLayoutManager(layout);

        if (catagory.equals("like")) {
            ArrayList<main_model> arrayList = new ArrayList<main_model>();
            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(mAuth.getUid().toString()).child(catagory);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        main_model obj = ds.getValue(main_model.class);
                        arrayList.add(new main_model(obj.getImage(),obj.getName()));
                    }
                    main_adapter arrayAdapter = new main_adapter(arrayList, Show_catagory.this);
                    GridLayoutManager layout = new GridLayoutManager(Show_catagory.this,3);
                    recycle.setAdapter(arrayAdapter);
                    recycle.setLayoutManager(layout);
                    simmer.stopShimmer();
                    simmerLayout.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            ArrayList<main_model> arrayList = new ArrayList<main_model>();
            databaseReference = FirebaseDatabase.getInstance().getReference(catagory);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        main_model obj = ds.getValue(main_model.class);
                        arrayList.add(new main_model(obj.getImage(),obj.getName()));
                    }
                    main_adapter arrayAdapter = new main_adapter(arrayList, Show_catagory.this);
                    GridLayoutManager layout = new GridLayoutManager(Show_catagory.this,3);
                    recycle.setAdapter(arrayAdapter);
                    recycle.setLayoutManager(layout);
                    simmer.stopShimmer();
                    simmerLayout.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

}