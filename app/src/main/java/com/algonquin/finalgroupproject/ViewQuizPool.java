package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewQuizPool extends Activity {

    private ListView listview;
    private ArrayList<ArrayList<String>> quizPool = new ArrayList<>();
    private ProgressBar progressBar;
    private Button btn_addMultipleChoice;
    private TextView txt_question;
    private TextView txt_answerA;
    private TextView txt_answerB;
    private TextView txt_answerC;
    private TextView txt_answerD;

    //Get a writable database.
    private QuizPoolDatabaseHelper dbHelper;
    private SQLiteDatabase db = null;

    //Get table name and column name.
    String tableName = dbHelper.QUIZ_TABLE_NAME_MULTICHOICE;
    String keyMultiChoiceQues = dbHelper.QUIZ_KEY_QUESTION_MULTICHOICE;
    String keyAnswerA = dbHelper.QUIZ_KEY_ANSWERA_MULTICHOICE;
    String keyAnswerB = dbHelper.QUIZ_KEY_ANSWERB_MULTICHOICE;
    String keyAnswerC = dbHelper.QUIZ_KEY_ANSWERC_MULTICHOICE;
    String keyAnswerD = dbHelper.QUIZ_KEY_ANSWERD_MULTICHOICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quiz_pool);

        dbHelper = new QuizPoolDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        listview = findViewById(R.id.listview_quizpool);
        progressBar = findViewById(R.id.progressBar_Quiz);
        txt_question = findViewById(R.id.edittext_newQuiz);
        txt_answerA = findViewById(R.id.edittext_A);
        txt_answerB = findViewById(R.id.edittext_B);
        txt_answerC = findViewById(R.id.edittext_C);
        txt_answerD = findViewById(R.id.edittext_D);
        btn_addMultipleChoice = findViewById(R.id.button_addQuestion);

        //ArrayList here is used to store one quesiton information
        //final ArrayList<String> oneQuestion = new ArrayList<String>();

        progressBar.setVisibility(View.VISIBLE);

        //in this case, “this” is the ChatWindow, which is-A Context object
        final QuizAdapter messageAdapter = new QuizAdapter(this);
        listview.setAdapter(messageAdapter);

        String query = "SELECT * FROM " + tableName + ";";
        Cursor c = db.rawQuery(query, null);


        //Read existed messages from database.
        c.moveToFirst();
        while(!c.isAfterLast()){

            String question = c.getString( c.getColumnIndex( dbHelper.QUIZ_KEY_QUESTION_MULTICHOICE) );
            String answerA = c.getString( c.getColumnIndex( dbHelper.QUIZ_KEY_ANSWERA_MULTICHOICE) );
            String answerB = c.getString( c.getColumnIndex( dbHelper.QUIZ_KEY_ANSWERB_MULTICHOICE) );
            String answerC = c.getString( c.getColumnIndex( dbHelper.QUIZ_KEY_ANSWERC_MULTICHOICE) );
            String answerD = c.getString( c.getColumnIndex( dbHelper.QUIZ_KEY_ANSWERD_MULTICHOICE) );
            ArrayList<String> oneQuestion = new ArrayList<String>();
            //oneQuestion.clear();
            oneQuestion.add(question);
            oneQuestion.add(answerA);
            oneQuestion.add(answerB);
            oneQuestion.add(answerC);
            oneQuestion.add(answerD);
            quizPool.add(oneQuestion);
            //for debug
//            Log.i("ViewQuizPool", "quizPool:" + quizPool);
//            Log.i("ViewQuizPool", "SQL QUESTION:" + question );
//            Log.i("ViewQuizPool", "SQL ANSWERA:" + answerA );
//            Log.i("ViewQuizPool", "SQL ANSWERB:" + answerB );
//            Log.i("ViewQuizPool", "SQL ANSWERC:" + answerC );
//            Log.i("ViewQuizPool", "SQL ANSWERD:" + answerD );
            c.moveToNext();
        }
        //this restarts the process of getCount() & getView() to retrieve chat history
        messageAdapter.notifyDataSetChanged();

        //Print colomn name.
        Log.i("ViewQuizPool", "Cursor’s  column count = " + c.getColumnCount());
        for(int i = 0; c.getColumnCount() > i; i++){
            Log.i("ViewQuizPool", "Coloumn " + i + " : " + c.getColumnName(i));
        }

        //when click send button, clear the edit text and add the new message into database, update listview as well
        btn_addMultipleChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //New question.
                ArrayList<String> oneQuestion = new ArrayList<String>();
                String question = txt_question.getText().toString();
                oneQuestion.add(question);
                String answerA = txt_answerA.getText().toString();
                oneQuestion.add(answerA);
                String answerB = txt_answerB.getText().toString();
                oneQuestion.add(answerB);
                String answerC = txt_answerC.getText().toString();
                oneQuestion.add(answerC);
                String answerD = txt_answerD.getText().toString();
                oneQuestion.add(answerD);
                quizPool.add(oneQuestion);

                //this restarts the process of getCount() & getView()
                messageAdapter.notifyDataSetChanged();

                //when the user clicks “Send”, clear the edittext
                txt_question.setText("");
                txt_answerA.setText("");
                txt_answerB.setText("");
                txt_answerC.setText("");
                txt_answerD.setText("");

//                //Write new question to database.
                ContentValues cv = new ContentValues();
                cv.put(keyMultiChoiceQues, question);
                cv.put(keyAnswerA, answerA);
                cv.put(keyAnswerB, answerB);
                cv.put(keyAnswerC, answerC);
                cv.put(keyAnswerD, answerD);
                db.insert(tableName,null,cv);

            }
        });
    }

    private class QuizAdapter extends ArrayAdapter<ArrayList<String>> {
        public QuizAdapter(Context ctx) {
            super(ctx, 0);
        }

        LayoutInflater inflater = ViewQuizPool.this.getLayoutInflater();
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View result;
            TextView question;
            TextView answerA;
            TextView answerB;
            TextView answerC;
            TextView answerD;

            result = inflater.inflate(R.layout.quiz_pool_layout, null);
            question = result.findViewById(R.id.quizList);
            answerA = result.findViewById(R.id.quiz_Answer_A);
            answerB = result.findViewById(R.id.quiz_Answer_B);
            answerC = result.findViewById(R.id.quiz_Answer_C);
            answerD = result.findViewById(R.id.quiz_Answer_D);

            question.setText(getItem(position).get(0)); // get the string at position
            answerA.setText(getItem(position).get(1));
            answerB.setText(getItem(position).get(2));
            answerC.setText(getItem(position).get(3));
            answerD.setText(getItem(position).get(4));

            return result;
        }

        @Override
        public int getCount(){
            return quizPool.size();
        }

        @Override
        public ArrayList<String> getItem(int position){
            return quizPool.get(position);
        }

    }
}
