package com.example.apppfe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class acceuil_suiveur extends AppCompatActivity {
    private TextView goTohistorique2,goToparametre2,gotomaps2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil_suiveur);

        goTohistorique2 = findViewById(R.id.histo2);
        goToparametre2 = findViewById(R.id.parametre2);
        gotomaps2= findViewById(R.id.mapsid2);


        goToparametre2.setOnClickListener(v -> {
            startActivity(new Intent(acceuil_suiveur.this,parametre.class));
        });
//        golistepersonne.setOnClickListener(v -> {
//            startActivity(new Intent(acceuil.this,liste_personne.class));
//        });
        goTohistorique2.setOnClickListener(v -> {
            startActivity(new Intent(acceuil_suiveur.this, historique.class));
        });
        gotomaps2.setOnClickListener(v -> {
            startActivity(new Intent(acceuil_suiveur.this, maps.class));
        });


    }
}
