package com.moslemdev.kuypuasa.ui.bottomNav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.card.MaterialCardView;
import com.moslemdev.kuypuasa.PopUpPuasaHaram;
import com.moslemdev.kuypuasa.PopUpPuasaMakruh;
import com.moslemdev.kuypuasa.PopUpPuasaSunnah;
import com.moslemdev.kuypuasa.PopUpPuasaWajib;
import com.moslemdev.kuypuasa.R;

public class HomeFragment extends Fragment {

    private MaterialCardView cvPuasaSunnah;
    private MaterialCardView cvPuasaWajib;
    private MaterialCardView cvPuasaMakruh;
    private MaterialCardView cvPuasaHaram;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        cvPuasaSunnah = root.findViewById(R.id.card_view_puasa_sunnah);
        cvPuasaWajib = root.findViewById(R.id.card_view_puasa_wajib);
        cvPuasaMakruh = root.findViewById(R.id.card_view_puasa_makruh);
        cvPuasaHaram = root.findViewById(R.id.card_view_puasa_haram);

        cvPuasaSunnah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PopUpPuasaSunnah.class);
                startActivity(intent);
            }
        });

        cvPuasaWajib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PopUpPuasaWajib.class);
                startActivity(intent);
            }
        });

        cvPuasaMakruh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PopUpPuasaMakruh.class);
                startActivity(intent);
            }
        });

        cvPuasaHaram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PopUpPuasaHaram.class);
                startActivity(intent);
            }
        });

        return root;
    }
}