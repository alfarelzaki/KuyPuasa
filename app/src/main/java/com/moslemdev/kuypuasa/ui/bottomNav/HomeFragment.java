package com.moslemdev.kuypuasa.ui.bottomNav;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.moslemdev.kuypuasa.IsiDataDiri;
import com.moslemdev.kuypuasa.PopUpPuasaHaram;
import com.moslemdev.kuypuasa.PopUpPuasaMakruh;
import com.moslemdev.kuypuasa.PopUpPuasaSunnah;
import com.moslemdev.kuypuasa.PopUpPuasaWajib;
import com.moslemdev.kuypuasa.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private MaterialCardView cvPuasaSunnah;
    private MaterialCardView cvPuasaWajib;
    private MaterialCardView cvPuasaMakruh;
    private MaterialCardView cvPuasaHaram;
    private Calendar today = Calendar.getInstance();
    private int sToday = today.get(Calendar.DAY_OF_MONTH);
    public static int state;
    TextView namaUserHome;
    MaterialCardView verifikasiPuasa;
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
        verifikasiPuasa = root.findViewById(R.id.verifikasi_puasa);
        namaUserHome = root.findViewById(R.id.nama_user_home);

        namaUserHome.setText(IsiDataDiri.user.nama);
        if (sToday != state) {
            verifikasiPuasa.setVisibility(View.VISIBLE);
        }

        if (IsiDataDiri.user.photo != null) {
            loadImageFromStorage(IsiDataDiri.user.photo);
            Glide.with(this).load(bitmap).into(photoProfileHome);
        }

        verifikasiPuasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Anda mendapatkan 30xp!", Toast.LENGTH_SHORT).show();
                IsiDataDiri.user.setExperience(IsiDataDiri.user.getExperience()+30);
                checkLevelUp();
                state = sToday;
                saveDataUser();
                verifikasiPuasa.setVisibility(View.GONE);
            }
        });

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

    private void checkLevelUp() {
        // jika lebih dari batas level, maka naik level
        if (IsiDataDiri.user.getExperience() >= IsiDataDiri.user.getLevel()*40) {
            IsiDataDiri.user.setExperience(IsiDataDiri.user.getExperience()-IsiDataDiri.user.getLevel()*40);
            IsiDataDiri.user.setLevel(IsiDataDiri.user.getLevel()+1);

            new MaterialAlertDialogBuilder(getActivity())
                .setTitle("Selamat kamu naik Level!")
                .setMessage("Anda telah berhasil mencapai level " + IsiDataDiri.user.getLevel() + ", tingkatkan terus puasamu!")
                .setPositiveButton("Ok!", null)
                .show();
        }
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

    private void saveDataUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DataUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(IsiDataDiri.user);
        editor.putString("userData", json);
        editor.putInt("stateVerifikasi", state);
        editor.commit();
    }
}