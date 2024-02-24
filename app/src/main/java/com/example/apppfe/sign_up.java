package com.example.apppfe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apppfe.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_up extends AppCompatActivity {

    private TextView gotosignin;
    private EditText email, password, confirmpswrd;
    private String emails, passwords, confirmpswrds;
    private Button btsignup;
    ////visibility
    boolean passwordvisibility;
    ////
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savesInstanceState) {
        super.onCreate(savesInstanceState);
        setContentView(R.layout.sign_up);

        gotosignin = findViewById(R.id.goToSignIn);
        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.passwordSignUp);
        confirmpswrd = findViewById((R.id.confirmpswrd));
        btsignup = findViewById(R.id.signUp);

        gotosignin.setOnClickListener(v -> {
            startActivity(new Intent(sign_up.this, sign_in.class));
        });

        firebaseAuth = FirebaseAuth.getInstance();

        btsignup.setOnClickListener(v -> {

            if (valide()) {

                firebaseAuth.createUserWithEmailAndPassword(emails, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendEmailVerification();
                        } else {
                            Toast.makeText(sign_up.this, "register failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

        });
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
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisibility = false;
                        } else {
                            // Pour afficher le mot de passe
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisibility = true;
                        }
                        password.setSelection(selection); // Rétablir la position du curseur après la modification
                        return true; // Indique que l'événement a été consommé
                    }
                }
                return false; // Indique que l'événement n'a pas été consommé
            }
        });
       confirmpswrd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2; // Position de l'icône à droite
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Vérifie si le toucher est sur l'icône à droite
                    if (event.getRawX() >= confirmpswrd.getRight() - confirmpswrd.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection =confirmpswrd.getSelectionEnd();
                        if (passwordvisibility) {
                            // Pour masquer le mot de passe
                            confirmpswrd.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
                            confirmpswrd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisibility = false;
                        } else {
                            // Pour afficher le mot de passe
                            confirmpswrd.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0);
                            confirmpswrd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisibility = true;
                        }
                        confirmpswrd.setSelection(selection); // Rétablir la position du curseur après la modification
                        return true; // Indique que l'événement a été consommé
                    }
                }
                return false; // Indique que l'événement n'a pas été consommé
            }
        });

    }

    private void sendEmailVerification() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                            senduserdata();
                        Toast.makeText(sign_up.this,"register fait avec succes svp verifier your mail",Toast.LENGTH_LONG).show();

                        firebaseAuth.signOut();
                        startActivity(new Intent(sign_up.this,acceuil.class));
                        finish();
                    }else{
                        Toast.makeText(sign_up.this,"echeque",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
    private void senduserdata(){
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference myref= firebaseDatabase .getReference("users");
        User user=new User(emails,passwords, confirmpswrds);
        myref.child(""+firebaseAuth.getUid()).setValue(user);


    }

    private boolean valide() {
        boolean res = false;
        emails = email.getText().toString();
        passwords = password.getText().toString();
        confirmpswrds = confirmpswrd.getText().toString();
        if (!validemail(emails)) {
            email.setError("Email invalide!!!");
        } else if (passwords.isEmpty() || passwords.length() < 6) {
            password.setError("Mot de passe invalide!!!");

        } else if (!confirmpswrds.equals(passwords)) {
            confirmpswrd.setError("Les mots de passe ne correspondent pas !!!");

        } else {
            res = true;
        }
        return res;
    }

    public boolean validemail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}