package com.example.lokovoznje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VehicleActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String[] tip = {"benzin", "diesel", "struja"};
    EditText txtRegistracija, txtTipMotora, txtPotrosnja;
    Button btnDodaj;
    DatabaseReference reff;
    Vehicle vehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        txtRegistracija = (EditText)findViewById(R.id.inptRegistracija);
        Spinner spin = (Spinner) findViewById(R.id.inptTipMotora);
        spin.setOnItemSelectedListener(this);
        txtPotrosnja = (EditText)findViewById(R.id.inptProsjek);
        btnDodaj = (Button)findViewById(R.id.spremiVoziloButton);
        vehicle = new Vehicle();
        reff = FirebaseDatabase.getInstance().getReference("Voznja");
        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float pot = Float.parseFloat(txtPotrosnja.getText().toString().trim());
                vehicle.setAverageFuel(pot);
                String tipmotora = spin.getSelectedItem().toString();
                vehicle.setEngineType(tipmotora);
                vehicle.setOwnerId("pero");
                vehicle.setRegistration(txtRegistracija.getText().toString().trim());
                vehicle.setEngineType(txtTipMotora.getText().toString().trim());
                reff.push().setValue(vehicle);

            }
        });
    }
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        Toast.makeText(getApplicationContext(),tip[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}