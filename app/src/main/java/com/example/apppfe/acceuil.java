package com.example.apppfe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class acceuil extends AppCompatActivity {
    private TextView goTohistorique,goToparametre,gotomaps,gotoajousuiv,gotoajoupersonne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil);

        goTohistorique = findViewById(R.id.historiqueacceuil);
        goToparametre = findViewById(R.id.parametreacceuil);
        gotomaps = findViewById(R.id.mapsacceuil);
        gotoajousuiv = findViewById(R.id.ajoutersuivacceuil);
       gotoajoupersonne= findViewById(R.id.ajouterperacceuil);


        goToparametre.setOnClickListener(v -> {
            startActivity(new Intent(acceuil.this,parametre.class));
        });
        goTohistorique.setOnClickListener(v -> {
            startActivity(new Intent(acceuil.this, historique.class));
        });
        gotomaps.setOnClickListener(v -> {
            startActivity(new Intent(acceuil.this, maps.class));
        });
        gotoajoupersonne.setOnClickListener(v -> {
            startActivity(new Intent(acceuil.this, ajouterpersonne.class));

        });
        gotoajousuiv.setOnClickListener(v -> {
            startActivity(new Intent(acceuil.this, sign_up.class));
        });
}}
