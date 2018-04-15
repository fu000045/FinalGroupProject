package com.algonquin.finalgroupproject.octranspo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class OCDatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Message.db";
    public static final int VERSION_NUM = 5;

    public static final String TABLE_NAME = "Message";
    private static final String ACTIVITY_NAME = "ActivityName";


    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";


    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " TEXT);";


    public OCDatabaseHelper(Context ctx) {

        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        Log.i(ACTIVITY_NAME, "Calling onCreate()");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i("OCDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        //       Log.i("OCDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + "newVersion=" + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);  //make a new database
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i(ACTIVITY_NAME, "Calling onDowngrade - Going from old version " + oldVersion + " to new version " + newVersion);
        onCreate(db);

    }


    public void onOpen(SQLiteDatabase db) {
        Log.i("DATABASE", "Database opened");
    }
}






