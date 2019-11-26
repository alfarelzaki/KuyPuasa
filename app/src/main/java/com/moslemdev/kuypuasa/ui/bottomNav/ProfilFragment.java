package com.moslemdev.kuypuasa.ui.bottomNav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.moslemdev.kuypuasa.EditProfil;
import com.moslemdev.kuypuasa.R;

public class ProfilFragment extends Fragment {

    private Button buttonEdit;
    TextView tvNama;
    TextView tvEmail;
    TextView tvGender;
    TextView tvUmur;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_profile, container, false);

        tvNama = root.findViewById(R.id.);

        buttonEdit = root.findViewById(R.id.button_edit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfil.class);
                startActivity(intent);
            }
        });

        return root;
    }
}