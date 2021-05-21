package com.example.inptbank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.*;

import androidx.annotation.Nullable;



public class Donnees extends SQLiteOpenHelper {



    private String creationBD = "CREATE TABLE IF NOT EXISTS  profils ("
            + "rib INTEGER PRIMARY KEY,"
            + "dateCreation TEXT,"
            + "prenom TEXT NOT NULL,"
            + "nom TEXT NOT NULL,"
            + "gabPassword INTEGER NOT NULL,"
            + "solde REAL,"
            + "emailDB TEXT,"
            + "passwordDB TEXT);";

    public Donnees(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creationBD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
