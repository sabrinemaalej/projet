package com.example.apppfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class liste_personne extends AppCompatActivity {


    private  RecyclerView recyclerView;
    private ArrayList<personne> list;
    private myadapter adapter;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("person");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_personne);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        adapter=new myadapter(this,list);
        recyclerView.setAdapter(adapter);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
               for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                   personne p=dataSnapshot.getValue(personne.class);
                   list.add(p);
               }
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(liste_personne.this, "FAILED", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
