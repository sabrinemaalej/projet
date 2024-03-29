package com.example.apppfe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_in extends AppCompatActivity {

    private TextView goToSignUp, goToForgetPass;
    private EditText email, password;
    private Button btnSignIn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String emailS, passwordS;
    //// visibility
    boolean passwordvisibility;
    ////
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        goToSignUp = findViewById(R.id.goToSignUp);
        goToForgetPass = findViewById(R.id.goToForgetPassword);
        email = findViewById(R.id.emailSignIn);
        password = findViewById(R.id.passwordSignIn);
        btnSignIn = findViewById(R.id.btsignIn);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        SharedPreferences preferences = getSharedPreferences("checkBox", MODE_PRIVATE);
        boolean resCheckBox = preferences.getBoolean("remember", false);
        if (resCheckBox && firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(sign_in.this, acceuil.class));
            finish();
        }

        goToSignUp.setOnClickListener(v -> {
            startActivity(new Intent(sign_in.this, sign_up.class));
        });

        goToForgetPass.setOnClickListener(v -> {
            startActivity(new Intent(sign_in.this, forgetpassword.class));
        });

        btnSignIn.setOnClickListener(v -> {
            emailS = email.getText().toString().trim();
            passwordS = password.getText().toString().trim();
            if (!isValidEmail(emailS)) {
                email.setError("Email is invalid!!");
            } else if (passwordS.length() < 6) {
                password.setError("Password must be at least 6 characters");
            } else {
                login(emailS, passwordS);
            }
        });

        //// visibility
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2; // Position de l'icône à droite
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Vérifie si le toucher est sur l'icône à droite
                    if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordvisibility) {
                            // Pour masquer le mot de passe
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
                            password.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                            passwordvisibility = false;
                        } else {
                            // Pour afficher le mot de passe
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0);
                            password.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                            passwordvisibility = true;
                        }
                        password.setSelection(selection); // Rétablir la position du curseur après la modification
                        return true; // Indique que l'événement a été consommé
                    }
                }
                return false; // Indique que l'événement n'a pas été consommé
            }
        });
    }

    private void login(String emailS, String passwordS) {
        progressDialog.setMessage("Please wait...!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(emailS, passwordS).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                checkEmailVerification();
            } else {
                Toast.makeText(this, "Sign in failed!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void checkEmailVerification() {
        FirebaseUser loggedUser = firebaseAuth.getCurrentUser();
        Log.d("myTag", "this is my message");  // Ajout du point-virgule ici
        if (loggedUser != null) {
            if (loggedUser.isEmailVerified()) {
                String userEmail = loggedUser.getEmail();
                if (compareStrings(userEmail, "sabrine.maalej2001@gmail.com")) {
                    startActivity(new Intent(sign_in.this, acceuil.class));
                } else if (!compareStrings(userEmail, "sabrine.maalej2001@gmail.com")) {
                    startActivity(new Intent(sign_in.this, acceuil_suiveur.class));
                }
            } else {
                // Si l'email n'est pas vérifié, afficher un message à l'utilisateur
                Toast.makeText(sign_in.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                progressDialog.dismiss();
            }
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
            progressDialog.dismiss();
        }
    }


    private boolean compareStrings(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }


    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
