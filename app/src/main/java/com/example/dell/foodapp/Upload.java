package com.example.dell.foodapp;

public class Upload {
    private String imageurl;

    public Upload(){
        //Empty Constructor needed
    }

    public Upload(String imageurl){
        this.imageurl=imageurl;
    }
    public String getImageurl(){
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
