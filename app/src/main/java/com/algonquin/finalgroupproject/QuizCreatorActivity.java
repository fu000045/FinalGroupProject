package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class QuizCreatorActivity extends Activity {

    LinearLayout linearlayout;
    Button btn_viewQuizPool;
    Button btn_quizImport;
    Button btn_quizStat;
    Button btn_quizHelp;
    Button btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_creator);

        linearlayout = findViewById(R.id.linearlayout);
        btn_viewQuizPool = findViewById(R.id.View_Quiz_Pool);
        btn_quizImport = findViewById(R.id.Quiz_Import);
        btn_quizStat = findViewById(R.id.Quiz_Statistics);
        btn_quizHelp = findViewById(R.id.Quiz_help);
        btn_return = findViewById(R.id.Return);

        btn_viewQuizPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Jump to QuizPool Activity.
                Intent intent = new Intent(QuizCreatorActivity.this, QuizPool.class);
                startActivity(intent);
            }
        });

        //parser from server
        btn_quizImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Jump to QuizParser Activity.
                Intent intent = new Intent(QuizCreatorActivity.this, QuizParser.class);
                startActivity(intent);
            }
        });

        //Statistics
        btn_quizStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Jump to QuizStat Activity.
                Intent intent = new Intent(QuizCreatorActivity.this, QuizStat.class);
                startActivity(intent);
            }
        });

        //Help dialogue box.
        btn_quizHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(linearlayout, getString(R.string.toast_quizHelp), Snackbar.LENGTH_LONG);
                snackbar.show();

                LayoutInflater inflater = QuizCreatorActivity.this.getLayoutInflater();

                //Pop up a dialogue box.
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizCreatorActivity.this);
                builder.setView(inflater.inflate(R.layout.quiz_help_layout, null));
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setTitle(R.string.quiz_help)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .show();
            }
        });

        //Return to MainActivity
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuizCreatorActivity.this, R.string.toast_quiz, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuizCreatorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
