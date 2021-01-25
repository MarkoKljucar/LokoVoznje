package com.example.lokovoznje;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    Context context;
    ArrayList<Vehicle> vozila;

    public VehicleAdapter(Context c, ArrayList<Vehicle> v)
    {
        context = c;
        vozila = v;
    }
    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VehicleViewHolder(LayoutInflater.from(context).inflate(R.layout.vehiclecard,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        holder.registracija.setText(vozila.get(position).getRegistration());
        holder.vrstaVozila.setText(vozila.get(position).getCarName());
        holder.tip.setText(vozila.get(position).getEngineType());
    }

    @Override
    public int getItemCount() {
        return vozila.size();
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder
    {
        TextView registracija, vrstaVozila, tip;
        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            registracija = (TextView) itemView.findViewById(R.id.registracija);
            vrstaVozila = (TextView) itemView.findViewById(R.id.vrsta);
            tip = (TextView) itemView.findViewById(R.id.tip);
        }
    }
}
