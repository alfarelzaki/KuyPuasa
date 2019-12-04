package com.moslemdev.kuypuasa.ui.bottomNav;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.moslemdev.kuypuasa.IsiDataDiri;
import com.moslemdev.kuypuasa.PopUpPuasaHaram;
import com.moslemdev.kuypuasa.PopUpPuasaMakruh;
import com.moslemdev.kuypuasa.PopUpPuasaSunnah;
import com.moslemdev.kuypuasa.PopUpPuasaWajib;
import com.moslemdev.kuypuasa.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private MaterialCardView cvPuasaSunnah;
    private MaterialCardView cvPuasaWajib;
    private MaterialCardView cvPuasaMakruh;
    private MaterialCardView cvPuasaHaram;
    CircleImageView photoProfileHome;
    Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        cvPuasaSunnah = root.findViewById(R.id.card_view_puasa_sunnah);
        cvPuasaWajib = root.findViewById(R.id.card_view_puasa_wajib);
        cvPuasaMakruh = root.findViewById(R.id.card_view_puasa_makruh);
        cvPuasaHaram = root.findViewById(R.id.card_view_puasa_haram);
        photoProfileHome = root.findViewById(R.id.home_photo_profile);

        if (IsiDataDiri.user.photo != null) {
            loadImageFromStorage(IsiDataDiri.user.photo);
            Glide.with(this).load(bitmap).into(photoProfileHome);
        }

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

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}