package com.example.dell.foodapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class AfterLogin extends AppCompatActivity {

    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        customSwipeAdapter=new CustomSwipeAdapter(this);
        viewPager.setAdapter(customSwipeAdapter);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
    }
}
