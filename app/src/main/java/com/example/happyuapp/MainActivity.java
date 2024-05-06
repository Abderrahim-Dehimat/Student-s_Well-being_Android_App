package com.example.happyuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView logoImg;
    TextView sloganTxt, existingAccountTxt, inscrireBtn;
    Button seConnecterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaring the views
        logoImg = findViewById(R.id.logoApp);
        sloganTxt = findViewById(R.id.sloganApp);
        seConnecterBtn = findViewById(R.id.se_connecter_btn);
        inscrireBtn = findViewById(R.id.inscrireBtn);
        existingAccountTxt = findViewById(R.id.ExistingTxt);

        // Adding functionalities
        logoImg.setImageResource(R.drawable.logo_app);

        seConnecterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(i);
            }
        });

        inscrireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
        


    }
}