package com.example.lokovoznje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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


    RecyclerView recyclerVehicles;
    List<Vehicle> vozilaData;
    private VehicleAdapter.VehicleClickListener listener;

    public VehicleAdapter vehicleAdapter;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Jeste li sigurni da želite obrisati vozilo?");
        builder.setMessage("Vozilo može sadržavati vožnje.");

        recyclerVehicles = (RecyclerView) findViewById(R.id.vehicleRecycler);
        recyclerVehicles.setHasFixedSize(true);
        recyclerVehicles.setLayoutManager(new LinearLayoutManager(this));
        vozilaData=new ArrayList<>();

        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("Vehicle");
        Query query = nm.orderByChild("ownerId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        Vehicle l=npsnapshot.getValue(Vehicle.class);

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

                    @Override
                    public void onDeleteClick(int position) {
                        builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query applesQuery = ref.child("Vehicle").orderByChild("registration").equalTo(vozilaData.get(position).getRegistration());
                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                                Query voznjeQuery = ref.child("Voznja").orderByChild("vehicleId").equalTo(vozilaData.get(position).getRegistration());
                                if(voznjeQuery != null){
                                    voznjeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot voznjeSnapshot: dataSnapshot.getChildren()) {
                                                voznjeSnapshot.getRef().removeValue();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                                else{
                                    finish();
                                    startActivity(getIntent());
                                }

                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("NE", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

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