package com.example.apppfe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_up extends AppCompatActivity {

    private TextView gotosignin;
    private EditText email, password, confirmpswrd;
    private String emails, passwords, confirmpswrds;
    private Button btsignup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savesInstanceState) {
        super.onCreate(savesInstanceState);
        setContentView(R.layout.sign_up);
        gotosignin=findViewById(R.id.goToSignIn);
        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.passwordSignUp);
        confirmpswrd = findViewById((R.id.confirmpswrd));
         btsignup=findViewById(R.id.signUp);

         progressDialog=new ProgressDialog(this);
         firebaseAuth =FirebaseAuth.getInstance();


        gotosignin.setOnClickListener(v -> {
            startActivity(new Intent(sign_up.this,sign_in.class));
        });


        btsignup.setOnClickListener(v -> {
//            progressDialog.setMessage("attant svp");
//            progressDialog.show();
            if (valide()){
//                Toast.makeText(this,"valid",Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
                firebaseAuth.createUserWithEmailAndPassword(emails.trim(),passwords.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                });
            }
        });
    }

    private boolean valide() {
        boolean res = false; // Initialiser à true car si tous les champs sont valides, res devrait rester true
        emails = email.getText().toString();
        passwords = password.getText().toString();
        confirmpswrds = confirmpswrd.getText().toString();
        if (!validemail(emails)) {
            email.setError("Email invalide!!!");// Si l'email est invalide, res est mis à false
        } else if (passwords.isEmpty() || passwords.length() < 6) {
            password.setError("Mot de passe invalide!!!");

        } else if (!confirmpswrds.equals(passwords)) {
            confirmpswrd.setError("Les mots de passe ne correspondent pas !!!");
             // Si les mots de passe ne correspondent pas, res est mis à false
        }else
        { res=true;}
        return res;
    }
    private boolean validemail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
