package com.example.dell.foodapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    Context context;
    List<Upload> mFoodUploads;
    List<HotelUpload> mHotelUploads;

    public RecyclerViewAdapter(Context context, List<Upload> mFoodUploads,List<HotelUpload>mHotelUploads){
        this.context=context;
        this.mFoodUploads=mFoodUploads;
        this.mHotelUploads=mHotelUploads;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.my_row,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        Upload uploadCurrent=mFoodUploads.get(i);
        HotelUpload hotelCurrent=mHotelUploads.get(i);
        Picasso.get()
                .load(uploadCurrent.getImageurl())
                .into(recyclerViewHolder.restaurant_imageview_);
        recyclerViewHolder.title_textview.setText(hotelCurrent.getName());
        recyclerViewHolder.price_textview.setText(Double.toString(hotelCurrent.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mFoodUploads.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView price_textview,title_textview;
        ImageView restaurant_imageview_;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            price_textview=(TextView)itemView.findViewById(R.id.price_textview);
            title_textview=(TextView)itemView.findViewById(R.id.title_textview);
            restaurant_imageview_=(ImageView) itemView.findViewById(R.id.restaurant_imageview);
        }
    }
}
