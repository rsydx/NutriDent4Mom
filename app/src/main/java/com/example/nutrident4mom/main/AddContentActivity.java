package com.example.nutrident4mom.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nutrident4mom.R;
import com.example.nutrident4mom.model.Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddContentActivity extends AppCompatActivity {

    EditText txtTitle, txtContent;
    Button BtnAdd, BtnBack,BtnImage;
    ProgressBar progress;
    ImageView ImgView;
    private Uri imageUri;
    Model model = new Model();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("students");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);

        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        BtnImage = findViewById(R.id.BtnImage);


        BtnAdd = findViewById(R.id.BtnAdd);
        BtnBack = findViewById(R.id.BtnBack);
        ImgView = findViewById(R.id.ImgView);
        progress = findViewById(R.id.progress);

        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                clearAll();
            }
        });

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });
        BtnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri !=null){
                    uploadToFirebase(imageUri);
                }else{
                    Toast.makeText(AddContentActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis()+"."+ getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        model.setPurl(uri.toString());
//                        String modelId = root.push().getKey();
//                        root.child(modelId).setValue(model);
                        progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddContentActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progress.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progress.setVisibility(View.INVISIBLE);
                Toast.makeText(AddContentActivity.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            ImgView.setImageURI(imageUri);

        }
    }

    private void insertData(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", txtTitle.getText().toString());
        map.put("nameLower", txtTitle.getText().toString().toLowerCase(Locale.ROOT));
        map.put("course", txtContent.getText().toString());
        map.put("email","blank");
        map.put("purl", model.getPurl());

        FirebaseDatabase.getInstance().getReference().child("students").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddContentActivity.this, "Data inserted succesfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddContentActivity.this, "Error while inserting data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAll(){
        txtTitle.setText("");
        txtContent.setText("");
    }
}