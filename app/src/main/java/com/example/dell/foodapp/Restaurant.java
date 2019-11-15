package com.example.dell.foodapp;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Restaurant extends AppCompatActivity {

    TextView hotel_name_textView;
    ImageView food_image;
    ListView menu_listview;
    Intent intent;
    //String hotel_name,food_image_url;
    int hotel_position;
    DatabaseReference mMenuDatabaseRef;
    FirebaseAuth mAuth;
    List<String> menu_items;
    List<Integer> price_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        //food_image=(ImageView)findViewById(R.id.food_imageview);
        menu_listview=(ListView)findViewById(R.id.restaurant_menu_listview);
        //hotel_name_textView=(TextView)findViewById(R.id.hotel_name_textview);
        mMenuDatabaseRef=FirebaseDatabase.getInstance().getReference("menu");
        menu_items=new ArrayList<>();
        price_items=new ArrayList<>();
        intent=getIntent();

        hotel_position=intent.getIntExtra("position",0);
        mMenuDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot postsnapshot = dataSnapshot.child(Integer.toString(hotel_position)).child("items");
                for (DataSnapshot postpost : postsnapshot.getChildren()){
                    menu_items.add(postpost.getValue(String.class));
                }
                postsnapshot = dataSnapshot.child(Integer.toString(hotel_position)).child("price");
                for (DataSnapshot postpost : postsnapshot.getChildren()){
                    price_items.add(postpost.getValue(Integer.class));
                }
                CustomAdapter customAdapter=new CustomAdapter();
                menu_listview.setAdapter(customAdapter);
                clickonitem();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        //hotel_name_textView.setText(intent.getStringExtra("hotel_name"));
        /*Picasso.get()
                .load(intent.getStringExtra("food_image"))
                .into(food_image);*/
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return price_items.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.items_display,null);

            TextView title_textView=(TextView)convertView.findViewById(R.id.item_title_textview);
            TextView price_textView=(TextView)convertView.findViewById(R.id.item_price_textview);

            title_textView.setText(menu_items.get(position));
            price_textView.setText(Integer.toString(price_items.get(position)));
            return convertView;
        }
    }
    void clickonitem() {
        menu_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int price = price_items.get(position);
                Intent intent = new Intent(Restaurant.this, PayItems.class);
                intent.putExtra("price",price);
                startActivity(intent);
            }
        });
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
                Intent intent=new Intent(Restaurant.this,MainActivity.class);
                startActivity(intent);
        }
        return true;
    }
}
