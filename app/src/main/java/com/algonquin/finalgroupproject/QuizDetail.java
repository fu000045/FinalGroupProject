package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizDetail extends Activity {
    TextView txt_questionMultiChoice;
    TextView txt_answerA;
    TextView txt_answerB;
    TextView txt_answerC;
    TextView txt_answerD;
    Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_detail);

        txt_questionMultiChoice = findViewById(R.id.Question_multichoice_framelayout);
        txt_answerA = findViewById(R.id.AnswerA_multichoice_framelayout);
        txt_answerB = findViewById(R.id.AnswerB_multichoice_framelayout);
        txt_answerC = findViewById(R.id.AnswerC_multichoice_framelayout);
        txt_answerD = findViewById(R.id.AnswerD_multichoice_framelayout);
        btn_delete = findViewById(R.id.delete_question);

        //start a FragmentTransaction to add a fragment to the FrameLayout
        QuizMultiChoiceFragment myFragment = new QuizMultiChoiceFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.Quiz_MultiChoice_Framlayout, myFragment).addToBackStack(null).commit();

        //Get message passed by ViewQuizPool
        Bundle bundle = getIntent().getBundleExtra("QuizMultiChoiceDetail");
        String question = bundle.getString("Question");
        String answerA = bundle.getString("AnswerA");
        String answerB = bundle.getString("AnswerB");
        String answerC = bundle.getString("AnswerC");
        String answerD = bundle.getString("AnswerD");
        txt_questionMultiChoice.setText(question);
        txt_answerA.setText(String.valueOf(answerA));
        txt_answerB.setText(String.valueOf(answerB));
        txt_answerC.setText(String.valueOf(answerC));
        txt_answerD.setText(String.valueOf(answerD));

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizDetail.this);
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.quiz_dialog_message)
                        .setTitle(R.string.quiz_dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button to end the activity to go back to the StartActivity()
//                                Intent resultIntent = new Intent();
//                                resultIntent.putExtra("Response", msg);
//                                setResult(Activity.RESULT_OK, resultIntent);
//                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
            }
        });
    }
}
