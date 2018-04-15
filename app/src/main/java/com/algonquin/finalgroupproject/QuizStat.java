package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class QuizStat extends Activity {

    //Get a writable database.
    private QuizPoolDatabaseHelper dbHelper;
    private SQLiteDatabase db = null;
    private Cursor c;

    private TextView txt_longest;
    private TextView txt_shortest;
    private TextView txt_average;

    //Get table name and column name.
    String tableName_multichoice = dbHelper.QUIZ_TABLE_NAME_MULTICHOICE;
    String tableName_truefalse = dbHelper.QUIZ_TABLE_NAME_TRUEFALSE;
    String tableName_numeric = dbHelper.QUIZ_TABLE_NAME_NUMERIC;
    String keyQues = dbHelper.QUIZ_KEY_QUESTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_stat);

        dbHelper = new QuizPoolDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        txt_longest = findViewById(R.id.quiz_stat_longest);
        txt_shortest = findViewById(R.id.quiz_stat_shortest);
        txt_average = findViewById(R.id.quiz_stat_average);
        String query = null;
        String question = null;
        String question_longest = null;
        String question_shortest = null;
        int length_longest = 0;
        int length_shortest = Integer.MAX_VALUE;
        int length_average = 0;
        int length_sum = 0;
        int count_multichoice = 0;
        int count_truefalse = 0;
        int count_numeric = 0;

        //sort out the longest/shortest/average length of question in multichoice
        query = "SELECT * FROM " + tableName_multichoice + ";";
        c = db.rawQuery(query, null);
        count_multichoice = c.getCount();
        //Read existed questions from database.
        c.moveToFirst();
        while(!c.isAfterLast()){
            question = c.getString( c.getColumnIndex( keyQues ) );
            //get the longest one
            if(question.length() > length_longest){
                length_longest = question.length();
                question_longest = question;
            }else if(question.length() < length_shortest){//get the shortest one
                length_shortest = question.length();
                question_shortest = question;
            }
            length_sum += question.length();
            c.moveToNext();
        }
        txt_longest.setText("The longest question is a MULTI CHOICE question:\n" + question_longest
                            + "\nIt has " + length_longest + " characters.");
        txt_shortest.setText("The shortest question is a MULTI CHOICE question:\n" + question_shortest
                            + "\nIt has " + length_shortest + " characters.");

        //sort out the longest/shortest/average length of question in truefalse
        query = "SELECT * FROM " + tableName_truefalse + ";";
        c = db.rawQuery(query, null);
        count_truefalse = c.getCount();
        //Read existed questions from database.
        c.moveToFirst();
        while(!c.isAfterLast()){
            question = c.getString( c.getColumnIndex( keyQues ) );
            //get the longest one
            if(question.length() > length_longest){
                length_longest = question.length();
                question_longest = question;
                txt_longest.setText("The longest question is a TRUE/FALSE question:\n" + question_longest
                        + "\nIt has " + length_longest + " characters.");
            }else if(question.length() < length_shortest){//get the shortest one
                length_shortest = question.length();
                question_shortest = question;
                txt_shortest.setText("The shortest question is a TRUE/FALSE question:\n" + question_shortest
                        + "\nIt has " + length_shortest + " characters.");
            }
            length_sum += question.length();
            c.moveToNext();
        }

        //sort out the longest/shortest/average length of question in numeric
        query = "SELECT * FROM " + tableName_numeric + ";";
        c = db.rawQuery(query, null);
        count_numeric = c.getCount();
        //Read existed questions from database.
        c.moveToFirst();
        while(!c.isAfterLast()){
            question = c.getString( c.getColumnIndex( keyQues ) );
            //get the longest one
            if(question.length() > length_longest){
                length_longest = question.length();
                question_longest = question;
                txt_longest.setText("The longest question is a NUMERIC question:\n" + question_longest
                        + "\nIt has " + length_longest + " characters.");
            }else if(question.length() < length_shortest){//get the shortest one
                length_shortest = question.length();
                question_shortest = question;
                txt_shortest.setText("The shortest question is a NUMERIC question:\n" + question_shortest
                        + "\nIt has " + length_shortest + " characters.");
            }
            length_sum += question.length();
            c.moveToNext();
        }

        //get average length of all the question
        length_average = length_sum / (count_multichoice + count_truefalse + count_numeric);
        txt_average.setText("The average question length is " + length_average + " characters.");
    }
}
