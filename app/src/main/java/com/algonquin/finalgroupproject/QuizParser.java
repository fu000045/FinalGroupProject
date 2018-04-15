package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuizParser extends Activity {

    //Get a writable database.
    private QuizPoolDatabaseHelper dbHelper;
    private SQLiteDatabase db = null;
    private Cursor c;
    private String query;
    //used for search database to check if the new question already exists
    private Boolean exist = false;
    //Get table name and column name.
    String tableName_multichoice = dbHelper.QUIZ_TABLE_NAME_MULTICHOICE;
    String tableName_truefalse = dbHelper.QUIZ_TABLE_NAME_TRUEFALSE;
    String tableName_numeric = dbHelper.QUIZ_TABLE_NAME_NUMERIC;
    String keyQues = dbHelper.QUIZ_KEY_QUESTION;
    String keyAnswerA = dbHelper.QUIZ_KEY_ANSWERA;
    String keyAnswerB = dbHelper.QUIZ_KEY_ANSWERB;
    String keyAnswerC = dbHelper.QUIZ_KEY_ANSWERC;
    String keyAnswerD = dbHelper.QUIZ_KEY_ANSWERD;
    String keyCorrect = dbHelper.QUIZ_KEY_CORRECT_ANSWER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new QuizPoolDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        //start a thread to parse xml information
        QuizQuery quiz = new QuizQuery();
        quiz.execute();
    }

    public class QuizQuery extends AsyncTask<String, Integer, String> {

        String question;
        String answerA;
        String answerB;
        String answerC;
        String answerD;
        String correct;
        String question_inDB;

        @Override
        protected String doInBackground(String... args) {

            String tagname;

            //From: Parsing XML Data.[Web Page] Retrieved from: https://developer.android.com/training/basics/network-ops/xml.html
            // Given a string representation of a URL, sets up a connection and gets an input stream.
            URL url;
            InputStream inStream = null;
            try {
                //conncet to an URL, and parser the xml info
                url = new URL("http://torunski.ca/CST2335/QuizInstance.xml");
                HttpURLConnection conn;
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();

                inStream = conn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(inStream, null);
                int eventType = parser.getEventType();

                //while not end of document
                while(eventType != XmlPullParser.END_DOCUMENT){
                    //check eventType
                    switch(eventType){
                        //if eventType is START_TAG, parser the info needed
                        case XmlPullParser.START_TAG:
                            tagname = parser.getName();
                            //if tag name is "MultipleChoiceQuestion", get the multi choice information
                            if (tagname.equalsIgnoreCase("MultipleChoiceQuestion")) {
                                question = parser.getAttributeValue(null, "question");
                                correct = parser.getAttributeValue(null, "correct");
                                if(correct.equals("1")){
                                    correct = "A";
                                }else if(correct.equals("2")){
                                    correct = "B";
                                }else if(correct.equals("3")){
                                    correct = "C";
                                }else if(correct.equals("4")) {
                                    correct = "D";
                                }else{
                                        throw new IllegalArgumentException("This answer is not accepted in multi choice question!");
                                    }
                                //if tag name is "Answer", get 4 multi choice answers

                                //if tag name is "TrueFalseQuestion", get the true false question and answer
                            }else if(tagname.equalsIgnoreCase("TrueFalseQuestion")){
                                question = parser.getAttributeValue(null, "question");
                                correct = parser.getAttributeValue(null, "answer");
                                //search the table, if the question already in database, no insert
                                query = "SELECT * FROM " + tableName_truefalse + ";";
                                c = db.rawQuery(query, null);
                                c.moveToFirst();
                                while(!c.isAfterLast()){
                                    question_inDB = c.getString( c.getColumnIndex( keyQues ) );
                                    //compare the current question in database with new parser question
                                    //if questions are the same, do not insert into database and break;
                                    if(question.equals(question_inDB)){
                                        exist = true;
                                        break;
                                    }
                                    c.moveToNext();
                                }
                                if(!exist){
                                    //write multi choice question to database
                                    query = "INSERT INTO " + tableName_truefalse + " ( " + keyQues
                                            + " , " + keyCorrect + " ) VALUES ( '" + question + "' , '" + correct + "' );";
                                    db.execSQL(query);
                                }else{
                                    Toast.makeText(QuizParser.this, R.string.toast_quizTFParser, Toast.LENGTH_SHORT).show();
                                    exist = false;
                                }
                                //if tag name is "NumericQuestion", get the numeric question and answer
                            }else if (tagname.equalsIgnoreCase("NumericQuestion")){
                                question = parser.getAttributeValue(null, "question");
                                answerA = parser.getAttributeValue(null, "accuracy");
                                correct = parser.getAttributeValue(null, "answer");
                                //search the table, if the question already in database, no insert
                                query = "SELECT * FROM " + tableName_numeric + ";";
                                c = db.rawQuery(query, null);
                                c.moveToFirst();
                                while(!c.isAfterLast()){
                                    question_inDB = c.getString( c.getColumnIndex( keyQues ) );
                                    //compare the current question in database with new parser question
                                    //if questions are the same, do not insert into database and break;
                                    if(question.equals(question_inDB)){
                                        exist = true;
                                        break;
                                    }
                                    c.moveToNext();
                                }
                                if(!exist){
                                    //write numeric question to database
                                    query = "INSERT INTO " + tableName_numeric + " ( " + keyQues
                                            + " , " + keyAnswerA + " , " + keyCorrect + " ) VALUES ( '"
                                            + question + "' , '" + answerA + "' , '" + correct + "' );";
                                    db.execSQL(query);
                                }else{
                                    Toast.makeText(QuizParser.this, R.string.toast_quizNumericParser, Toast.LENGTH_SHORT).show();
                                    exist = false;
                                }
                            }
                            break;
                        //if eventType is TEXT, parser the info needed
                        case XmlPullParser.TEXT:
                            //as there are many \n && \t exist in XML file,
                            //here use regular expression to get rid of any TEXT without any regular characters.
                            if(parser.getText().matches("^[\\w.-]+$")) {
                                if (answerA == null) {
                                    answerA = parser.getText();
                                    //once parser TEXT string for answerA, quit this loop and start the next loop
                                    break;
                                } else if (answerB == null) {
                                    answerB = parser.getText();
                                    break;
                                } else if (answerC == null) {
                                    answerC = parser.getText();
                                    break;
                                } else if (answerD == null) {
                                    //when get the fourth answer choice, ready to insert the new question into database
                                    answerD = parser.getText();
                                    Log.d("multi choice: ", answerA+"  "+answerB+"  "+answerC+"  "+answerD);
                                    //search the table, if the question already in database, no insert
                                    query = "SELECT * FROM " + tableName_multichoice + ";";
                                    c = db.rawQuery(query, null);
                                    c.moveToFirst();
                                    while (!c.isAfterLast()) {
                                        question_inDB = c.getString(c.getColumnIndex(keyQues));
                                        //compare the current question in database with new parser question
                                        //if questions are the same, do not insert into database
                                        if (question.equals(question_inDB)) {
                                            exist = true;
                                            break;
                                        }
                                        c.moveToNext();
                                    }
                                    if (!exist) {
                                        //write multi choice question to database
                                        query = "INSERT INTO " + tableName_multichoice + " ( " + keyQues + " , " + keyAnswerA
                                                + " , " + keyAnswerB + " , " + keyAnswerC + " , " + keyAnswerD
                                                + " , " + keyCorrect + " ) VALUES ( '" + question + "' , '" + answerA + "' , '"
                                                + answerB + "' , '" + answerC + "' , '" + answerD + "' , '" + correct + "' );";
                                        db.execSQL(query);
                                    } else {
                                        Toast.makeText(QuizParser.this, R.string.toast_quizMultiChoiceParser, Toast.LENGTH_SHORT).show();
                                        exist = false;
                                    }
                                    //once get the four answers, set them to null, prepared for next new question insert
                                    answerA = null;
                                    answerB = null;
                                    answerC = null;
                                    answerD = null;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(inStream != null ) {
                        inStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        //get the info from doInBackground(), and update the info on GUI
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //once finish parser XML, jump to QuizCreaterActivity to check the newly add questions.
            Intent intent = new Intent(QuizParser.this, QuizPool.class);
            startActivity(intent);
        }
    }
}
