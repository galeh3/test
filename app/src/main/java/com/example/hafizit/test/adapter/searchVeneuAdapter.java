package com.example.hafizit.test.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.hafizit.test.R;

import java.util.List;

/**
 * Created by Hafiz IT on 07/08/2017.
 */

public class searchVeneuAdapter extends RecyclerView.Adapter<searchVeneuAdapter.MyViewHolder> {
    private List<VeneuAdapter> datas;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView names, address;
        public ImageView image;
        public MyViewHolder(View view){
            super(view);
            names = (TextView) view.findViewById(R.id.veneuName);
            address = (TextView) view.findViewById(R.id.veneuAddress);
            image = (ImageView) view.findViewById(R.id.veneuImage);
        }
    }

    public searchVeneuAdapter(Context cx, List<VeneuAdapter> data){
        mContext = cx;
        this.datas = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_search_veneu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        VeneuAdapter data = datas.get(position);
        holder.names.setText(data.getName());
        holder.address.setText(data.getAddress());
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        ImageRequest imageRequest = new ImageRequest(data.getImage(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.image.setImageBitmap(response);
            }
        },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP, // Image scale type
                Bitmap.Config.RGB_565, //Image decode configuration
                new Response.ErrorListener() { // Error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with error response
                        error.printStackTrace();
//                        Snackbar.make(mCLayout,"Error",Snackbar.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(imageRequest);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
