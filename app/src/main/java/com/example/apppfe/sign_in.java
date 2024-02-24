package com.example.apppfe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_in extends AppCompatActivity {

    private TextView goToSignUp,goToForgetPass;
    private EditText email,password;
    private Button btnSignIn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String emailS,passwordS;
////visibility
    boolean passwordvisibility;
    ////
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private CheckBox rememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        goToSignUp = findViewById(R.id.goToSignUp);
        goToForgetPass = findViewById(R.id.goToForgetPassword);
        email = findViewById(R.id.emailSignIn);
        password = findViewById(R.id.passwordSignIn);
        btnSignIn = findViewById(R.id.btsignIn);
        rememberMe = findViewById(R.id.rememberMe);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        SharedPreferences preferences = getSharedPreferences("checkBox", MODE_PRIVATE);
        boolean resCheckBox = preferences.getBoolean("remember", false);

        if(resCheckBox && firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(sign_in.this, acceuil.class));
            finish();
        }
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkBox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("remember", true);
                    editor.apply();
                }else if(!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkBox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("remember", false);
                    editor.apply();
                }
            }
        });

        goToSignUp.setOnClickListener(v -> {
            startActivity(new Intent(sign_in.this,sign_up.class));
        });

        goToForgetPass.setOnClickListener(v -> {
            startActivity(new Intent(sign_in.this,forgetpassword.class));
        });

        btnSignIn.setOnClickListener(v -> {
            emailS = email.getText().toString().trim();
            passwordS = password.getText().toString().trim();
            if (!isValidEmail(emailS)){
                email.setError("Email is invalid!!");
            } else if (passwordS.length() < 4) {
                password.setError("Password is invalid");
            }else {
                login(emailS,passwordS);
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

///
    }

    private void login(String emailS, String passwordS) {
        progressDialog.setMessage("Please wait...!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(emailS,passwordS).addOnCompleteListener(task -> {
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
        if (loggedUser != null) {
            if (loggedUser.isEmailVerified()){
                finish();
                startActivity(new Intent(sign_in.this,acceuil.class));
                progressDialog.dismiss();
            }else {
                Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                progressDialog.dismiss();
            }
        }
    }

    private boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}