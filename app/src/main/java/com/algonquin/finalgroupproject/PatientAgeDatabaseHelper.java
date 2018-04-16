package com.algonquin.finalgroupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wenchongxu on 2018-04-16.
 */

public class PatientAgeDatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "patient.db";
    private static int VERSION_NUM = 1;
    public final static String AGE_TABLE_NAME = "ageTable";
    public final static String KEY_AGE = "age";
    public final static String KEY_ID = "id";

    private static final String createAgeTable = "CREATE TABLE "
            + AGE_TABLE_NAME +" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_AGE+" TEXT);";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createAgeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AGE_TABLE_NAME);
    }
    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion){

    }

    @Override
    public void onOpen (SQLiteDatabase db){
    }
    public PatientAgeDatabaseHelper(Context ctx) {super(ctx, DATABASE_NAME, null, VERSION_NUM);}
}
