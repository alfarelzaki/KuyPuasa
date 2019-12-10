package com.moslemdev.kuypuasa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "puasacalendar.db";
    private static final int DATABASE_VERSION = 1;
    private static final String PUASA_TABLE = "puasa";
    private static final String PUASA_ID = "id";
    private static final String PUASA_NAME = "name";
    private static final String PUASA_TIME = "time";
    private static final String CREATE_TABLE_PUASA =
            "CREATE TABLE " + PUASA_TABLE
            + "(" + PUASA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PUASA_NAME + " TEXT, "
            + PUASA_TIME + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_PUASA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '"
                + PUASA_TABLE + "'");
        onCreate(sqLiteDatabase);
    }

    public long addPuasa(String name, long time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PUASA_NAME, name);
        values.put(PUASA_TIME, time);
        long insert = db.insert(PUASA_TABLE, null, values);
        return insert;
    }

    public ArrayList<Map<String, String>> getAllPuasa() {
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        String name = "";
        String time = "";
        int id = 0;
        String selectQuery = "SELECT * FROM " + PUASA_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do{
                id = cursor.getInt(cursor.getColumnIndex(PUASA_ID));
                name = cursor.getString(cursor.getColumnIndex(PUASA_NAME));
                time = cursor.getString(cursor.getColumnIndex(PUASA_TIME));
                Map<String, String> itemMap = new HashMap<>();
                itemMap.put(PUASA_ID, id+"");
                itemMap.put(PUASA_NAME, name);
                itemMap.put(PUASA_TIME, time);
                arrayList.add(itemMap);
                Log.d("waktu 2", time);
            } while (cursor.moveToNext());
        }

        return arrayList;
    }

    public ArrayList<Map<String, String>> getPuasaInSpesificDay(String time) {
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        String name = "";
        int id = 0;
        String selectQuery = "SELECT * FROM " + PUASA_TABLE + " WHERE " + PUASA_TIME + " = " + time;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do{
                id = cursor.getInt(cursor.getColumnIndex(PUASA_ID));
                name = cursor.getString(cursor.getColumnIndex(PUASA_NAME));
                time = cursor.getString(cursor.getColumnIndex(PUASA_TIME));
                Map<String, String> itemMap = new HashMap<>();
                itemMap.put(PUASA_ID, id+"");
                itemMap.put(PUASA_NAME, name);
                itemMap.put(PUASA_TIME, time);
                arrayList.add(itemMap);
            } while (cursor.moveToNext());
        }

        return arrayList;
    }
}
