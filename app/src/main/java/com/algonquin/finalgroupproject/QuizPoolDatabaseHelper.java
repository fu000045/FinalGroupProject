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

    // Table Columns names
    public static final String QUIZ_KEY_ID_MULTICHOICE = "id";
    public static final String QUIZ_KEY_QUESTION_MULTICHOICE = "question";
    public static String QUIZ_KEY_ANSWERA_MULTICHOICE = "answerA";
    public static String QUIZ_KEY_ANSWERB_MULTICHOICE = "answerB";
    public static String QUIZ_KEY_ANSWERC_MULTICHOICE = "answerC";
    public static String QUIZ_KEY_ANSWERD_MULTICHOICE = "answerD";
    public static String QUIZ_KEY_CORRECT_ANSWER_MULTICHOICE = "correctAnswer";

    public QuizPoolDatabaseHelper(Context ctx) {
        super(ctx, QUIZ_DATABASE_NAME, null, QUIZ_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + QUIZ_TABLE_NAME_MULTICHOICE +
                " (" + QUIZ_KEY_ID_MULTICHOICE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + QUIZ_KEY_QUESTION_MULTICHOICE + " TEXT, " +
                QUIZ_KEY_ANSWERA_MULTICHOICE + " TEXT, " + QUIZ_KEY_ANSWERB_MULTICHOICE + " TEXT, " +
                QUIZ_KEY_ANSWERC_MULTICHOICE + " TEXT, " + QUIZ_KEY_CORRECT_ANSWER_MULTICHOICE + " CHAR(1), " +
                QUIZ_KEY_ANSWERD_MULTICHOICE + " TEXT);";
        try {
            Log.i("QuizPoolDatabaseHelper", query);
            db.execSQL(query);
        } catch (SQLException e) {
            Log.e("QuizPoolDatabaseHelper", e.getMessage());
        }
        Log.i("QuizPoolDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE_NAME_MULTICHOICE + ";");
        onCreate(db);
        Log.i("QuizPoolDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion= " + newVer);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE_NAME_MULTICHOICE + ";");
        onCreate(db);
        Log.i("QuizPoolDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion= " + newVer);
    }
}
