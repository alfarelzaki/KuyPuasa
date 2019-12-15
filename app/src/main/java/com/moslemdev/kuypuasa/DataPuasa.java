package com.moslemdev.kuypuasa;

import java.util.ArrayList;

public class DataPuasa {
    private static int[] tanda = {
            R.drawable.mark_puasa_senin_kamis,
            R.drawable.mark_puasa_ayyamul_bidh,
            R.drawable.mark_puasa_arafah,
            R.drawable.mark_puasa_asyura_tasua,
            R.drawable.mark_puasa_ramadhan,
            R.drawable.mark_haram_berpuasa,
    };

    private static String[] puasa = {
            "Puasa Senin Kamis",
            "Puasa Ayyamul Bidh",
            "Puasa Arafah",
            "Puasa Asyura Tasu'a",
            "Puasa Ramadhan",
            "Haram Berpuasa",
    };

    private static String[] deskripsi = {
            "Puasa yang dilaksanakan setiap hari Senin dan Kamis",
            "Puasa pada hari ke 13, 14, dan 15 bulan Hijriyah",
            "Puasa yang dilaksanakan pada tanggal 9 Dzulhijjah",
            "Puasa pada 10 Muharram dan satu hari sebelum atau sesudahnya",
            "Puasa wajib umat islam pada bulan Ramadhan",
            "Waktu Haram Berpuasa (Idul Fitri, Idul Adha dan Hari Tasyrik",
    };

    private static int[] experience = {
            30,
            50,
            100,
            100,
            300,
            20,
    };

    public static ArrayList<Puasa> getListData() {
        ArrayList<Puasa> listPuasa = new ArrayList<>();
        for (int i = 0; i < puasa.length; i++) {
            Puasa item = new Puasa();
            item.setPuasa(puasa[i]);
            item.setDeskripsi(deskripsi[i]);
            item.setExperience(experience[i]);
            item.setTanda(tanda[i]);
            listPuasa.add(item);
        }
        return listPuasa;
    }
}
