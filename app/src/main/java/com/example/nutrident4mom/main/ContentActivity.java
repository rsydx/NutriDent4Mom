package com.example.nutrident4mom.main;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nutrident4mom.R;



import android.widget.ImageView;
import android.widget.TextView;

public class ContentActivity extends AppCompatActivity {

    ImageView imageholder;
    TextView emailholder,courseholder,nameholder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        imageholder = (ImageView) findViewById(R.id.imagegholder);
        emailholder = (TextView) findViewById(R.id.emailholder);
        courseholder = (TextView) findViewById(R.id.courseholder);
        nameholder = (TextView) findViewById(R.id.nameholder);
        Glide.with(imageholder.getContext()).load(getIntent().getStringExtra("imagegholder")).into(imageholder);

        emailholder.setText(getIntent().getStringExtra("emailholder"));
        nameholder.setText(getIntent().getStringExtra("nameholder"));
        courseholder.setText(getIntent().getStringExtra("courseholder"));








    }
}
