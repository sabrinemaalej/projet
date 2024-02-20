package com.example.apppfe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class sign_up extends AppCompatActivity {
    private TextView gotosignin;
    private EditText email,password,confirmpswrd;
    private Button btsignup;
    @Override
    protected void onCreate(Bundle savesInstanceState){
        super.onCreate(savesInstanceState);
        setContentView(R.layout.sign_up);
        email = findViewById(R.id.emailSignUp);
        password=findViewById(R.id.passwordSignUp);
        confirmpswrd=findViewById((R.id.confirmpswrd));

        gotosignin.setOnClickListener(v -> {
            startActivity(new Intent( sign_up.this,sign_in.class));
        });
    }

}
