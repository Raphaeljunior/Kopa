package com.example.Kopa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CODING_MOVAT on 8/6/2014.
 */
public class Dbhelper extends SQLiteOpenHelper {
    public static String TABLE_NAME = "";
    public static String TRANSACTION = "";
    public  static String DEBT = "";
    public static String
    public Dbhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
