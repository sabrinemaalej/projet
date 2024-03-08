package com.example.apppfe;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class forgetpassword extends AppCompatActivity {
    private Button btnReset, btnGoBack;
    private EditText emailReset;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private String emailS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);

        btnGoBack = findViewById(R.id.btnGoBack);
        btnReset = findViewById(R.id.btnReset);
        emailReset = findViewById(R.id.emailReset);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnGoBack.setOnClickListener(v -> {
            startActivity(new Intent(forgetpassword.this, sign_in.class));
        });

        btnReset.setOnClickListener(v -> {
            emailS = emailReset.getText().toString().trim();
            if (!isValidEmail(emailS)) {
                emailReset.setError("Email is invalid!!");
            } else {
                progressDialog.setMessage("Please wait...!");
                progressDialog.show();

                firebaseAuth.sendPasswordResetEmail(emailS).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(forgetpassword.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                        startActivity(new Intent(forgetpassword.this, sign_in.class));
                    } else {
                        Toast.makeText(forgetpassword.this, "Failed !", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

