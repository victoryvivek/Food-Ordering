package com.example.dell.foodapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class PayItems extends AppCompatActivity {

    Button pay_btn;
    EditText pay_edittext;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_items);

        pay_btn=(Button)findViewById(R.id.pay_btn);
        pay_edittext=(EditText)findViewById(R.id.pay_edittext);

        pay_edittext.setText(Integer.toString(getIntent().getIntExtra("price",0)));
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float_window();
            }
        });
    }
    void float_window(){
        AlertDialog.Builder builder=new AlertDialog.Builder(PayItems.this)
                .setTitle("Do you want to Continue?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pay_btn.setText("Order Booked");
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);

        MenuItem item=menu.findItem(R.id.menu_logout);
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            item.setVisible(true);
        }else{
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                mAuth.signOut();
                Intent intent=new Intent(PayItems.this,MainActivity.class);
                startActivity(intent);
        }
        return true;
    }
}
