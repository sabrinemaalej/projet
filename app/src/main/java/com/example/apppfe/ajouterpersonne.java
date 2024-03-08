package com.example.apppfe;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ajouterpersonne extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button selectButton, enrgButton,listbt;
    private EditText codeEditText, nomEditText;
    private ImageView imageView;
    private Uri imageUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouterpersonne);

        // Initialize Firebase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Create a storage reference and database reference
        storageReference = storage.getReference("images");
        databaseReference = database.getReference("person");

        selectButton = findViewById(R.id.select);
        enrgButton = findViewById(R.id.enrg);
        codeEditText = findViewById(R.id.code);
        nomEditText = findViewById(R.id.nom);
        imageView = findViewById(R.id.img);
        listbt=findViewById(R.id.btlist);

        /////list
        listbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ajouterpersonne.this,liste_personne.class));
            }
        });

/////






        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });



        enrgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();
                                    String code = codeEditText.getText().toString().trim();
                                    String nom = nomEditText.getText().toString().trim();

                                    // Create a user object
                                    personne p = new personne(code, nom, downloadUrl);

                                    // Push the user object to Firebase Database
                                    databaseReference.push().setValue(p);

                                    Toast.makeText(ajouterpersonne.this, "Upload successful", Toast.LENGTH_LONG).show();
                                    codeEditText.setText("");
                                    nomEditText.setText("");
                                    imageView.setImageResource(android.R.color.transparent);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ajouterpersonne.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
