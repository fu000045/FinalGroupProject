package com.algonquin.finalgroupproject;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Yali Fu on 3/18/2018.
 */

public class QuizPoolDatabaseHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int QUIZ_DATABASE_VERSION = 1;

    // Database Name
    private static final String QUIZ_DATABASE_NAME = "Quiz.db";

    // Table name
    public static final String QUIZ_TABLE_NAME_MULTICHOICE = "quiz_multichoice";
    public static final String QUIZ_TABLE_NAME_TRUEFALSE = "quiz_truefalse";
    public static final String QUIZ_TABLE_NAME_NUMERIC = "quiz_numeric";
    // Table Columns names
    public static final String QUIZ_KEY_ID = "id";
    public static final String QUIZ_KEY_QUESTION= "question";
    public static String QUIZ_KEY_ANSWERA = "answerA";
    public static String QUIZ_KEY_ANSWERB = "answerB";
    public static String QUIZ_KEY_ANSWERC = "answerC";
    public static String QUIZ_KEY_ANSWERD = "answerD";
    public static String QUIZ_KEY_CORRECT_ANSWER = "correctAnswer";

    public QuizPoolDatabaseHelper(Context ctx) {
        super(ctx, QUIZ_DATABASE_NAME, null, QUIZ_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + QUIZ_TABLE_NAME_MULTICHOICE +
                " (" + QUIZ_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + QUIZ_KEY_QUESTION + " TEXT, " +
                QUIZ_KEY_ANSWERA + " TEXT, " + QUIZ_KEY_ANSWERB + " TEXT, " +
                QUIZ_KEY_ANSWERC + " TEXT, " + QUIZ_KEY_ANSWERD + " TEXT, " +
                QUIZ_KEY_CORRECT_ANSWER + " TEXT);" ;
        String query2 = "CREATE TABLE " + QUIZ_TABLE_NAME_TRUEFALSE +
                " (" + QUIZ_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + QUIZ_KEY_QUESTION + " TEXT, " +
                QUIZ_KEY_CORRECT_ANSWER + " TEXT);";
        String query3 = "CREATE TABLE " + QUIZ_TABLE_NAME_NUMERIC +
                " (" + QUIZ_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + QUIZ_KEY_QUESTION + " TEXT, " +
                QUIZ_KEY_ANSWERA + " TEXT, " + QUIZ_KEY_CORRECT_ANSWER + " TEXT);";
        try {
            db.execSQL(query1);
            db.execSQL(query2);
            db.execSQL(query3);
        } catch (SQLException e) {
            Log.e("QuizPoolDatabaseHelper", e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE_NAME_MULTICHOICE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE_NAME_TRUEFALSE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE_NAME_NUMERIC + ";");
        onCreate(db);
        Log.i("QuizPoolDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion= " + newVer);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE_NAME_MULTICHOICE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE_NAME_TRUEFALSE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE_NAME_NUMERIC + ";");
        onCreate(db);
        Log.i("QuizPoolDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion= " + newVer);
    }
}
