package com.example.happyuapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FirstRegisterFragment extends Fragment {

    EditText emailEdit, passwordEdit, passwordConfirmationEdit;
    Button suivantBtn, registerButton;
    ImageView goBack2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstRegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FirstRegisterFragment newInstance(String param1, String param2) {
        FirstRegisterFragment fragment = new FirstRegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_first_register, container, false);
        emailEdit = view.findViewById(R.id.adresse_mail_edit_text);
        passwordEdit = view.findViewById(R.id.mot_de_passe_edit_text);
        passwordConfirmationEdit = view.findViewById(R.id.confirmation_de_mot_de_passe_edit_text);
        suivantBtn = view.findViewById(R.id.suivant_btn);
        registerButton = view.findViewById(R.id.register_button);
        goBack2 = view.findViewById(R.id.go_back2);

        goBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LogInActivity.class);
                startActivity(i);
            }
        });

        suivantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                String passwordConfimation = passwordConfirmationEdit.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }


                boolean isValid = checkLoginCredentials(email, password);

                if (!isValid) {
                    DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                    boolean insertionReussie = dbHelper.insertCompte(email, password);

                    if (insertionReussie) {
                        Toast.makeText(getActivity(), "Compte inséré avec succès", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Échec de l'insertion du compte", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Email existe déja", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Replace the current fragment with the SecondRegisterFragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.firstFragment, new SecondRegisterFragment())
                        .addToBackStack(null) // Optional: Adds the transaction to the back stack
                        .commit();
            }
        });


        return view;

    }
    private boolean checkLoginCredentials(String email, String password) {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
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