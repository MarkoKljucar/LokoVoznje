package com.example.lokovoznje;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {


    Context context;
    private List<Vehicle> vozilaData;
    private VehicleClickListener listener;

    public VehicleAdapter(List<Vehicle> vozilaData, VehicleClickListener vehiclelistener)
    {
        this.listener = vehiclelistener;
        this.vozilaData = vozilaData;
    }
    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.vehiclecard,parent,false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        Vehicle voz = vozilaData.get(position);
        String owner = voz.getOwnerId();
        holder.registracija.setText(voz.getRegistration());
        holder.vrstaVozila.setText(voz.getCarName());
        holder.tip.setText(voz.getEngineType());

    }

    @Override
    public int getItemCount() {

        return vozilaData.size();
    }


    public class VehicleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView registracija, vrstaVozila, tip;
        Vehicle vehicle;
        ImageView mDeleteImage;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            registracija = (TextView) itemView.findViewById(R.id.registracija);
            vrstaVozila = (TextView) itemView.findViewById(R.id.naziv);
            tip = (TextView) itemView.findViewById(R.id.tip);
            mDeleteImage = (ImageView) itemView.findViewById(R.id.img_delete);
            itemView.setOnClickListener(this);
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int postion = getAdapterPosition();
                        if(postion!= RecyclerView.NO_POSITION){
                            listener.onDeleteClick(postion);
                        }
                    }
                }
            });

        }


        @Override
        public void onClick(View v) {

            listener.onClick(v, getAdapterPosition());
        }
    }
    public interface VehicleClickListener{
        void onDeleteClick(int position);
        void onClick(View v, int postion);
    }




}
