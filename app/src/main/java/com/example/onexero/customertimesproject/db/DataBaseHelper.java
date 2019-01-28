package com.example.onexero.customertimesproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.example.onexero.customertimesproject.model.NameModel;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "customertimesdb";
    private static final int DATABASE_VERSION = 1;

    private static ArrayList<NameModel> mColumnNames;

    public DataBaseHelper(Context context, ArrayList<NameModel> columnNames) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mColumnNames = columnNames;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNT_TABLE = "  " +
                " CREATE TABLE account (" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT ";
        for (NameModel columnName : mColumnNames) {
            CREATE_ACCOUNT_TABLE += " , [" + columnName.getName() + "] varchar NULL ";
        }
        CREATE_ACCOUNT_TABLE += ");";
        try {
            db.execSQL(CREATE_ACCOUNT_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
