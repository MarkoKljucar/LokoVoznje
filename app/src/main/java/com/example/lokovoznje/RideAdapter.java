package com.example.lokovoznje;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder> {

    private List<Ride> rideData;
    @NonNull
    @Override
    public RideAdapter.RideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ridecard,parent,false);
        return new RideViewHolder(view);
    }
    public RideAdapter(List<Ride> rideData)
    {

        this.rideData = rideData;
    }

    @Override
    public void onBindViewHolder(@NonNull RideAdapter.RideViewHolder holder, int position) {
        Ride ride = rideData.get(position);
        holder.datum.setText(ride.getDate());
        holder.relacija.setText(ride.getRelation());
        holder.razlog.setText(ride.getCause());
        holder.udaljenost.setText(Integer.toString(ride.getDifference()));

    }

    public int getItemCount() {

        return rideData.size();
    }

    public class RideViewHolder extends RecyclerView.ViewHolder {
        TextView datum, relacija, razlog, udaljenost;
        public RideViewHolder(@NonNull View itemView) {
            super(itemView);
            datum = (TextView) itemView.findViewById(R.id.datumVoznje);
            relacija = (TextView) itemView.findViewById(R.id.relacijaVoznje);
            razlog = (TextView) itemView.findViewById(R.id.razlogVoznje);
            udaljenost = (TextView) itemView.findViewById(R.id.udaljenostVoznje);

        }

    }

}
