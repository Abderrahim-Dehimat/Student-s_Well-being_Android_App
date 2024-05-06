package com.example.happyuapp;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondRegisterFragment extends Fragment {
    EditText nameEdit, firstNameEdit, birthdayEdit, countryEdit, cityEdit;
    Button suivantBtn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public SecondRegisterFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondRegisterFragment newInstance(String param1, String param2) {
        SecondRegisterFragment fragment = new SecondRegisterFragment();
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
        View view =  inflater.inflate(R.layout.fragment_second_register, container, false);
        nameEdit = view.findViewById(R.id.nomEditText);
        firstNameEdit = view.findViewById(R.id.prenomEditText);
        birthdayEdit = view.findViewById(R.id.datDeNaissanceEdit);
        countryEdit = view.findViewById(R.id.paysEdtiText);
        cityEdit = view.findViewById(R.id.villeEditText);
        suivantBtn = view.findViewById(R.id.buttonEnvoyer);

        suivantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LogInActivity.class);
                startActivity(i);
            }

        });

        return  view;

    }
}