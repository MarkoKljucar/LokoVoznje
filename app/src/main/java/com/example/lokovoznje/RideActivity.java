package com.example.lokovoznje;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RideActivity extends AppCompatActivity {
    EditText txtPocetna, txtDatum, txtZavrsna, txtRelacija, txtRazlog;
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
        txtRazlog = (EditText) findViewById(R.id.inptRazlog);
        Bundle extras = getIntent().getExtras();
        String registracija = extras.getString("rega");
        ride = new Ride();
        reff = FirebaseDatabase.getInstance().getReference("Voznja");
        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int poc = Integer.parseInt(txtPocetna.getText().toString().trim());
                int zav = Integer.parseInt(txtZavrsna.getText().toString().trim());
                ride.setVehicleId(registracija);
                ride.setStartMilage(poc);
                ride.setDate(txtDatum.getText().toString().trim());
                ride.setEndMilage(zav);
                ride.setRelation(txtRelacija.getText().toString().trim());
                ride.setCause(txtRazlog.getText().toString().trim());
                reff.push().setValue(ride);
                Intent intent2 = new Intent(getApplicationContext(), RideListActivity.class);
                intent2.putExtra("id", registracija);
                startActivity(intent2);

                finish();
            }
        });
    }
}