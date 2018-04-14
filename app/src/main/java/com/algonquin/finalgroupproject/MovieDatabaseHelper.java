package com.algonquin.finalgroupproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieDatabaseHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int MOVIE_DATABASE_VERSION = 1;

    // Database Name
    private static final String MOVIE_DATABASE_NAME = "Movie.db";

    // Table name
    public static final String MOVIE_TABLE_NAME= "movie_table";

    //Table Columns names
    public static final String MOVIE_KEY_ID = "id";
    public static String MOVIE_TITLE_ANSWER = "movieTitle";
    public static String MOVIE_MAINACTORS_ANSWER = "mainActors";
    public static String MOVIE_LENGTH_ANSWER = "length";
    public static String MOVIE_GENRE_ANSWER = "genre";
    public static String MOVIE_DESCRIPTION_ANSWER = "description";


    public MovieDatabaseHelper(Context ctx) {
        super(ctx, MOVIE_DATABASE_NAME, null, MOVIE_DATABASE_VERSION);
    }
        @Override
    public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + MOVIE_TABLE_NAME +
                    " (" + MOVIE_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MOVIE_TITLE_ANSWER + " TEXT, " + MOVIE_MAINACTORS_ANSWER + " TEXT, " +
                    MOVIE_LENGTH_ANSWER + " TEXT, " + MOVIE_GENRE_ANSWER + " TEXT, " +
                    MOVIE_DESCRIPTION_ANSWER + " TEXT);";
            try {
                Log.i("MovieDatabaseHelper", query);
                db.execSQL(query);
            } catch (SQLException e) {
                Log.e("MovieDatabaseHelper", e.getMessage());
            }
            Log.i("MovieDatabaseHelper", "Calling onCreate");
    }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + MOVIE_DATABASE_NAME + ";");
            onCreate(db);
            Log.i("MovieDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion= " + newVer);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + MOVIE_DATABASE_NAME + ";");
            onCreate(db);
            Log.i("MovieDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion= " + newVer);
        }
}
