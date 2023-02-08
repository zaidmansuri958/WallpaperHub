package com.zaidMansuri.wallpaperhub;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_3D extends Fragment {
    RecyclerView recycle;
    main_adapter adapter;
    DatabaseReference databaseReference;
    ShimmerFrameLayout simmer;
    LinearLayout simmerLayout;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Fragment_3D() {

    }
    public static Fragment_3D newInstance(String param1, String param2) {
        Fragment_3D fragment = new Fragment_3D();
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
        View root=inflater.inflate(R.layout.fragment_3_d, container, false);
        Context context=container.getContext();
        simmer=root.findViewById(R.id.shimmer_view_container);
        simmerLayout=root.findViewById(R.id.simmerLayout);
        recycle=root.findViewById(R.id.recycle);
        ArrayList<main_model> arrayList = new ArrayList<main_model>();
        databaseReference = FirebaseDatabase.getInstance().getReference("3D");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    main_model obj = ds.getValue(main_model.class);
                    arrayList.add(new main_model(obj.getImage(),obj.getName()));
                }
                main_adapter arrayAdapter = new main_adapter(arrayList, context);
                GridLayoutManager layout = new GridLayoutManager(context,3);
                recycle.setAdapter(arrayAdapter);
                recycle.setLayoutManager(layout);
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