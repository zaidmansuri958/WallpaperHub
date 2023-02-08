package com.zaidMansuri.wallpaperhub;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home extends Fragment {
    RecyclerView recycle,main_recycle;
    bannerAdapter adapter;
    main_adapter main_adapter;
    ShimmerFrameLayout simmer;
    LinearLayout simmerLayout;
    DatabaseReference databaseReference;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Home() {
    }

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context=container.getContext();
        View root= inflater.inflate(R.layout.fragment_home, container, false);
        TextView text=root.findViewById(R.id.text);
        recycle=root.findViewById(R.id.recycle);
        main_recycle=root.findViewById(R.id.mainRecycle);
        simmer=root.findViewById(R.id.shimmer_view_container);
        simmerLayout=root.findViewById(R.id.simmerLayout);
//        Banner recycle

        ArrayList<TopbannerModal> arrayList = new ArrayList<TopbannerModal>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("TopBanner");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    TopbannerModal obj = ds.getValue(TopbannerModal.class);
                    arrayList.add(new TopbannerModal(obj.getImage(), obj.getName()));
                }
                catagoryAdapter arrayAdapter = new catagoryAdapter(arrayList, context);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
                recycle.setAdapter(arrayAdapter);
                recycle.setLayoutManager(layoutManager);
                text.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        Main recycle set


        main_recycle=root.findViewById(R.id.mainRecycle);
        ArrayList<main_model> array = new ArrayList<main_model>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("RecentlyAdded");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    main_model obj = ds.getValue(main_model.class);
                    array.add(new main_model(obj.getImage(),obj.getName()));
                }
                main_adapter arrayAdapter = new main_adapter(array, context);
                GridLayoutManager layout = new GridLayoutManager(context,3);
                main_recycle.setAdapter(arrayAdapter);
                main_recycle.setLayoutManager(layout);
                simmer.stopShimmer();
                simmerLayout.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }

}