package com.moslemdev.kuypuasa.ui.bottomNav;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.moslemdev.kuypuasa.EditProfil;
import com.moslemdev.kuypuasa.IsiDataDiri;
import com.moslemdev.kuypuasa.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilFragment extends Fragment {

    private Button buttonEdit;
    private TextView tvNama;
    private TextView tvEmail;
    private TextView tvGender;
    private TextView tvUmur;
    private TextView tvLevel;
    private TextView tvCapaian;
    private CircleImageView photoProfile;
    Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_profile, container, false);

        tvNama = root.findViewById(R.id.profil_nama);
        tvEmail= root.findViewById(R.id.profil_email);
        tvGender = root.findViewById(R.id.profil_gender);
        tvUmur = root.findViewById(R.id.profil_umur);
        tvLevel = root.findViewById(R.id.profil_level);
        tvCapaian = root.findViewById(R.id.profil_pencapaian);
        photoProfile = root.findViewById(R.id.photo_profil);

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
        tvNama.setText(IsiDataDiri.user.getNama());
        tvEmail.setText(IsiDataDiri.user.getEmail());
        tvGender.setText(IsiDataDiri.user.getGender());
        tvUmur.setText(String.valueOf(IsiDataDiri.user.getUmur()));
        tvLevel.setText("Level " + IsiDataDiri.user.getLevel() + " (" + IsiDataDiri.user.getExperience()
                + "/" + IsiDataDiri.user.getLevel()*40 + ")");
        tvCapaian.setText(String.valueOf(IsiDataDiri.user.getCapaian()));

        if (IsiDataDiri.user.photo != null) {
            loadImageFromStorage(IsiDataDiri.user.getPhoto());
            Glide.with(this).load(bitmap).into(photoProfile);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInformation();
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