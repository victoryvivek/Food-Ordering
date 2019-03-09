package com.example.dell.foodapp;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomSwipeAdapter extends PagerAdapter {
    //private int[] image_resources;
    private Context ctx;
    private LayoutInflater layoutInflater;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;

    public CustomSwipeAdapter(Context ctx) {
        this.ctx = ctx;
        mStorageRef = FirebaseStorage.getInstance().getReference("offers");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("offers");

        mUploads=new ArrayList<>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload =postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getCount() {
        return mUploads.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (LinearLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.swipe_imageview);
        Upload uploadCurrent=mUploads.get(position);
        //imageView.setImageResource(mUploads.get(position));
        Picasso.with(ctx)
                .load(uploadCurrent.getImageurl())
                .fit()
                .centerCrop()
                .into(imageView);
        container.addView(item_view);
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = ctx.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
}
