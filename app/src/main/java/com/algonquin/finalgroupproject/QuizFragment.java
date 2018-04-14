package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by Yali Fu on 3/24/2018.
 */

public class QuizFragment extends Fragment {

    //used to judge if it's a tablet or phone
    public boolean isTablet;
    EditText txt_quesiton;
    CheckBox check_answerA;
    CheckBox check_answerB;
    CheckBox check_answerC;
    CheckBox check_answerD;
    EditText text_answerA;
    EditText text_answerB;
    EditText text_answerC;
    EditText text_answerD;
    EditText text_correct;
    Button btn_add;
    Button btn_cancel;
    Button btn_update;
    Button btn_delete;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //Get message passed by tablet(chatwindow) or phone(messagedetails)
        bundle = getArguments();

        View view = null;

        if(bundle.getString("QuestionType").equals("MultiChoice")){
            view = inflater.inflate(R.layout.quiz_multi_choice_fragment, container, false);

            txt_quesiton = view.findViewById(R.id.Question_multichoice_fragment);
            check_answerA = view.findViewById(R.id.AnswerA_multichoice_fragment);
            check_answerB = view.findViewById(R.id.AnswerB_multichoice_fragment);
            check_answerC = view.findViewById(R.id.AnswerC_multichoice_fragment);
            check_answerD = view.findViewById(R.id.AnswerD_multichoice_fragment);
            text_answerA = view.findViewById(R.id.txt_answerA);
            text_answerB = view.findViewById(R.id.txt_answerB);
            text_answerC = view.findViewById(R.id.txt_answerC);
            text_answerD = view.findViewById(R.id.txt_answerD);
            btn_add = view.findViewById(R.id.add_question);
            btn_cancel = view.findViewById(R.id.cancel_question);
            btn_update = view.findViewById(R.id.update_question);
            btn_delete = view.findViewById(R.id.delete_question);

            //Get message passed by QuizPool
            final String questionType = bundle.getString("QuestionType");
            String question = bundle.getString("Question");
            String answerA = bundle.getString("AnswerA");
            String answerB = bundle.getString("AnswerB");
            String answerC = bundle.getString("AnswerC");
            String answerD = bundle.getString("AnswerD");
            String correct = bundle.getString("Correct");
            final long Id = bundle.getLong("ID");

            if(question == null){
                Log.i("fragment", "add question");
                btn_update.setVisibility(View.INVISIBLE);
                btn_delete.setVisibility(View.INVISIBLE);
            }else{
                btn_add.setVisibility(View.INVISIBLE);
                txt_quesiton.setText(question);
                text_answerA.setText(answerA);
                text_answerB.setText(answerB);
                text_answerC.setText(answerC);
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

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newQues = txt_quesiton.getText().toString();
                    String newAnsA = text_answerA.getText().toString();
                    String newAnsB = text_answerB.getText().toString();
                    String newAnsC = text_answerC.getText().toString();
                    String newAnsD = text_answerD.getText().toString();
                    String newCrt = "";

                    if((newQues.isEmpty() || newQues.trim().isEmpty())
                            || (newAnsA.isEmpty() || newAnsA.trim().isEmpty())
                            || (newAnsB.isEmpty() || newAnsB.trim().isEmpty())
                            || (newAnsC.isEmpty() || newAnsC.trim().isEmpty())
                            || (newAnsD.isEmpty() || newAnsD.trim().isEmpty())){
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(QuizFragment.this.getActivity());
                        builder.setMessage(R.string.quiz_multichoice_dialog_message)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                })
                                .show();
                    }

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
                        QuizPool quizpool = (QuizPool)getActivity();
                        quizpool.addForTablet(questionType, Id, newQues, newAnsA, newAnsB, newAnsC, newAnsD, newCrt);
                    } else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ID", Id);
                        resultIntent.putExtra("QuestionType", "MultiChoice");
                        resultIntent.putExtra("Question", newQues);
                        resultIntent.putExtra("AnswerA", newAnsA);
                        resultIntent.putExtra("AnswerB", newAnsB);
                        resultIntent.putExtra("AnswerC", newAnsC);
                        resultIntent.putExtra("AnswerD", newAnsD);
                        resultIntent.putExtra("Correct", newCrt);
                        getActivity().setResult(222, resultIntent);
                        getActivity().finish();
                    }
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newQues = txt_quesiton.getText().toString();
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
                        QuizPool quizpool = (QuizPool)getActivity();
                        quizpool.updateForTablet(questionType, Id, newQues, newAnsA, newAnsB, newAnsC, newAnsD, newCrt);
                    } else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ID", Id);
                        resultIntent.putExtra("QuestionType", "MultiChoice");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuizFragment.this.getActivity());
                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(R.string.quiz_dialog_message)
                            .setTitle(R.string.quiz_dialog_title)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button to end the activity and go back to the previous activity
                                    if(isTablet) {
                                        QuizPool quizpool = (QuizPool)getActivity();
                                        quizpool.deleteForTablet(questionType, Id);
                                    } else {
                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("ID", Id);
                                        resultIntent.putExtra("QuestionType", "MultiChoice");
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
            //true false
        }else if(bundle.getString("QuestionType").equals("TrueFalse")){
            view = inflater.inflate(R.layout.quiz_true_false_fragment, container, false);

            txt_quesiton = view.findViewById(R.id.Question_truefalse_fragment);
            check_answerA = view.findViewById(R.id.AnswerA_truefalse_fragment);
            check_answerB = view.findViewById(R.id.AnswerB_truefalse_fragment);
            btn_add = view.findViewById(R.id.add_question);
            btn_cancel = view.findViewById(R.id.cancel_question);
            btn_update = view.findViewById(R.id.update_question);
            btn_delete = view.findViewById(R.id.delete_question);

            //Get message passed by QuizPool
            final String questionType = bundle.getString("QuestionType");
            String question = bundle.getString("Question");
            String correct = bundle.getString("Correct");
            final long Id = bundle.getLong("ID");

            if(question == null){
                Log.i("fragment", "add question");
                btn_update.setVisibility(View.INVISIBLE);
                btn_delete.setVisibility(View.INVISIBLE);
            }else{
                btn_add.setVisibility(View.INVISIBLE);
                txt_quesiton.setText(question);
                if(correct.equalsIgnoreCase("True")){
                    check_answerA.setChecked(true);
                }else if(correct.equalsIgnoreCase("False")){
                    check_answerB.setChecked(true);
                }else{
                    throw new IllegalArgumentException("No such answer accepted!");
                }
            }

            //only one checkbox can be selected, and set the correct answer
            check_answerA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    check_answerB.setChecked(false);
                }
            });
            check_answerB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    check_answerA.setChecked(false);
                }
            });

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newQues = txt_quesiton.getText().toString();
                    String newCrt = "";

                    if((newQues.isEmpty() || newQues.trim().isEmpty())
                            || (check_answerA.isChecked()==false &&check_answerB.isChecked()==false)){
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(QuizFragment.this.getActivity());
                        builder.setMessage(R.string.quiz_multichoice_dialog_message)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                })
                                .show();
                    }

                    if(check_answerA.isChecked()){
                        newCrt = "True";
                    }else if(check_answerB.isChecked()){
                        newCrt = "False";
                    }
                    if(isTablet) {
                        QuizPool quizpool = (QuizPool)getActivity();
                        quizpool.addForTablet(questionType, Id, newQues, null, null, null, null, newCrt);
                    } else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ID", Id);
                        resultIntent.putExtra("QuestionType", "TrueFalse");
                        resultIntent.putExtra("Question", newQues);
                        resultIntent.putExtra("Correct", newCrt);
                        getActivity().setResult(222, resultIntent);
                        getActivity().finish();
                    }
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newQues = txt_quesiton.getText().toString();
                    String newCrt = "";
                    if(check_answerA.isChecked()){
                        newCrt = "True";
                    }else if(check_answerB.isChecked()){
                        newCrt = "False";
                    }
                    if(isTablet) {
                        QuizPool quizpool = (QuizPool)getActivity();
                        quizpool.updateForTablet(questionType, Id, newQues, null, null, null, null, newCrt);
                    } else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ID", Id);
                        resultIntent.putExtra("QuestionType", "TrueFalse");
                        resultIntent.putExtra("Question", newQues);
                        resultIntent.putExtra("Correct", newCrt);
                        getActivity().setResult(666, resultIntent);
                        getActivity().finish();
                    }
                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuizFragment.this.getActivity());
                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(R.string.quiz_dialog_message)
                            .setTitle(R.string.quiz_dialog_title)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button to end the activity and go back to the previous activity
                                    if(isTablet) {
                                        QuizPool quizpool = (QuizPool)getActivity();
                                        quizpool.deleteForTablet(questionType, Id);
                                    } else {
                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("ID", Id);
                                        resultIntent.putExtra("QuestionType", "TrueFalse");
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
            //for numeric
        }else if(bundle.getString("QuestionType").equals("Numeric")){
            view = inflater.inflate(R.layout.quiz_numeric_fragment, container, false);

            txt_quesiton = view.findViewById(R.id.Question_numeric_fragment);
            text_answerA = view.findViewById(R.id.AnswerA_numeric_fragment);
            text_correct = view.findViewById(R.id.Correct_numeric_fragment);
            btn_add = view.findViewById(R.id.add_question);
            btn_cancel = view.findViewById(R.id.cancel_question);
            btn_update = view.findViewById(R.id.update_question);
            btn_delete = view.findViewById(R.id.delete_question);

            //Get message passed by QuizPool
            final String questionType = bundle.getString("QuestionType");
            String question = bundle.getString("Question");
            String answerA = bundle.getString("AnswerA");
            String correct = bundle.getString("Correct");
            final long Id = bundle.getLong("ID");

            if(question == null){
                Log.i("fragment", "add question");
                btn_update.setVisibility(View.INVISIBLE);
                btn_delete.setVisibility(View.INVISIBLE);
            }else{
                btn_add.setVisibility(View.INVISIBLE);
                txt_quesiton.setText(question);
                text_answerA.setText(answerA);
                text_correct.setText(correct);
            }

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newQues = txt_quesiton.getText().toString();
                    String newAnsA = text_answerA.getText().toString();
                    String newCrt = text_correct.getText().toString();

                    if((newQues.isEmpty() || newQues.trim().isEmpty())
                            || (newAnsA.isEmpty() || newAnsA.trim().isEmpty())
                            || (newCrt.isEmpty() || newCrt.trim().isEmpty())){
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(QuizFragment.this.getActivity());
                        builder.setMessage(R.string.quiz_multichoice_dialog_message)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                })
                                .show();
                    }

                    if(isTablet) {
                        QuizPool quizpool = (QuizPool)getActivity();
                        quizpool.addForTablet(questionType, Id, newQues, newAnsA, null, null, null, newCrt);
                    } else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ID", Id);
                        resultIntent.putExtra("QuestionType", "Numeric");
                        resultIntent.putExtra("Question", newQues);
                        resultIntent.putExtra("AnswerA", newAnsA);
                        resultIntent.putExtra("Correct", newCrt);
                        getActivity().setResult(222, resultIntent);
                        getActivity().finish();
                    }
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newQues = txt_quesiton.getText().toString();
                    String newAnsA = text_answerA.getText().toString();
                    String newCrt = text_correct.getText().toString();
                    if(isTablet) {
                        QuizPool quizpool = (QuizPool)getActivity();
                        quizpool.updateForTablet(questionType, Id, newQues, newAnsA, null, null, null, newCrt);
                    } else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ID", Id);
                        resultIntent.putExtra("QuestionType", "Numeric");
                        resultIntent.putExtra("Question", newQues);
                        resultIntent.putExtra("AnswerA", newAnsA);
                        resultIntent.putExtra("Correct", newCrt);
                        getActivity().setResult(666, resultIntent);
                        getActivity().finish();
                    }
                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuizFragment.this.getActivity());
                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(R.string.quiz_dialog_message)
                            .setTitle(R.string.quiz_dialog_title)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button to end the activity and go back to the previous activity
                                    if(isTablet) {
                                        QuizPool quizpool = (QuizPool)getActivity();
                                        quizpool.deleteForTablet(questionType, Id);
                                    } else {
                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("ID", Id);
                                        resultIntent.putExtra("QuestionType", "Numeric");
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
        }
        //cancel button to return to quizpool activity
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuizPool.class);
                startActivity(intent);
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
