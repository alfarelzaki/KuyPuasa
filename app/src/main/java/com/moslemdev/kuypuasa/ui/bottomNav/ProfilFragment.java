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
import com.moslemdev.kuypuasa.IsiDataDiri;
import com.moslemdev.kuypuasa.R;

import static android.app.Activity.RESULT_OK;

public class ProfilFragment extends Fragment {

    private Button buttonEdit;
    TextView tvNama;
    TextView tvEmail;
    TextView tvGender;
    TextView tvUmur;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_profile, container, false);

        tvNama = root.findViewById(R.id.profil_nama);
        tvEmail= root.findViewById(R.id.profil_email);
        tvGender = root.findViewById(R.id.profil_gender);
        tvUmur = root.findViewById(R.id.profil_umur);

        setUserInformation();

        buttonEdit = root.findViewById(R.id.button_edit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfil.class);
                startActivityForResult(intent, 1);
            }
        });

        return root;
    }

    private void setUserInformation() {
        tvNama.setText(IsiDataDiri.user.nama);
        tvEmail.setText(IsiDataDiri.user.email);
        tvGender.setText(IsiDataDiri.user.gender);
        tvUmur.setText(String.valueOf(IsiDataDiri.user.umur));
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInformation();
    }
}