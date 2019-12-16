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
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.moslemdev.kuypuasa.DataPuasa;
import com.moslemdev.kuypuasa.DatabaseHelper;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    // date as day
    Date senin = new Date(17903);
    Date kamis = new Date(17899);

    // dalam satu minggu terdapat 7 hari
    long oneWeek = 7;

    // detik dalam sehari
    long oneDay = 86400;

    DatabaseHelper db;
    private MaterialCardView cvPuasaSunnah;
    private MaterialCardView cvPuasaWajib;
    private MaterialCardView cvPuasaMakruh;
    private MaterialCardView cvPuasaHaram;
    private Calendar today = Calendar.getInstance();
    private GregorianCalendar gCal;
    private int sToday = today.get(Calendar.DAY_OF_MONTH);
    public static int state;
    TextView namaUserHome;
    TextView puasaHariIniHome;
    TextView tanggalHome;
    MaterialCardView verifikasiPuasa;
    CircleImageView photoProfileHome;
    Bitmap bitmap;
    ArrayList<Map<String, String>> arrayList;

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
        puasaHariIniHome = root.findViewById(R.id.puasa_hari_ini_home);
        tanggalHome = root.findViewById(R.id.date_home);

        db = new DatabaseHelper(getActivity());
        gCal = new GregorianCalendar(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        Log.d("level", String.valueOf(gCal.getTimeInMillis()/(1000*86400)+1));
        int todayExperience = db.getExperiencePuasaInSpesificDay(String.valueOf(gCal.getTimeInMillis()/(1000*86400)+1));

        // set tanggal home
        String[] days = new String[] { "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu" };
        String[] months = new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" };
        String day = days[today.get(Calendar.DAY_OF_WEEK)-1];
        String month = months[today.get(Calendar.MONTH)];
        tanggalHome.setText(day + ", " +  today.get(Calendar.DAY_OF_MONTH) + " " + month + " " + today.get(Calendar.YEAR));

        namaUserHome.setText(IsiDataDiri.user.nama);
        if (sToday != state) {
            verifikasiPuasa.setVisibility(View.VISIBLE);
        } else verifikasiPuasa.setVisibility(View.GONE);

        if (IsiDataDiri.user.photo != null) {
            loadImageFromStorage(IsiDataDiri.user.photo);
            Glide.with(this).load(bitmap).into(photoProfileHome);
        }

        verifikasiPuasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (todayExperience == 0) {
                    Toast.makeText(getActivity(), "Hari ini tidak ada puasa", Toast.LENGTH_SHORT).show();
                } else {
                    new MaterialAlertDialogBuilder(getActivity())
                            .setTitle("Klaim Exp")
                            .setMessage("Apakah anda sudah berpuasa hari ini?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getActivity(), "Anda mendapatkann " + todayExperience + "exp!", Toast.LENGTH_SHORT).show();
                                    IsiDataDiri.user.setExperience(IsiDataDiri.user.getExperience()+todayExperience);
                                    IsiDataDiri.user.setCapaian(IsiDataDiri.user.getCapaian()+1);
                                    checkLevelUp();
                                    state = sToday;
                                    saveDataUser();
                                    verifikasiPuasa.setVisibility(View.GONE);
                                }
                            })
                            .setNegativeButton("Tidak", null)
                            .show();
                }
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

        // jika inisialisasi pertama, maka buat database terlebih dahulu
        if (!initializeCalendarData()) {
            haramPuasaSaveToDatabase();
            puasaRamadhanSavetoDatabase();
            puasaArafahSaveToDatabase();
            puasaAsyuraSaveToDatabase();
            puasaAyyamulBidhSaveToDatabase();
            puasaSeninSaveToDatabase();
            puasaKamisSaveToDatabase();

            // save state (intinya calendar data sudah pernah di inisialisasi
            SharedPreferences sharedPreferences =
                    getActivity().getSharedPreferences("DataUser", getActivity().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userCalendar", "initialized");
            editor.commit();
        }

        loadDataPuasaInSpesificDay();

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

    private void loadDataPuasaInSpesificDay() {
        arrayList = db.getPuasaInSpesificDay(String.valueOf(gCal.getTimeInMillis()/(1000*86400)+1));
        puasaHariIniHome.setText("");
        for (int i=0; i<arrayList.size(); i++) {
            if (i==arrayList.size()-1) puasaHariIniHome.setText("-> " + arrayList.get(i).get("name"));
            else puasaHariIniHome.setText("-> " + arrayList.get(i).get("name") + "\n");
        }

        if (puasaHariIniHome.getText() == "") puasaHariIniHome.setText("Tidak ada puasa hari ini");
    }

    private void puasaAsyuraSaveToDatabase() {
        long time = (Long.valueOf(18503));
        db.addPuasa("Puasa Asyura Tasu'a", time);
    }

    private void puasaArafahSaveToDatabase() {
        long time = (Long.valueOf(18473));
        db.addPuasa("Puasa Arafah", time);
    }

    private void puasaRamadhanSavetoDatabase() {
        for (int i=0; i<30; i++) {
            long time = (Long.valueOf(18376)+i);
            db.addPuasa("Puasa Ramadhan", time);
        }
    }

    private void haramPuasaSaveToDatabase() {
        for (int i=0; i<3; i++) {
            long time = (Long.valueOf(18475)+i);
            db.addPuasa("Haram Berpuasa", time);
        }
    }

    private void puasaKamisSaveToDatabase() {
        for (int i=0; i<105; i++) {
            long time = (kamis.getTime() + oneWeek*i);
            db.addPuasa("Puasa Senin Kamis", time);
        }
    }

    private void puasaSeninSaveToDatabase() {
        for (int i=0; i<105; i++) {
            long time = (senin.getTime() + oneWeek*i);
            db.addPuasa("Puasa Senin Kamis", time);
        }
    }

    private void puasaAyyamulBidhSaveToDatabase() {
        ArrayList<Long> listPuasaAyyamulBidh = new ArrayList<>();
        listPuasaAyyamulBidh.add(Long.valueOf(18270));
        listPuasaAyyamulBidh.add(Long.valueOf(18299));
        listPuasaAyyamulBidh.add(Long.valueOf(18329));
        listPuasaAyyamulBidh.add(Long.valueOf(18359));
        listPuasaAyyamulBidh.add(Long.valueOf(18418));
        listPuasaAyyamulBidh.add(Long.valueOf(18448));
        listPuasaAyyamulBidh.add(Long.valueOf(18478));
        listPuasaAyyamulBidh.add(Long.valueOf(18506));
        listPuasaAyyamulBidh.add(Long.valueOf(18536));
        listPuasaAyyamulBidh.add(Long.valueOf(18565));
        listPuasaAyyamulBidh.add(Long.valueOf(18595));
        listPuasaAyyamulBidh.add(Long.valueOf(18624));

        for (int i=0; i<listPuasaAyyamulBidh.size(); i++) {
            // puasa ayyamul bidh biasanya berlangsung 3 hari
            for (int j=0; j<3; j++) {
                long time = (listPuasaAyyamulBidh.get(i)+j);
                db.addPuasa("Puasa Ayyamul Bidh", time);
            }
        }
    }

    private boolean initializeCalendarData() {
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences("DataUser", getActivity().MODE_PRIVATE);
        if (!sharedPreferences.contains("userCalendar")) {
            return false;
        }
        return true;
    }
}