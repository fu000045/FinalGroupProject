package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizPool extends Activity {

    private ListView listview_multichoice;
    private ListView listview_truefalse;
    private ListView listview_numeric;
    private ProgressBar progressBar;
    private Button btn_addQues;
    private Button btn_return;
    private boolean isTablet = false;

    //Get a writable database.
    private QuizPoolDatabaseHelper dbHelper;
    private SQLiteDatabase db = null;
    private Cursor c;
    private Bundle bundle = new Bundle();

    QuizFragment quizFragment = null;

    //in this case, “this” is the ChatWindow, which is-A Context object
    MultichoiceAdapter multichoiceAdapter;
    TrueFalseAdapter truefalseAdapter;
    NumericAdapter numericAdapter;

    //Get table name and column name.
    String tableName_multichoice = dbHelper.QUIZ_TABLE_NAME_MULTICHOICE;
    String tableName_truefalse = dbHelper.QUIZ_TABLE_NAME_TRUEFALSE;
    String tableName_numeric = dbHelper.QUIZ_TABLE_NAME_NUMERIC;
    String keyID = dbHelper.QUIZ_KEY_ID;
    String keyQues = dbHelper.QUIZ_KEY_QUESTION;
    String keyAnswerA = dbHelper.QUIZ_KEY_ANSWERA;
    String keyAnswerB = dbHelper.QUIZ_KEY_ANSWERB;
    String keyAnswerC = dbHelper.QUIZ_KEY_ANSWERC;
    String keyAnswerD = dbHelper.QUIZ_KEY_ANSWERD;
    String keyCorrect = dbHelper.QUIZ_KEY_CORRECT_ANSWER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_pool);

        dbHelper = new QuizPoolDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        listview_multichoice = findViewById(R.id.listview_multichoice);
        listview_truefalse = findViewById(R.id.listview_truefalse);
        listview_numeric = findViewById(R.id.listview_numeric);
        progressBar = findViewById(R.id.progressBar_Quiz);

        btn_addQues = findViewById(R.id.button_addQuestion);
        btn_return = findViewById(R.id.button_return);
        progressBar.setVisibility(View.VISIBLE);
        isTablet = (findViewById(R.id.tablet_framelayout) != null);

        //multi choice listview
        multichoiceAdapter = new MultichoiceAdapter(this);
        listview_multichoice.setAdapter(multichoiceAdapter);
        String query = "SELECT * FROM " + tableName_multichoice + ";";
        c = db.rawQuery(query, null);
        //Read existed questions from database.
        c.moveToFirst();
        //counter is used to count the database record
        int counter = 0;
        while(!c.isAfterLast()){
            String question = c.getString( c.getColumnIndex( keyQues ) );
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
            c.moveToNext();
        }
        //update progress bar
        progressBar.setProgress(33);
        //this restarts the process of getCount() & getView() to retrieve multi choice list
        multichoiceAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listview_multichoice);

        //true false listview
        truefalseAdapter = new TrueFalseAdapter(this);
        listview_truefalse.setAdapter(truefalseAdapter);
        query = "SELECT * FROM " + tableName_truefalse + ";";
        c = db.rawQuery(query, null);
        //Read existed questions from database.
        c.moveToFirst();
        while(!c.isAfterLast()){
            String question = c.getString( c.getColumnIndex( keyQues ) );
            String correct = c.getString( c.getColumnIndex( keyCorrect));
            //ArrayList here is used to store one question
            ArrayList<String> oneQuestion = new ArrayList<String>();
            oneQuestion.add(question);
            oneQuestion.add(correct);
            c.moveToNext();
        }
        //update progress bar
        progressBar.setProgress(67);
        truefalseAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listview_truefalse);

        //numeric listview
        numericAdapter = new NumericAdapter(this);
        listview_numeric.setAdapter(numericAdapter);
        query = "SELECT * FROM " + tableName_numeric + ";";
        c = db.rawQuery(query, null);
        //Read existed questions from database.
        c.moveToFirst();
        while(!c.isAfterLast()){
            String question = c.getString( c.getColumnIndex( keyQues ) );
            String answerA = c.getString( c.getColumnIndex( keyAnswerA));
            String correct = c.getString( c.getColumnIndex( keyCorrect));
            //ArrayList here is used to store one question
            ArrayList<String> oneQuestion = new ArrayList<String>();
            oneQuestion.add(question);
            oneQuestion.add(answerA);
            oneQuestion.add(correct);
            c.moveToNext();
        }
        //update progress bar
        progressBar.setProgress(100);
        progressBar.setVisibility(View.INVISIBLE);
        numericAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listview_numeric);

        //when click ADD button, clear the edit text and add the new question into database, update listview as well
        btn_addQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizPool.this);
                builder.setMessage(R.string.quiz_pool_message)
                        .setPositiveButton(R.string.quiz_multichoice, new DialogInterface.OnClickListener() {
                            Bundle bundle = new Bundle();
                            public void onClick(DialogInterface dialog, int id) {
                                bundle.putString("QuestionType", "MultiChoice");
                                if(isTablet){//for tablet
                                    quizFragment = new QuizFragment();
                                    quizFragment.setArguments(bundle);
                                    //tell the MessageFragment this is a tablet
                                    quizFragment.setIsTablet(true);
                                    //start a FragmentTransaction to add a fragment to the FrameLayout
                                    getFragmentManager().beginTransaction().replace(R.id.tablet_framelayout, quizFragment).commit();
                                }else{//for phone
                                    //tell the MessageFragment this is not a tablet
                                    //quizFragment.setIsTablet(false);
                                    //Jump to MessageDetails, pass the message and id information
                                    Intent intent = new Intent(QuizPool.this, QuizDetail.class);
                                    intent.putExtra("QuizDetail", bundle);
                                    startActivityForResult(intent, 820, bundle);
                                }
                            }
                        })
                        .setNegativeButton(R.string.quiz_truefalse, new DialogInterface.OnClickListener() {
                            Bundle bundle = new Bundle();
                            public void onClick(DialogInterface dialog, int id) {
                                bundle.putString("QuestionType", "TrueFalse");
                                if(isTablet){//for tablet
                                    quizFragment = new QuizFragment();
                                    quizFragment.setArguments(bundle);
                                    //tell the MessageFragment this is a tablet
                                    quizFragment.setIsTablet(true);
                                    //start a FragmentTransaction to add a fragment to the FrameLayout
                                    getFragmentManager().beginTransaction().replace(R.id.tablet_framelayout, quizFragment).commit();
                                }else{//for phone
                                    //tell the MessageFragment this is not a tablet
                                    //quizFragment.setIsTablet(false);
                                    //Jump to MessageDetails, pass the message and id information
                                    Intent intent = new Intent(QuizPool.this, QuizDetail.class);
                                    intent.putExtra("QuizDetail", bundle);
                                    startActivityForResult(intent, 820, bundle);
                                }
                            }
                        })
                        .setNeutralButton(R.string.quiz_numeric, new DialogInterface.OnClickListener() {
                            Bundle bundle = new Bundle();
                            public void onClick(DialogInterface dialog, int id) {
                                bundle.putString("QuestionType", "Numeric");
                                if(isTablet){//for tablet
                                    quizFragment = new QuizFragment();
                                    quizFragment.setArguments(bundle);
                                    //tell the MessageFragment this is a tablet
                                    quizFragment.setIsTablet(true);
                                    //start a FragmentTransaction to add a fragment to the FrameLayout
                                    getFragmentManager().beginTransaction().replace(R.id.tablet_framelayout, quizFragment).commit();
                                }else{//for phone
                                    //tell the MessageFragment this is not a tablet
                                    //quizFragment.setIsTablet(false);
                                    //Jump to MessageDetails, pass the message and id information
                                    Intent intent = new Intent(QuizPool.this, QuizDetail.class);
                                    intent.putExtra("QuizDetail", bundle);
                                    startActivityForResult(intent, 820, bundle);
                                }
                            }
                        })
                        .show();

            }
        });
        //return button to return to QuizCreator activity
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizPool.this, QuizCreatorActivity.class);
                startActivity(intent);
            }
        });

        //click a question will display the detailes on another fragment layout.
        //for multi choice
        listview_multichoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayList<String> oneQuestion = new ArrayList<>();
                oneQuestion = multichoiceAdapter.getItem(position);
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
                bundle.putString("QuestionType", "MultiChoice");
                bundle.putString("Question", question);
                bundle.putString("AnswerA", answerA);
                bundle.putString("AnswerB", answerB);
                bundle.putString("AnswerC", answerC);
                bundle.putString("AnswerD", answerD);
                bundle.putString("Correct", correct);
                bundle.putLong("ID", Id);

                if(isTablet){//for tablet
                    quizFragment = new QuizFragment();
                    quizFragment.setArguments(bundle);
                    //tell the MessageFragment this is a tablet
                    quizFragment.setIsTablet(true);
                    //start a FragmentTransaction to add a fragment to the FrameLayout
                    getFragmentManager().beginTransaction().replace(R.id.tablet_framelayout, quizFragment).commit();
                }else{//for phone
                    //tell the MessageFragment this is not a tablet
                    //quizFragment.setIsTablet(false);
                    //Jump to MessageDetails, pass the message and id information
                    Intent intent = new Intent(QuizPool.this, QuizDetail.class);
                    intent.putExtra("QuizDetail", bundle);
                    startActivityForResult(intent, 820, bundle);
                }

            }
        });

        //for true false
        listview_truefalse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayList<String> oneQuestion = new ArrayList<>();
                oneQuestion = truefalseAdapter.getItem(position);
                String question = oneQuestion.get(0);
                String correct = oneQuestion.get(1);
                //id is the database id column value returned by funciton getItemId()
                long Id = id;

                //a bundle is used to pass message
                //pass data to fragment
                bundle.putString("QuestionType", "TrueFalse");
                bundle.putString("Question", question);
                bundle.putString("Correct", correct);
                bundle.putLong("ID", Id);

                if(isTablet){//for tablet
                    quizFragment = new QuizFragment();
                    quizFragment.setArguments(bundle);
                    //tell the MessageFragment this is a tablet
                    quizFragment.setIsTablet(true);
                    //start a FragmentTransaction to add a fragment to the FrameLayout
                    getFragmentManager().beginTransaction().replace(R.id.tablet_framelayout, quizFragment).commit();
                }else{//for phone
                    //tell the MessageFragment this is not a tablet
                    //quizFragment.setIsTablet(false);
                    //Jump to MessageDetails, pass the message and id information
                    Intent intent = new Intent(QuizPool.this, QuizDetail.class);
                    intent.putExtra("QuizDetail", bundle);
                    startActivityForResult(intent, 820, bundle);
                }

            }
        });

        //for numeric
        listview_numeric.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayList<String> oneQuestion = new ArrayList<>();
                oneQuestion = numericAdapter.getItem(position);
                String question = oneQuestion.get(0);
                String answerA = oneQuestion.get(1);
                String correct = oneQuestion.get(2);
                //id is the database id column value returned by funciton getItemId()
                long Id = id;

                //a bundle is used to pass message
                //pass data to fragment
                bundle.putString("QuestionType", "Numeric");
                bundle.putString("Question", question);
                bundle.putString("AnswerA", answerA);
                bundle.putString("Correct", correct);
                bundle.putLong("ID", Id);

                if(isTablet){//for tablet
                    quizFragment = new QuizFragment();
                    quizFragment.setArguments(bundle);
                    //tell the MessageFragment this is a tablet
                    quizFragment.setIsTablet(true);
                    //start a FragmentTransaction to add a fragment to the FrameLayout
                    getFragmentManager().beginTransaction().replace(R.id.tablet_framelayout, quizFragment).commit();
                }else{//for phone
                    //tell the MessageFragment this is not a tablet
                    //quizFragment.setIsTablet(false);
                    //Jump to MessageDetails, pass the message and id information
                    Intent intent = new Intent(QuizPool.this, QuizDetail.class);
                    intent.putExtra("QuizDetail", bundle);
                    startActivityForResult(intent, 820, bundle);
                }

            }
        });
    }

    //For phone to add/update/delete a message
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras = data.getExtras();
        long id = extras.getLong("ID");
        String questionType = extras.getString("QuestionType");
        String question = extras.getString("Question");
        String answerA = extras.getString("AnswerA");
        String answerB = extras.getString("AnswerB");
        String answerC = extras.getString("AnswerC");
        String answerD = extras.getString("AnswerD");
        String correct = extras.getString("Correct");
        String query = "";
        //multi choice
        if(questionType.equals("MultiChoice")){
            if(requestCode == 820){
                if (resultCode == 222) {
                    query = "INSERT INTO " + tableName_multichoice + " ( " + keyQues + " , " + keyAnswerA
                            + " , " + keyAnswerB + " , " + keyAnswerC + " , " + keyAnswerD
                            + " , " + keyCorrect + " ) VALUES ( '" + question + "' , '" + answerA + "' , '"
                            + answerB + "' , '" + answerC + "' , '" + answerD + "' , '" + correct + "' );";
                }else if(resultCode == 666){
                    query = "UPDATE " + tableName_multichoice + " SET " + keyQues + " = '" + question
                            + "', " + keyAnswerA + " = '" + answerA
                            + "', " + keyAnswerB + " = '" + answerB
                            + "', " + keyAnswerC + " = '" + answerC
                            + "', " + keyAnswerD + " = '" + answerD
                            + "', " + keyCorrect + " = '" + correct
                            + "' WHERE " + keyID + " = " + id + ";";
                }else if(resultCode == Activity.RESULT_OK){
                    query = "DELETE FROM " + tableName_multichoice +" WHERE " + keyID + " = " + id + ";";
                }
                db.execSQL(query);
                multichoiceAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listview_multichoice);
            }
        }//for true false
        else if(questionType.equals("TrueFalse")){
            if(requestCode == 820){
                if (resultCode == 222) {
                    query = "INSERT INTO " + tableName_truefalse + " ( " + keyQues
                            + " , " + keyCorrect + " ) VALUES ( '" + question + "' , '" + correct + "' );";
                }else if(resultCode == 666){
                    query = "UPDATE " + tableName_truefalse + " SET " + keyQues + " = '" + question
                            + "', " + keyCorrect + " = '" + correct
                            + "' WHERE " + keyID + " = " + id + ";";
                }else if(resultCode == Activity.RESULT_OK){
                    query = "DELETE FROM " + tableName_truefalse +" WHERE " + keyID + " = " + id + ";";
                }
                db.execSQL(query);
                truefalseAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listview_truefalse);
            }
        }//for numeric
        else if(questionType.equals("Numeric")){
            if(requestCode == 820){
                if (resultCode == 222) {
                    query = "INSERT INTO " + tableName_numeric + " ( " + keyQues
                            + " , " + keyAnswerA + " , " + keyCorrect + " ) VALUES ( '"
                            + question + "' , '" + answerA + "' , '" + correct + "' );";
                }else if(resultCode == 666){
                    query = "UPDATE " + tableName_numeric + " SET " + keyQues + " = '" + question
                            + "', " + keyAnswerA + " = '" + answerA + "', " + keyCorrect + " = '" + correct
                            + "' WHERE " + keyID + " = " + id + ";";
                }else if(resultCode == Activity.RESULT_OK){
                    query = "DELETE FROM " + tableName_numeric +" WHERE " + keyID + " = " + id + ";";
                }
                db.execSQL(query);
                numericAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listview_numeric);
            }
        }

    }

    //for tablet to add a message
    public void addForTablet(String questionType, long id, String question, String answerA,
                             String answerB, String answerC, String answerD, String correct){
        String query;
        if(questionType.equals("MultiChoice")){
            query = "INSERT INTO " + tableName_multichoice + " ( " + keyQues + " , " + keyAnswerA
                    + " , " + keyAnswerB + " , " + keyAnswerC + " , " + keyAnswerD
                    + " , " + keyCorrect + " ) VALUES ( '" + question + "' , '" + answerA + "' , '"
                    + answerB + "' , '" + answerC + "' , '" + answerD + "' , '" + correct + "' );";
            db.execSQL(query);
            multichoiceAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listview_multichoice);
        }else if(questionType.equals("TrueFalse")){
            query = "INSERT INTO " + tableName_truefalse + " ( " + keyQues
                    + " , " + keyCorrect + " ) VALUES ( '" + question + "' , '" + correct + "' );";
            db.execSQL(query);
            truefalseAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listview_truefalse);
        }else if(questionType.equals("Numeric")){
            query = "INSERT INTO " + tableName_numeric + " ( " + keyQues
                    + " , " + keyAnswerA + " , " + keyCorrect + " ) VALUES ( '"
                    + question + "' , '" + answerA + "' , '" + correct + "' );";
            db.execSQL(query);
            numericAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listview_numeric);
        }
    }

    //for tablet to update a message
    public void updateForTablet(String questionType, long id, String question, String answerA,
                                String answerB, String answerC, String answerD, String correct){
        String query;
        if(questionType.equals("MultiChoice")){
            query = "UPDATE " + tableName_multichoice + " SET " + keyQues + " = " + "'" + question
                    + "', " + keyAnswerA + " = " + "'" + answerA
                    + "', " + keyAnswerB + " = " + "'" + answerB
                    + "', " + keyAnswerC + " = " + "'" + answerC
                    + "', " + keyAnswerD + " = " + "'" + answerD
                    + "', " + keyCorrect + " = " + "'" + correct
                    + "' WHERE " + keyID + " = " + id + ";";
            db.execSQL(query);
            multichoiceAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listview_multichoice);
        }else if(questionType.equals("TrueFalse")){
            query = "UPDATE " + tableName_truefalse + " SET " + keyQues + " = " + "'" + question
                    + "', " + keyCorrect + " = '" + correct
                    + "' WHERE " + keyID + " = " + id + ";";
            db.execSQL(query);
            truefalseAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listview_truefalse);
        }else if(questionType.equals("Numeric")){
            query = "UPDATE " + tableName_numeric + " SET " + keyQues + " = '" + question
                    + "', " + keyAnswerA + " = '" + answerA + "', " + keyCorrect + " = '" + correct
                    + "' WHERE " + keyID + " = " + id + ";";
            db.execSQL(query);
            numericAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listview_numeric);
        }
    }

    //for tablet to delete a message
    public void deleteForTablet(String questionType, long id){
        String query;
        if(questionType.equals("MultiChoice")){
            query = "DELETE FROM " + tableName_multichoice + " WHERE " + keyID + " = " + id + ";";
            db.execSQL(query);
            multichoiceAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listview_multichoice);
        }else if(questionType.equals("TrueFalse")){
            query = "DELETE FROM " + tableName_truefalse + " WHERE " + keyID + " = " + id + ";";
            db.execSQL(query);
            truefalseAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listview_truefalse);
        }else if(questionType.equals("Numeric")){
            query = "DELETE FROM " + tableName_numeric + " WHERE " + keyID + " = " + id + ";";
            db.execSQL(query);
            numericAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listview_numeric);
        }

        if(quizFragment != null) {
            getFragmentManager().beginTransaction().remove(quizFragment).commit();
        }
    }

    //set the three listviews with one scroll
    private void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
