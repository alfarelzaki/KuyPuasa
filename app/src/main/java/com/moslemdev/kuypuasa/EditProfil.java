package com.moslemdev.kuypuasa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfil extends AppCompatActivity {

    private Button buttonSimpan;
    EditText etNama;
    EditText etEmail;
    EditText etGender;
    EditText etUmur;
    CircleImageView editPhotoProfile;
    TextView changePhotoProfile;
    static Bitmap bitmap;
    BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();

    static File mypath;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profil);

        etNama = findViewById(R.id.edit_nama);
        etEmail= findViewById(R.id.edit_email);
        etGender= findViewById(R.id.edit_gender);
        etUmur= findViewById(R.id.edit_umur);
        buttonSimpan = findViewById(R.id.button_simpan);
        editPhotoProfile = findViewById(R.id.edit_photo_profile);
        changePhotoProfile = findViewById(R.id.button_change_photo);

        // menampilkan data user sebelum diedit
        showUserData();

        if (IsiDataDiri.user.photo != null) {
            Log.d("tag path", IsiDataDiri.user.photo);
            loadImageFromStorage(IsiDataDiri.user.photo);
            Glide.with(this).load(bitmap).into(editPhotoProfile);
        }

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIsFilled()) {
                    changeUserData();
                    finish();
                }
            }
        });

        changePhotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");

                if (!PermissionHelper.isPermissionGranted(EditProfil.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    PermissionHelper.askForPermission(EditProfil.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                }

                if (!PermissionHelper.isPermissionGranted(EditProfil.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    PermissionHelper.askForPermission(EditProfil.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

                startActivityForResult(intent, PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {

            //berhubungan dengan bitmap photo profile
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            // Decode just the boundaries
            int srcWidth = mBitmapOptions.outWidth;
            int srcHeight = mBitmapOptions.outHeight;
            int dstWidth = 100;

            mBitmapOptions.inScaled = true;
            mBitmapOptions.inSampleSize = 4;
            mBitmapOptions.inDensity = srcWidth;
            mBitmapOptions.inTargetDensity =  dstWidth * mBitmapOptions.inSampleSize;

            bitmap = BitmapFactory.decodeFile(picturePath, mBitmapOptions);
            IsiDataDiri.user.photo = saveToInternalStorage(bitmap);
            Glide.with(this).load(bitmap).into(editPhotoProfile);
        }
    }

    private void showUserData() {
        etNama.setText(IsiDataDiri.user.nama);
        etEmail.setText(IsiDataDiri.user.email);
        etGender.setText(IsiDataDiri.user.gender);
        etUmur.setText(String.valueOf(IsiDataDiri.user.umur));
    }

    private boolean checkIsFilled() {
        boolean filled = true;
        if (TextUtils.isEmpty(etNama.getText())) {
            etNama.setError("Isikan nama");
            filled = false;
        }

        if (TextUtils.isEmpty(etEmail.getText())) {
            etEmail.setError("Isikan email");
            filled = false;
        }

        if (TextUtils.isEmpty(etGender.getText())) {
            etGender.setError("Isikan gender");
            filled = false;
        }

        if (TextUtils.isEmpty(etUmur.getText())) {
            etUmur.setError("Isikan umur");
            filled = false;
        }

        return filled;
    }

    private void changeUserData() {
        IsiDataDiri.user.nama = etNama.getText().toString();
        IsiDataDiri.user.email = etEmail.getText().toString();
        IsiDataDiri.user.gender = etGender.getText().toString();
        IsiDataDiri.user.umur = Integer.parseInt(etUmur.getText().toString());
        saveEditDataUser();
    }

    private void saveEditDataUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("DataUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(IsiDataDiri.user);
        editor.putString("userData", json);
        editor.commit();
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir

        mypath = new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
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
