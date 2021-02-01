package com.example.lokovoznje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RideListActivity extends AppCompatActivity {

    TextView registracija;
    List<Ride> rideData;
    public RideAdapter rideAdapter;
    RecyclerView recyclerRides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_list);
        registracija = findViewById(R.id.textViewRega);
        String rega = "Nepostojeca registracija";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            rega = extras.getString("id");
        }
        registracija.setText(rega);
        recyclerRides = (RecyclerView) findViewById(R.id.rideRecycler);
        recyclerRides.setHasFixedSize(true);
        recyclerRides.setLayoutManager(new LinearLayoutManager(this));
        String novaRega = (String) registracija.getText();
        rideData=new ArrayList<>();
        final DatabaseReference mm;
        mm = FirebaseDatabase.getInstance().getReference("Voznja");
        Query query1 = mm.orderByChild("vehicleId").equalTo(novaRega);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        Ride r = snapshot1.getValue(Ride.class);

                        rideData.add(r);

                    }
                    rideAdapter = new RideAdapter(rideData);
                    recyclerRides.setAdapter(rideAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    public void dodaj(View view) {
        Intent intent = new Intent(getApplicationContext(), RideActivity.class);
        intent.putExtra("rega", registracija.getText());
        startActivity(intent);

    }
}
