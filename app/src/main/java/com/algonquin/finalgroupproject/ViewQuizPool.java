package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        progressBar.setVisibility(View.VISIBLE);

        //in this case, “this” is the ChatWindow, which is-A Context object
        final QuizAdapter quizAdapter = new QuizAdapter(this);
        listview.setAdapter(quizAdapter);

        String query = "SELECT * FROM " + tableName + ";";
        Cursor c = db.rawQuery(query, null);

        //Read existed questions from database.
        c.moveToFirst();
        //counter is used to count the database record
        int counter = 0;
        while(!c.isAfterLast()){

            String question = c.getString( c.getColumnIndex( dbHelper.QUIZ_KEY_QUESTION_MULTICHOICE) );
            String answerA = c.getString( c.getColumnIndex( dbHelper.QUIZ_KEY_ANSWERA_MULTICHOICE) );
            String answerB = c.getString( c.getColumnIndex( dbHelper.QUIZ_KEY_ANSWERB_MULTICHOICE) );
            String answerC = c.getString( c.getColumnIndex( dbHelper.QUIZ_KEY_ANSWERC_MULTICHOICE) );
            String answerD = c.getString( c.getColumnIndex( dbHelper.QUIZ_KEY_ANSWERD_MULTICHOICE) );
            //ArrayList here is used to store one question
            ArrayList<String> oneQuestion = new ArrayList<String>();
            oneQuestion.add(question);
            oneQuestion.add(answerA);
            oneQuestion.add(answerB);
            oneQuestion.add(answerC);
            oneQuestion.add(answerD);
            quizPool.add(oneQuestion);
            //for debug
//            Log.i("ViewQuizPool", "SQL QUESTION:" + question );
//            Log.i("ViewQuizPool", "SQL ANSWERA:" + answerA );
//            Log.i("ViewQuizPool", "SQL ANSWERB:" + answerB );
//            Log.i("ViewQuizPool", "SQL ANSWERC:" + answerC );
//            Log.i("ViewQuizPool", "SQL ANSWERD:" + answerD );

            //update progress bar
            counter++;
            if(counter != c.getCount()){
                progressBar.setProgress(counter * 100 /(c.getCount()));
            }else{
                progressBar.setVisibility(View.INVISIBLE);
            }

            c.moveToNext();
        }
        //this restarts the process of getCount() & getView() to retrieve chat history
        quizAdapter.notifyDataSetChanged();

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
                quizAdapter.notifyDataSetChanged();

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

        //click a question will display the detailes on another fragment layout.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayList<String> oneQuestion = new ArrayList<>();
                oneQuestion = quizAdapter.getItem(position);
                String question = oneQuestion.get(0);
                String answerA = oneQuestion.get(1);
                String answerB = oneQuestion.get(2);
                String answerC = oneQuestion.get(3);
                String answerD = oneQuestion.get(4);

                //a bundle is used to pass message
                QuizMultiChoiceFragment quizMultiChoiceFragment = new QuizMultiChoiceFragment();
                //pass data to fragment
                Bundle bundle = new Bundle();
                bundle.putString("Question", question);
                bundle.putString("AnswerA", answerA);
                bundle.putString("AnswerB", answerB);
                bundle.putString("AnswerC", answerC);
                bundle.putString("AnswerD", answerD);

                Intent intent = new Intent(ViewQuizPool.this, QuizDetail.class);
                intent.putExtra("QuizMultiChoiceDetail", bundle);
                startActivityForResult(intent, 820, bundle);

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