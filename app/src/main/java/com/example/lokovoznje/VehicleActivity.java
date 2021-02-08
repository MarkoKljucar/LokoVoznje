package com.example.lokovoznje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
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
import java.util.regex.Pattern;

public class VehicleActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    EditText txtRegistracija, txtPotrosnja, txtNazivVozila;
    Button btnDodaj;
    DatabaseReference reff;
    Vehicle vehicle;
    List<String> registracije;
    public final Pattern REGISTRACIJA_PATTERN = Pattern.compile(
            "[a-zA-Z0-9]{2}" +
                    "-" +
                    "[a-zA-Z0-9]{3,4}" +
                    "-" +
                    "[a-zA-Z0-9]{1,2}"
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        txtRegistracija = (EditText)findViewById(R.id.inptRegistracija);
        txtRegistracija.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
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
                String rega = txtRegistracija.getText().toString().trim();
                String nazivVozila = txtNazivVozila.getText().toString().trim();
                float pot = 0.0f;
                if (txtPotrosnja.getText().toString().equals("") == false){
                    pot = Float.parseFloat(txtPotrosnja.getText().toString());
                }
                if (txtPotrosnja.getText().toString().equals("")){
                    txtPotrosnja.setError("Molimo unesite potrošnju vozila!");
                    return;
                }

                if(registracije.contains(rega))
                {
                    txtRegistracija.setError("Ta registracija postoji unesite neku drugu!");
                    return;
                }
                else if (rega.isEmpty())
                {
                    txtRegistracija.setError("Molim unesite registraciju vozila!");
                    return;
                }
                else if(nazivVozila.isEmpty())
                {
                    txtNazivVozila.setError("Molim unesite naziv vozila");
                    return;
                }
                else if(pot == 0){
                    txtPotrosnja.setError("Potrošnja ne može biti negativna vrijednost!");
                    return;
                }
                else if (!REGISTRACIJA_PATTERN.matcher(rega).matches()){
                    txtRegistracija.setError("Registracija nije u pravilnom obliku: npr. DA-321ABC-DA");
                    return;
                }
                else{
                    vehicle.setRegistration(rega);
                    vehicle.setEngineType(spinner.getSelectedItem().toString());
                    vehicle.setOwnerId(userId);
                    vehicle.setCarName(txtNazivVozila.getText().toString().trim());
                    vehicle.setAverageFuel(pot);

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