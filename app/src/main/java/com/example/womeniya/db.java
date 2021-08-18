package com.example.womeniya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class db extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "phone.db";
    private static final String COLUMN_PHONE = "phone";
    private static final String TABLE_NAME = "phoneNumber";
    private static final String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";

    public db(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS phoneNumber" + "(" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PHONE + " TEXT" + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int Version) {
        db.execSQL("DROP TABLE IF EXISTS phoneNumber ");
        onCreate(db);
    }

    public void addnumber1(phone_number phone) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, phone.getPhoneNum());
        SQLiteDatabase db = getWritableDatabase();
        onUpgrade(db, 1, 1);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void addnumber2(phone_number phone) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, phone.getPhoneNum());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String databaseToPhoneFirst() {
        String phoneN = "";
        SQLiteDatabase db = getWritableDatabase();
        Cursor s = db.rawQuery(query, null);
        s.moveToFirst();
        phoneN = s.getString(s.getColumnIndex("phone"));
        return phoneN;
    }

    public String databaseToPhoneSecond() {
        String phoneN = "";
        SQLiteDatabase db = getWritableDatabase();
        Cursor s = db.rawQuery(query, null);
        s.moveToFirst();
        s.moveToNext();
        phoneN = s.getString(s.getColumnIndex("phone"));
        return phoneN;

    }

    public int number() {
        SQLiteDatabase db = getWritableDatabase();

        Cursor s = db.rawQuery(query, null);
        if (s.getCount() == 2) {
            return 2;
        } else {
            return 0;
        }
    }


}
