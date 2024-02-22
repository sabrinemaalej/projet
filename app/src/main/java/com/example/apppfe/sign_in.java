package com.example.apppfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_in extends AppCompatActivity {
    private TextView forgetPass;
    private TextView goToAcceuil;
    private Button signIn;
    private EditText emailSignIn;
    private EditText passwordSignIn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        emailSignIn = findViewById(R.id.emailSignIn);
        passwordSignIn = findViewById(R.id.passwordSignIn);
        forgetPass = findViewById(R.id.forgetPass);
        goToAcceuil = findViewById(R.id.goToSignUp); // Correction ici

        signIn = findViewById(R.id.signUp);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        // Vérifier si l'utilisateur est déjà connecté
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            startActivity(new Intent(sign_in.this, acceuil.class));
            finish();
        }

        // Lien vers l'activité d'accueil
        goToAcceuil.setOnClickListener(v -> {
            startActivity(new Intent(sign_in.this, acceuil.class));
        });

        // Écouteur pour le bouton de connexion
        signIn.setOnClickListener(v -> {
            String email = emailSignIn.getText().toString().trim();
            String password = passwordSignIn.getText().toString().trim();

            // Vérifier si les champs sont vides
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(sign_in.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                if (currentUser != null && currentUser.isEmailVerified()) {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(sign_in.this, acceuil.class));
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(sign_in.this, "Please verify your email's account", Toast.LENGTH_SHORT).show();
                                    firebaseAuth.signOut();
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(sign_in.this, "Please verify that your data is correct", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
