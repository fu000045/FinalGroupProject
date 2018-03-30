package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizMultiChoicePool extends Activity {

    private ListView listview;
//    private ArrayList<ArrayList<String>> quizPool = new ArrayList<>();
    private ProgressBar progressBar;
    private Button btn_addMultipleChoice;
    private TextView txt_question;
    private TextView txt_answerA;
    private TextView txt_answerB;
    private TextView txt_answerC;
    private TextView txt_answerD;
    private TextView txt_correct;
    private boolean isTablet = false;

    //Get a writable database.
    private QuizPoolDatabaseHelper dbHelper;
    private SQLiteDatabase db = null;
    private Cursor c;

    QuizMultiChoiceFragment quizMultiChoiceFragment = new QuizMultiChoiceFragment();

    //in this case, “this” is the ChatWindow, which is-A Context object
    QuizAdapter quizAdapter;

    //Get table name and column name.
    String tableName = dbHelper.QUIZ_TABLE_NAME_MULTICHOICE;
    String keyID = dbHelper.QUIZ_KEY_ID_MULTICHOICE;
    String keyMultiChoiceQues = dbHelper.QUIZ_KEY_QUESTION_MULTICHOICE;
    String keyAnswerA = dbHelper.QUIZ_KEY_ANSWERA_MULTICHOICE;
    String keyAnswerB = dbHelper.QUIZ_KEY_ANSWERB_MULTICHOICE;
    String keyAnswerC = dbHelper.QUIZ_KEY_ANSWERC_MULTICHOICE;
    String keyAnswerD = dbHelper.QUIZ_KEY_ANSWERD_MULTICHOICE;
    String keyCorrect = dbHelper.QUIZ_KEY_CORRECT_ANSWER_MULTICHOICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_multi_choice_pool);

        dbHelper = new QuizPoolDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        listview = findViewById(R.id.listview_quizpool);
        progressBar = findViewById(R.id.progressBar_Quiz);
        txt_question = findViewById(R.id.edittext_newQuiz);
        txt_answerA = findViewById(R.id.edittext_A);
        txt_answerB = findViewById(R.id.edittext_B);
        txt_answerC = findViewById(R.id.edittext_C);
        txt_answerD = findViewById(R.id.edittext_D);
        txt_correct = findViewById(R.id.edittext_correct);
        btn_addMultipleChoice = findViewById(R.id.button_addQuestion);

        progressBar.setVisibility(View.VISIBLE);

        isTablet = (findViewById(R.id.tablet_framelayout) != null);
        //in this case, “this” is the ChatWindow, which is-A Context object
        quizAdapter = new QuizAdapter(this);
        listview.setAdapter(quizAdapter);

        String query = "SELECT * FROM " + tableName + ";";
        c = db.rawQuery(query, null);

        //Read existed questions from database.
        c.moveToFirst();
        //counter is used to count the database record
        int counter = 0;
        while(!c.isAfterLast()){

            String question = c.getString( c.getColumnIndex( keyMultiChoiceQues ) );
            String answerA = c.getString( c.getColumnIndex( keyAnswerA ) );
            String answerB = c.getString( c.getColumnIndex( keyAnswerB ) );
            String answerC = c.getString( c.getColumnIndex( keyAnswerC ) );
            String answerD = c.getString( c.getColumnIndex( keyAnswerD ) );
            String correct = c.getString( c.getColumnIndex( keyCorrect));
            //ArrayList here is used to store one question
            ArrayList<String> oneQuestion = new ArrayList<String>();
            oneQuestion.add(question);
            oneQuestion.add(answerA);
            oneQuestion.add(answerB);
            oneQuestion.add(answerC);
            oneQuestion.add(answerD);
            oneQuestion.add(correct);
//            quizPool.add(oneQuestion);
            //for debug
//            Log.i("QuizMultiChoicePool", "SQL QUESTION:" + question );
//            Log.i("QuizMultiChoicePool", "SQL ANSWERA:" + answerA );
//            Log.i("QuizMultiChoicePool", "SQL ANSWERB:" + answerB );
//            Log.i("QuizMultiChoicePool", "SQL ANSWERC:" + answerC );
//            Log.i("QuizMultiChoicePool", "SQL ANSWERD:" + answerD );

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

        //when click ADD button, clear the edit text and add the new message into database, update listview as well
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
                String correct = txt_correct.getText().toString();
                oneQuestion.add(correct);

                if((question.isEmpty() || question.trim().isEmpty())
                        || (answerA.isEmpty() || answerA.trim().isEmpty())
                        || (answerB.isEmpty() || answerB.trim().isEmpty())
                        || (answerC.isEmpty() || answerC.trim().isEmpty())
                        || (answerD.isEmpty() || answerD.trim().isEmpty())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuizMultiChoicePool.this);
                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(R.string.quiz_multichoice_dialog_message)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
                }else{
                    //this restarts the process of getCount() & getView()
                    quizAdapter.notifyDataSetChanged();

                    //when the user clicks “Send”, clear the edittext
                    txt_question.setText("");
                    txt_answerA.setText("");
                    txt_answerB.setText("");
                    txt_answerC.setText("");
                    txt_answerD.setText("");
                    txt_correct.setText("");

                    //Write new question to database.
                    ContentValues cv = new ContentValues();
                    cv.put(keyMultiChoiceQues, question);
                    cv.put(keyAnswerA, answerA);
                    cv.put(keyAnswerB, answerB);
                    cv.put(keyAnswerC, answerC);
                    cv.put(keyAnswerD, answerD);
                    cv.put(keyCorrect, correct);
                    db.insert(tableName,null,cv);
                }
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
                String correct = oneQuestion.get(5);
                //id is the database id column value returned by funciton getItemId()
                long Id = id;

                //a bundle is used to pass message
                //pass data to fragment
                Bundle bundle = new Bundle();
                bundle.putString("Question", question);
                bundle.putString("AnswerA", answerA);
                bundle.putString("AnswerB", answerB);
                bundle.putString("AnswerC", answerC);
                bundle.putString("AnswerD", answerD);
                bundle.putString("Correct", correct);
                bundle.putLong("ID", Id);

                if(isTablet){//for tablet
                    quizMultiChoiceFragment.setArguments(bundle);
                    //tell the MessageFragment this is a tablet
                    quizMultiChoiceFragment.setIsTablet(true);
                    //start a FragmentTransaction to add a fragment to the FrameLayout
                    getFragmentManager().beginTransaction().replace(R.id.tablet_framelayout,quizMultiChoiceFragment).commit();
                }else{//for phone
                    //tell the MessageFragment this is not a tablet
                    quizMultiChoiceFragment.setIsTablet(false);
                    //Jump to MessageDetails, pass the message and id information
                    Intent intent = new Intent(QuizMultiChoicePool.this, QuizMultiChoiceDetail.class);
                    intent.putExtra("QuizMultiChoiceDetail", bundle);
                    startActivityForResult(intent, 820, bundle);
                }

            }
        });
    }

    //For phone to delete a message
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        long id = extras.getLong("ID");
        String question = extras.getString("Question");
        String answerA = extras.getString("AnswerA");
        String answerB = extras.getString("AnswerB");
        String answerC = extras.getString("AnswerC");
        String answerD = extras.getString("AnswerD");
        String correct = extras.getString("Correct");
        String query = "";
        //
        if(requestCode == 820){
            if (resultCode == 666) {
                query = "UPDATE " + tableName + " SET " + keyMultiChoiceQues + " = " + "'" + question
                        + "', " + keyAnswerA + " = " + "'" + answerA
                        + "', " + keyAnswerB + " = " + "'" + answerB
                        + "', " + keyAnswerC + " = " + "'" + answerC
                        + "', " + keyAnswerD + " = " + "'" + answerD
                        + "', " + keyCorrect + " = " + "'" + correct
                        + "' WHERE " + keyID + " = " + id + ";";
            }else if(resultCode == Activity.RESULT_OK){
                query = "DELETE FROM " + tableName +" WHERE " + keyID + " = " + id + ";";
            }
            db.execSQL(query);
            quizAdapter.notifyDataSetChanged();
        }
    }

    //for tablet to update a message
    public void updateForTablet(long id, String ques, String A, String B, String C, String D, String crt){
        String query = "UPDATE " + tableName + " SET " + keyMultiChoiceQues + " = " + "'" + ques
                + "', " + keyAnswerA + " = " + "'" + A
                + "', " + keyAnswerB + " = " + "'" + B
                + "', " + keyAnswerC + " = " + "'" + C
                + "', " + keyAnswerD + " = " + "'" + D
                + "', " + keyCorrect + " = " + "'" + crt
                + "' WHERE " + keyID + " = " + id + ";";
        db.execSQL(query);
        //for debug
        //Log.d("ChatWindow", chat.toString());
        quizAdapter.notifyDataSetChanged();
    }

    //for tablet to delete a message
    public void deleteForTablet(long id){
        long Id = id;
        String query = "DELETE FROM " + tableName + " WHERE " + keyID + " = " + id + ";";
        db.execSQL(query);
        quizAdapter.notifyDataSetChanged();

        getFragmentManager().beginTransaction().remove(quizMultiChoiceFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private class QuizAdapter extends ArrayAdapter<ArrayList<String>> {
        public QuizAdapter(Context ctx) {
            super(ctx, 0);
        }

        LayoutInflater inflater = QuizMultiChoicePool.this.getLayoutInflater();
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View result;
            RadioGroup checkAnswer;
            TextView question;
            TextView answerA;
            TextView answerB;
            TextView answerC;
            TextView answerD;
            TextView correct;

            result = inflater.inflate(R.layout.quiz_multi_choice_layout, null);
            checkAnswer = result.findViewById(R.id.radioCheck);
            question = result.findViewById(R.id.quizList);
            answerA = result.findViewById(R.id.quiz_Answer_A);
            answerB = result.findViewById(R.id.quiz_Answer_B);
            answerC = result.findViewById(R.id.quiz_Answer_C);
            answerD = result.findViewById(R.id.quiz_Answer_D);
            correct = result.findViewById(R.id.tv_correct);

            question.setText("Q: " + getItem(position).get(0)); // get the string at position
            answerA.setText("A. " + getItem(position).get(1));
            answerB.setText("B. " + getItem(position).get(2));
            answerC.setText("C. " + getItem(position).get(3));
            answerD.setText("D. " + getItem(position).get(4));
            correct.setText("Correct Answer is: " + getItem(position).get(5));
            if(getItem(position).get(5).equalsIgnoreCase("A")){
                checkAnswer.check(R.id.quiz_Answer_A);
            }else if(getItem(position).get(5).equalsIgnoreCase("B")){
                checkAnswer.check(R.id.quiz_Answer_B);
            }else if(getItem(position).get(5).equalsIgnoreCase("C")){
                checkAnswer.check(R.id.quiz_Answer_C);
            }else if(getItem(position).get(5).equalsIgnoreCase("D")){
                checkAnswer.check(R.id.quiz_Answer_D);
            }else{
                throw new IllegalArgumentException("No such answer accepted!");
            }

            return result;
        }

        @Override
        public int getCount(){
            String query = "SELECT * FROM " + tableName + ";";
            c = db.rawQuery(query, null);
            return c.getCount();
        }

        @Override
        public ArrayList<String> getItem(int position){
            String query = "SELECT * FROM " + tableName + ";";
            c = db.rawQuery(query, null);
            ArrayList<String> oneQuestion = new ArrayList<>();

            c.moveToPosition(position);
            oneQuestion.add(c.getString(c.getColumnIndex(keyMultiChoiceQues)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyAnswerA)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyAnswerB)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyAnswerC)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyAnswerD)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyCorrect)));
            return oneQuestion;
        }

        @Override
        public long getItemId(int position){
            String query = "SELECT * FROM " + tableName + ";";
            c = db.rawQuery(query, null);
            c.moveToPosition(position);
            int id = c.getInt(c.getColumnIndex(keyID));
            return id;
        }
    }
}