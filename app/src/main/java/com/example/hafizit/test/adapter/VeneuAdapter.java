package com.example.hafizit.test.adapter;

/**
 * Created by Hafiz IT on 07/08/2017.
 */

public class VeneuAdapter {
    String name, address, image;

    public  VeneuAdapter(){

    }

    public VeneuAdapter(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }
}
