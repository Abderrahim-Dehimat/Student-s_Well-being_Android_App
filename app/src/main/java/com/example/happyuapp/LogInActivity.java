package com.example.happyuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    TextView logInTxt;
    EditText emailEditText, passwordEditText;
    Button logInButton, RegisterButton;
    ImageView goBackImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // declaring the views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        logInButton = findViewById(R.id.logInButton);
        RegisterButton = findViewById(R.id.RegisterButton);
        goBackImg = findViewById(R.id.go_back);

        goBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        //Create Compte juste for testing
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        boolean insertionReussie = dbHelper.insertCompte("kawthar@gmail.com", "kawthar");

        if (insertionReussie) {
            Toast.makeText(this, "Compte inséré avec succès", Toast.LENGTH_SHORT).show();
        }

        // Register Button
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        // LogIn Button
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isValid = checkLoginCredentials(email, password);

                if (isValid) {
                    CustomAuthManager.setCurrentLoggedInEmail(email);
                    Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LogInActivity.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean checkLoginCredentials(String email, String password) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_PASSWORD
        };

        String selection = DatabaseHelper.COLUMN_EMAIL + " = ? AND " +
                DatabaseHelper.COLUMN_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_COMPTES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isValid = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isValid;
    }

}