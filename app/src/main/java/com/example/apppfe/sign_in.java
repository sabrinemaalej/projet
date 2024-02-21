package com.example.apppfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
    private TextView goToSignup;
    private Button signIn;
    private EditText emailSignIn;
    private EditText passwordSignIn;
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

   @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        email = findViewById(R.id.emailSignIn);
        password = findViewById(R.id.passwordSignIn);

        forgetPass=findViewById(R.id.forgetPass);
        goToSignup=findViewById(R.id.emailSignIn);
        signIn= findViewById(R.id.signUp);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        goToSignup.setOnClickListener(v -> {
            startActivity((new Intent(sign_in.this,sign_up.class)));
        });


        signIn.setOnClickListener(v -> {
            progressDialog.setMessage("please waite...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        verifyEmail();
                        progressDialog.dismiss();
                    }else{
                        Toast.makeText(sign_in.this,"please verify that your data is correct",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        });
    }

    private void verifyEmail() {
        FirebaseUser connectedUser = firebaseAuth.getCurrentUser();
        boolean isEmailFlag = connectedUser.isEmailVerified();
        if (isEmailFlag){
            startActivity(new Intent(sign_in.this,acceuil.class));
        }else{
            Toast.makeText(this,"Please verify your email's account",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}