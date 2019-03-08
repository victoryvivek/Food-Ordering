package com.example.dell.foodapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AfterLogin extends AppCompatActivity {

    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        adapter=new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);
    }
}
