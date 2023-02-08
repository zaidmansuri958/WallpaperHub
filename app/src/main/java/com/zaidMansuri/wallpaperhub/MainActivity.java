
package com.zaidMansuri.wallpaperhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bnv;
    FirebaseAuth mAuth;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FirebaseMessaging.getInstance().subscribeToTopic("notification");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new Home()).commit();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView=findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        bnv=findViewById(R.id.bottomNav);
        mAuth=FirebaseAuth.getInstance();
        bnv.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bnv.add(new MeowBottomNavigation.Model(2,R.drawable.ic_trending));
        bnv.add(new MeowBottomNavigation.Model(3,R.drawable.ic_3d));
//        bnv.add(new MeowBottomNavigation.Model(4,R.drawable.ic_person));

        bnv.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bnv.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                int id= item.getId();
                Fragment temp=null;
                switch (id){
                    case 1:
                        temp=new Home();
                        break;
                    case 2:
                        temp=new Trending();
                        break;
                    case 3:
                        temp=new Fragment_3D();
                        break;                }
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame,temp);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        bnv.show(1,true);
    }
}