//
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            if (listItem instanceof ViewGroup) {
//                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            }
//
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null)
//            return;
//
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
//        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
//        View view = null;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            view = listAdapter.getView(i, view, listView);
//            if (i == 0)
//                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight += view.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    //multi choice adapter
    private class MultichoiceAdapter extends ArrayAdapter<ArrayList<String>> {
        public MultichoiceAdapter(Context ctx) {
            super(ctx, 0);
        }

        LayoutInflater inflater = QuizPool.this.getLayoutInflater();
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
            correct.setText(getString(R.string.quiz_pool_crtAns) + getItem(position).get(5));
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
            String query = "SELECT * FROM " + tableName_multichoice + ";";
            c = db.rawQuery(query, null);
            return c.getCount();
        }

        @Override
        public ArrayList<String> getItem(int position){
            String query = "SELECT * FROM " + tableName_multichoice + ";";
            c = db.rawQuery(query, null);
            ArrayList<String> oneQuestion = new ArrayList<>();

            c.moveToPosition(position);
            oneQuestion.add(c.getString(c.getColumnIndex(keyQues)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyAnswerA)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyAnswerB)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyAnswerC)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyAnswerD)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyCorrect)));
            return oneQuestion;
        }

        @Override
        public long getItemId(int position){
            String query = "SELECT * FROM " + tableName_multichoice + ";";
            c = db.rawQuery(query, null);
            c.moveToPosition(position);
            int id = c.getInt(c.getColumnIndex(keyID));
            return id;
        }
    }

    //true false adapter
    private class TrueFalseAdapter extends ArrayAdapter<ArrayList<String>> {
        public TrueFalseAdapter(Context ctx) {
            super(ctx, 0);
        }

        LayoutInflater inflater = QuizPool.this.getLayoutInflater();
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View result;
            RadioGroup checkAnswer;
            TextView question;
            TextView answerA;
            TextView answerB;
            TextView correct;

            result = inflater.inflate(R.layout.quiz_true_false_layout, null);
            checkAnswer = result.findViewById(R.id.radioCheck);
            question = result.findViewById(R.id.quizList);
            answerA = result.findViewById(R.id.quiz_Answer_A);
            answerB = result.findViewById(R.id.quiz_Answer_B);
            correct = result.findViewById(R.id.tv_correct);

            question.setText("Q: " + getItem(position).get(0)); // get the string at position
            answerA.setText("True");
            answerB.setText("False");
            correct.setText(getString(R.string.quiz_pool_crtAns) + getItem(position).get(1));
            if(getItem(position).get(1).equalsIgnoreCase("True")){
                checkAnswer.check(R.id.quiz_Answer_A);
            }else if(getItem(position).get(1).equalsIgnoreCase("False")){
                checkAnswer.check(R.id.quiz_Answer_B);
            }else{
                throw new IllegalArgumentException("No such answer accepted!");
            }

            return result;
        }

        @Override
        public int getCount(){
            String query = "SELECT * FROM " + tableName_truefalse + ";";
            c = db.rawQuery(query, null);
            return c.getCount();
        }

        @Override
        public ArrayList<String> getItem(int position){
            String query = "SELECT * FROM " + tableName_truefalse + ";";
            c = db.rawQuery(query, null);
            ArrayList<String> oneQuestion = new ArrayList<>();

            c.moveToPosition(position);
            oneQuestion.add(c.getString(c.getColumnIndex(keyQues)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyCorrect)));
            return oneQuestion;
        }

        @Override
        public long getItemId(int position){
            String query = "SELECT * FROM " + tableName_truefalse + ";";
            c = db.rawQuery(query, null);
            c.moveToPosition(position);
            int id = c.getInt(c.getColumnIndex(keyID));
            return id;
        }
    }

    //numeric adapter
    private class NumericAdapter extends ArrayAdapter<ArrayList<String>> {
        public NumericAdapter(Context ctx) {
            super(ctx, 0);
        }

        LayoutInflater inflater = QuizPool.this.getLayoutInflater();
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View result;
            TextView question;
            TextView answerA;
            TextView correct;

            result = inflater.inflate(R.layout.quiz_numeric_layout, null);
            question = result.findViewById(R.id.quizList);
            answerA = result.findViewById(R.id.quiz_Answer_A);
            correct = result.findViewById(R.id.tv_correct);

            question.setText("Q: " + getItem(position).get(0)); // get the string at position
            answerA.setText(getString(R.string.quiz_pool_numeric) + getItem(position).get(1));
            correct.setText(getString(R.string.quiz_pool_crtAns) + getItem(position).get(2));

            return result;
        }

        @Override
        public int getCount(){
            String query = "SELECT * FROM " + tableName_numeric + ";";
            c = db.rawQuery(query, null);
            return c.getCount();
        }

        @Override
        public ArrayList<String> getItem(int position){
            String query = "SELECT * FROM " + tableName_numeric + ";";
            c = db.rawQuery(query, null);
            ArrayList<String> oneQuestion = new ArrayList<>();

            c.moveToPosition(position);
            oneQuestion.add(c.getString(c.getColumnIndex(keyQues)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyAnswerA)));
            oneQuestion.add(c.getString(c.getColumnIndex(keyCorrect)));
            return oneQuestion;
        }

        @Override
        public long getItemId(int position){
            String query = "SELECT * FROM " + tableName_numeric + ";";
            c = db.rawQuery(query, null);
            c.moveToPosition(position);
            int id = c.getInt(c.getColumnIndex(keyID));
            return id;
        }
    }

}