package com.example.lokovoznje;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RideActivity extends AppCompatActivity {
    EditText txtPocetna, txtDatum, txtZavrsna, txtRelacija;
    Button btnDodaj;
    DatabaseReference reff;
    Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        txtPocetna = (EditText)findViewById(R.id.inptPocetna);
        txtDatum = (EditText)findViewById(R.id.inptDatum);
        txtZavrsna = (EditText)findViewById(R.id.inptZavrsnaKilometraza);
        txtRelacija = (EditText)findViewById(R.id.inptRelacija);
        btnDodaj = (Button)findViewById(R.id.btnDodajVoznju);
        ride = new Ride();
        reff = FirebaseDatabase.getInstance().getReference("Voznja");
        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int poc = Integer.parseInt(txtPocetna.getText().toString().trim());
                int zav = Integer.parseInt(txtZavrsna.getText().toString().trim());
                ride.setVehicleId("novavoznja1");
                ride.setStartMilage(poc);
                ride.setDate(txtDatum.getText().toString().trim());
                ride.setEndMilage(zav);
                ride.setRelation(txtRelacija.getText().toString().trim());
                reff.push().setValue(ride);

            }
        });
    }
}