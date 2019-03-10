package com.example.dell.foodapp;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AfterLogin extends AppCompatActivity {

    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference mDatabaseRef, mDatabaseRefHotel, mDatabaseRefFood,mDatabaseRefComb;
    List<Upload> mUploads, mFoodUploads;
    List<HotelUpload> mHotelUploads;
    Timer timer;
    int current_pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        mUploads = new ArrayList<>();
        mFoodUploads = new ArrayList<>();
        mHotelUploads = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("offers");
        mDatabaseRefFood = database.getReference("food");
        mDatabaseRefHotel = database.getReference("hotel");
        mDatabaseRefComb=database.getReference();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                customSwipeAdapter = new CustomSwipeAdapter(AfterLogin.this, mUploads);
                viewPager.setAdapter(customSwipeAdapter);
                createSlideShow();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        mDatabaseRefComb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot postsnapshot= dataSnapshot.child("hotel");
                for(DataSnapshot postpost:postsnapshot.getChildren()){
                    HotelUpload hotelUpload=postpost.getValue(HotelUpload.class);
                    mHotelUploads.add(hotelUpload);
                }
                postsnapshot= dataSnapshot.child("food");
                for(DataSnapshot postpost:postsnapshot.getChildren()){
                    Upload upload=postpost.getValue(Upload.class);
                    mFoodUploads.add(upload);
                }
                recyclerViewAdapter = new RecyclerViewAdapter(AfterLogin.this, mFoodUploads,mHotelUploads);
                recyclerView.setLayoutManager(new LinearLayoutManager(AfterLogin.this));
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void createSlideShow() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (current_pos == mUploads.size()) {
                    current_pos = 0;
                }
                viewPager.setCurrentItem(current_pos++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 2500);
    }
}
