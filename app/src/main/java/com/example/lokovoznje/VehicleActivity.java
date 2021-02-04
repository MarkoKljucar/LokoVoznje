package com.example.lokovoznje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class VehicleActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    EditText txtRegistracija, txtTipMotora, txtPotrosnja, txtNazivVozila;
    Button btnDodaj;
    DatabaseReference reff;
    Vehicle vehicle;
    List<String> registracije;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        txtRegistracija = (EditText)findViewById(R.id.inptRegistracija);
        txtPotrosnja = (EditText)findViewById(R.id.inptProsjek);
        btnDodaj = (Button)findViewById(R.id.spremiVoziloButton);
        txtNazivVozila = (EditText) findViewById(R.id.inptNazivVozila);
        registracije = new ArrayList<>();
        vehicle = new Vehicle();
        reff = FirebaseDatabase.getInstance().getReference("Vehicle");
        Spinner spinner = (Spinner) findViewById(R.id.inptTipMotora);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipovi_motora, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                        Vehicle v = snapshot1.getValue(Vehicle.class);
                        registracije.add(v.getRegistration());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float pot = Float.parseFloat(txtPotrosnja.getText().toString().trim());
                vehicle.setAverageFuel(pot);
                String rega = txtRegistracija.getText().toString().trim();
                if(registracije.contains(rega))
                {
                    txtRegistracija.setError("Ta registracija postoji unesite neku drugu!");
                    return;
                }
                else{
                    vehicle.setRegistration(rega);
                    vehicle.setEngineType(spinner.getSelectedItem().toString());
                    vehicle.setOwnerId(userId);
                    vehicle.setCarName(txtNazivVozila.getText().toString().trim());

                    reff.push().setValue(vehicle);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

            }
        });
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}