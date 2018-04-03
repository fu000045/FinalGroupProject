package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by Yali Fu on 3/24/2018.
 */

public class QuizMultiChoiceFragment extends Fragment {

    //used to judge if it's a tablet or phone
    public boolean isTablet;
    EditText questionMultiChoice;
    CheckBox check_answerA;
    CheckBox check_answerB;
    CheckBox check_answerC;
    CheckBox check_answerD;
    EditText text_answerA;
    EditText text_answerB;
    EditText text_answerC;
    EditText text_answerD;
    Button btn_update;
    Button btn_delete;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_multi_choice_fragment, container, false);

        questionMultiChoice = view.findViewById(R.id.Question_multichoice_fragment);
        check_answerA = view.findViewById(R.id.AnswerA_multichoice_fragment);
        check_answerB = view.findViewById(R.id.AnswerB_multichoice_fragment);
        check_answerC = view.findViewById(R.id.AnswerC_multichoice_fragment);
        check_answerD = view.findViewById(R.id.AnswerD_multichoice_fragment);
        text_answerA = view.findViewById(R.id.txt_answerA);
        text_answerB = view.findViewById(R.id.txt_answerB);
        text_answerC = view.findViewById(R.id.txt_answerC);
        text_answerD = view.findViewById(R.id.txt_answerD);
        btn_update = view.findViewById(R.id.update_question);
        btn_delete = view.findViewById(R.id.delete_question);

        //Get message passed by tablet(chatwindow) or phone(messagedetails)
        bundle = getArguments();

        //Get message passed by QuizMultiChoicePool
        final String question = bundle.getString("Question");
        final String answerA = bundle.getString("AnswerA");
        final String answerB = bundle.getString("AnswerB");
        final String answerC = bundle.getString("AnswerC");
        final String answerD = bundle.getString("AnswerD");
        final String correct = bundle.getString("Correct");
        final long Id = bundle.getLong("ID");

        questionMultiChoice.setText(question);
        check_answerA.setText("A: ");
        text_answerA.setText(answerA);
        check_answerB.setText("B: ");
        text_answerB.setText(answerB);
        check_answerC.setText("C: ");
        text_answerC.setText(answerC);
        check_answerD.setText("D: ");
        text_answerD.setText(answerD);
        if(correct.equalsIgnoreCase("A")){
            check_answerA.setChecked(true);
        }else if(correct.equalsIgnoreCase("B")){
            check_answerB.setChecked(true);
        }else if(correct.equalsIgnoreCase("C")){
            check_answerC.setChecked(true);
        }else if(correct.equalsIgnoreCase("D")){
            check_answerD.setChecked(true);
        }else{
            throw new IllegalArgumentException("No such answer accepted!");
        }

        //only one checkbox can be selected, and set the correct answer
        check_answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_answerB.setChecked(false);
                check_answerC.setChecked(false);
                check_answerD.setChecked(false);
            }
        });
        check_answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_answerA.setChecked(false);
                check_answerC.setChecked(false);
                check_answerD.setChecked(false);
            }
        });
        check_answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_answerA.setChecked(false);
                check_answerB.setChecked(false);
                check_answerD.setChecked(false);
            }
        });
        check_answerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_answerA.setChecked(false);
                check_answerB.setChecked(false);
                check_answerC.setChecked(false);
            }
        });



        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newQues = questionMultiChoice.getText().toString();
                String newAnsA = text_answerA.getText().toString();
                String newAnsB = text_answerB.getText().toString();
                String newAnsC = text_answerC.getText().toString();
                String newAnsD = text_answerD.getText().toString();
                String newCrt = "";
                if(check_answerA.isChecked()){
                    newCrt = "A";
                }else if(check_answerB.isChecked()){
                    newCrt = "B";
                }else if(check_answerC.isChecked()){
                    newCrt = "C";
                }else if(check_answerD.isChecked()){
                    newCrt = "D";
                }
                if(isTablet) {
                    QuizMultiChoicePool quizpool = (QuizMultiChoicePool)getActivity();
                    quizpool.updateForTablet(Id, newQues, newAnsA, newAnsB, newAnsC, newAnsD, newCrt);
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("ID", Id);
                    resultIntent.putExtra("Question", newQues);
                    resultIntent.putExtra("AnswerA", newAnsA);
                    resultIntent.putExtra("AnswerB", newAnsB);
                    resultIntent.putExtra("AnswerC", newAnsC);
                    resultIntent.putExtra("AnswerD", newAnsD);
                    resultIntent.putExtra("Correct", newCrt);
                    getActivity().setResult(666, resultIntent);
                    getActivity().finish();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizMultiChoiceFragment.this.getActivity());
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.quiz_dialog_message)
                        .setTitle(R.string.quiz_dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button to end the activity and go back to the previous activity
                                if(isTablet) {
                                    QuizMultiChoicePool quizpool = (QuizMultiChoicePool)getActivity();
                                    quizpool.deleteForTablet(Id);
                                } else {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("ID", Id);
                                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                                    getActivity().finish();
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
            }
        });

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setIsTablet(boolean b){
        isTablet = b;
    }
}
