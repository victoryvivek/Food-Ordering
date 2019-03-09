package com.example.dell.foodapp;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference mDatabaseRef;
    List<Upload> mUploads;
    Timer timer;
    int current_pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        mUploads = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("offers");

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        //recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                Toast.makeText(AfterLogin.this, "in listener " + Integer.toString(mUploads.size()), Toast.LENGTH_SHORT).show();
                customSwipeAdapter = new CustomSwipeAdapter(AfterLogin.this, mUploads);
                viewPager.setAdapter(customSwipeAdapter);
                createSlideShow();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
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
