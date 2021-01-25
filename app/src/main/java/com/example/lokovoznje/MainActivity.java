package com.example.lokovoznje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerVehicles;
    ArrayList<Vehicle> vehicleList;
    VehicleAdapter vehicleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        reference = FirebaseDatabase.getInstance().getReference().child("Vozila");
        recyclerVehicles = (RecyclerView) findViewById(R.id.vehicleRecycler);
        recyclerVehicles.setLayoutManager(new LinearLayoutManager(this));
        vehicleList = new ArrayList<Vehicle>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren() ){
                    Vehicle v = dataSnapshot.getValue(Vehicle.class);
                    vehicleList.add(v);
                }
                vehicleAdapter = new VehicleAdapter(MainActivity.this, vehicleList);
                recyclerVehicles.setAdapter(vehicleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Nešto nije uredu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout(View View){

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        finish();
    }
}