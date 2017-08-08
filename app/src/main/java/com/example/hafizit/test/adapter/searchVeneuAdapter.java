package com.example.hafizit.test.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hafizit.test.R;

import java.util.List;

/**
 * Created by Hafiz IT on 07/08/2017.
 */

public class searchVeneuAdapter extends RecyclerView.Adapter<searchVeneuAdapter.MyViewHolder> {
    private List<VeneuAdapter> datas;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView names, address;
        public MyViewHolder(View view){
            super(view);
            names = (TextView) view.findViewById(R.id.veneuName);
            address = (TextView) view.findViewById(R.id.veneuAddress);
        }
    }

    public searchVeneuAdapter(List<VeneuAdapter> data){

        this.datas = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_search_veneu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VeneuAdapter data = datas.get(position);
        holder.names.setText(data.getName());
        holder.address.setText(data.getAddress());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
