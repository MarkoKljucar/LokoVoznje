package com.example.lokovoznje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity{

    DatabaseReference reference;
    RecyclerView recyclerVehicles;
    List<Vehicle> vozilaData;
    private VehicleAdapter.VehicleClickListener listener;

    public VehicleAdapter vehicleAdapter;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerVehicles = (RecyclerView) findViewById(R.id.vehicleRecycler);
        recyclerVehicles.setHasFixedSize(true);
        recyclerVehicles.setLayoutManager(new LinearLayoutManager(this));
        vozilaData=new ArrayList<>();
        List<String> lista;

        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("Vehicle");
        Query query = nm.orderByChild("ownerId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        Vehicle l=npsnapshot.getValue(Vehicle.class);
                        String kljuc = npsnapshot.getKey();

                        vozilaData.add(l);

                    }
                    setOnClickListener();
                    vehicleAdapter =new VehicleAdapter(vozilaData, listener);
                    recyclerVehicles.setAdapter(vehicleAdapter);

                }
            }

            private void setOnClickListener() {
                listener = new VehicleAdapter.VehicleClickListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent = new Intent(getApplicationContext(), RideListActivity.class);
                        intent.putExtra("id", vozilaData.get(position).getRegistration());
                        intent.putExtra("pot", vozilaData.get(position).getAverageFuel());
                        intent.putExtra("tip", vozilaData.get(position).getEngineType());
                        startActivity(intent);
                    }
                };
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void logout(View View){

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }

    public void dodaj(View view) {
        startActivity(new Intent(getApplicationContext(), VehicleActivity.class));
    }
    
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}