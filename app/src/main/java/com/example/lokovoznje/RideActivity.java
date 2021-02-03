package com.example.lokovoznje;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class RideActivity extends AppCompatActivity{
    EditText txtPocetna, txtZavrsna, txtRelacija, txtRazlog;
    Button btnDodaj;
    DatabaseReference reff;
    Ride ride;
    DatePicker picker;
    String datum;
    private int razlika;
    private int max1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        txtPocetna = (EditText)findViewById(R.id.inptPocetna);
        picker = (DatePicker) findViewById(R.id.inptDatum);
        txtZavrsna = (EditText)findViewById(R.id.inptZavrsnaKilometraza);
        txtRelacija = (EditText)findViewById(R.id.inptRelacija);
        btnDodaj = (Button)findViewById(R.id.btnDodajVoznju);
        txtRazlog = (EditText) findViewById(R.id.inptRazlog);
        Bundle extras = getIntent().getExtras();
        String registracija = extras.getString("rega");
        ArrayList<Integer> ogranicenjeKil = extras.getIntegerArrayList("kil");
        Log.d("list", ogranicenjeKil.toString());
        ride = new Ride();
        reff = FirebaseDatabase.getInstance().getReference("Voznja");


        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datum = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

                int poc = Integer.parseInt(txtPocetna.getText().toString().trim());
                int zav = Integer.parseInt(txtZavrsna.getText().toString().trim());
                razlika = zav - poc;
                int max = Collections.max(ogranicenjeKil);
                if (poc < max)
                {
                    txtPocetna.setError("Početna kilometraža ne može biti manja od prethodne početne kilometraže!");
                    return;
                }
                else{
                    ride.setVehicleId(registracija);
                    ride.setStartMilage(poc);
                    ride.setDate(datum);
                    ride.setEndMilage(zav);
                    ride.setRelation(txtRelacija.getText().toString().trim());
                    ride.setCause(txtRazlog.getText().toString().trim());
                    ride.setDifference(razlika);
                    reff.push().setValue(ride);
                    Intent intent2 = new Intent(getApplicationContext(), RideListActivity.class);
                    intent2.putExtra("id", registracija);
                    startActivity(intent2);

                    finish();
                }

            }
        });
    }


}