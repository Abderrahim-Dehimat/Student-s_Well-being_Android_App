package com.example.happyuapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.slider.Slider;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MoodFragment extends Fragment {
    Slider slider;
    RadioGroup radioGroup2;

    RadioGroup radioGroup3 ;
    RadioGroup radioGroup4 ;
    RadioGroup radioGroup5;
    RadioGroup radioGroup6 ;
    public MoodFragment() {
        // Required empty public constructor
    }

    public static MoodFragment newInstance() {
        return new MoodFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mood, container, false);

        // Vous pouvez ajouter des écouteurs d'événements sur les RadioGroups pour réagir aux sélections de l'utilisateur
        radioGroup2 = view.findViewById(R.id.answersGroup2);

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Vous pouvez gérer la réponse de l'utilisateur ici
                RadioButton selectedRadioButton = view.findViewById(checkedId);
                String response = selectedRadioButton.getText().toString();
                // Affichez la réponse avec un Toast, par exemple
                Toast.makeText(getActivity(), "Vous avez sélectionné : " + response, Toast.LENGTH_SHORT).show();
            }
        });


        // Répétez pour les autres groupes de réponses
        slider = view.findViewById(R.id.slider);
        radioGroup3 = view.findViewById(R.id.answersGroup3);
        radioGroup4 = view.findViewById(R.id.answersGroup4);
        radioGroup5 = view.findViewById(R.id.answersGroup5);
        radioGroup6 = view.findViewById(R.id.answersGroup6);
        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                // Ce code est exécuté lorsque l'utilisateur commence à interagir avec le Slider
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                // Ce code est exécuté lorsque l'utilisateur a fini d'interagir avec le Slider
                float sliderValue = slider.getValue(); // Obtenir la valeur actuelle du Slider
                // Vous pouvez faire quelque chose avec la valeur du Slider ici
                Toast.makeText(getActivity(), "Valeur du Slider : " + sliderValue, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Here you can perform any additional setup for your fragment

        // Récupérez une référence au bouton "Envoyer"
        Button envoyerButton = view.findViewById(R.id.envoyer);

        // Ajoutez un écouteur d'événements au bouton "Envoyer"
        envoyerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float sliderValue = slider.getValue();

                // Récupérer l'ID du RadioButton sélectionné dans radioGroup2
                int selectedRadioButtonId2 = radioGroup2.getCheckedRadioButtonId();
                RadioButton selectedRadioButton2 = view.findViewById(selectedRadioButtonId2);
                String radioGroup2Value = selectedRadioButton2.getText().toString();

                int selectedRadioButtonId3 = radioGroup3.getCheckedRadioButtonId();
                RadioButton selectedRadioButton3 = view.findViewById(selectedRadioButtonId3);
                String radioGroup3Value = selectedRadioButton3.getText().toString();

                int selectedRadioButtonId4 = radioGroup4.getCheckedRadioButtonId();
                RadioButton selectedRadioButton4 = view.findViewById(selectedRadioButtonId4);
                String radioGroup4Value = selectedRadioButton4.getText().toString();

                int selectedRadioButtonId5 = radioGroup5.getCheckedRadioButtonId();
                RadioButton selectedRadioButton5 = view.findViewById(selectedRadioButtonId5);
                String radioGroup5Value = selectedRadioButton5.getText().toString();

                int selectedRadioButtonId6 = radioGroup6.getCheckedRadioButtonId();
                RadioButton selectedRadioButton6 = view.findViewById(selectedRadioButtonId6);
                String radioGroup6Value = selectedRadioButton6.getText().toString();

                Toast.makeText(getContext(), CustomAuthManager.getCurrentLoggedInEmail(), Toast.LENGTH_SHORT).show();
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                boolean insertionReussie = dbHelper.insertMood(sliderValue,radioGroup2Value, radioGroup3Value,radioGroup4Value,radioGroup5Value,radioGroup6Value,getCurrentDateTime());
                if (insertionReussie) {
                    Toast.makeText(getActivity(), "Mood inséré avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Échec de l'insertion du Mood"+sliderValue, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public static String getCurrentDateTime() {
        // Obtenir la date et l'heure actuelles
        Date currentDate = new Date();

        // Définir le format de date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Formater la date et l'heure actuelles en tant que chaîne de caractères
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }
}
