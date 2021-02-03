package com.example.lokovoznje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RideListActivity extends AppCompatActivity {

    TextView registracija;
    List<Ride> rideData;
    TextView predenikil;
    TextView subvenciran;
    TextView potrosnja;
    public RideAdapter rideAdapter;
    RecyclerView recyclerRides;
    public int razlikavoznje;
    float potrosnjaVozila;
    float cijenaGoriva;
    float potrosnjaVozila1;
    List<Integer> ogrKilometri;
    ArrayList<Integer> posaljiKil;
    Float potrosnja1 = 0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_list);
        registracija = findViewById(R.id.textViewRega);
        predenikil = findViewById(R.id.prijedeniKil);
        subvenciran = findViewById(R.id.subvenciraniTrosak);
        potrosnja = findViewById(R.id.realniTros);
        String rega = "Nepostojeca registracija";
        String tip = "Nepostojeci tip!";


        Bundle extras = getIntent().getExtras();
        
        if (extras != null) {
            rega = extras.getString("id");
            potrosnja1 = extras.getFloat("pot");
            tip = extras.getString("tip");

        }
        cijenaGoriva = 9.1F;
        Log.d("pero", tip);
        registracija.setText(rega);
        recyclerRides = (RecyclerView) findViewById(R.id.rideRecycler);

        recyclerRides.setHasFixedSize(true);
        recyclerRides.setLayoutManager(new LinearLayoutManager(this));
        String novaRega = (String) registracija.getText();

        //String test = Float.toString(potrosnjaVozila1);
        //potrosnja.setText(test + "kn");
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
                        String kilometri = Integer.toString(r.getDifference());
                        int broj = Integer.parseInt(kilometri);
                        Float statistika = ((potrosnjaVozila / 100) * razlikavoznje) * cijenaGoriva;
                        Float statistika2 = (potrosnja1 / 100);
                        //Log.d("cyka1", potrosnja1.toString());

                        rideData.add(r);
                        razlikavoznje += broj;
                        Float statistika3 = statistika2 * razlikavoznje;
                        Float statisitka4 = statistika3 * cijenaGoriva;
                        potrosnjaVozila = statisitka4;
                        Log.d("cyka", statisitka4.toString());
                        int sub = razlikavoznje * 2;
                        ogrKilometri = new ArrayList<Integer>() {{
                            add(r.getEndMilage());
                        } };


                        //Integer kilometriRazlike = r.getDifference();
                        //ogrKilometri.add(kilometriRazlike);
                        //ogrKilometri.add(kilometriRazlike);
                        String sub1 = Integer.toString(sub);
                        String s = Integer.toString(razlikavoznje);

                        //String rezultat = Float.toString(statistika);
                        //potrosnja.setText(rezultat + "kn");
                        predenikil.setText(s + "km");
                        subvenciran.setText(sub1 + "kn");
                        float rounded = (float) Math.round(potrosnjaVozila * 100) / 100;
                        potrosnja.setText(rounded + "kn");

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
        //intent.putIntegerArrayListExtra("kil", ogrKilometri);
        intent.putIntegerArrayListExtra("kil", (ArrayList<Integer>) ogrKilometri );
        startActivity(intent);

    }
}
