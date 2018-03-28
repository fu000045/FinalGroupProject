package com.algonquin.finalgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuizCreatorActivity extends Activity {

    LinearLayout linearlayout;
    Button btn_createAQuiz;
    Button btn_quizPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_creator);

        linearlayout = findViewById(R.id.linearlayout);
        btn_createAQuiz = findViewById(R.id.Create_Quiz);
        btn_quizPool = findViewById(R.id.View_Quiz_Pool);

        //From: Android Snackbar Example Tutorial [Web Page]
        //Retrieved from: https://www.journaldev.com/10324/android-snackbar-example-tutorial
        btn_createAQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(linearlayout, "Create a quiz from the pool!", Snackbar.LENGTH_LONG);
//                        .setAction("RETRY", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                            }
//                        });
//                snackbar.setActionTextColor(Color.RED);
//                View sbView = snackbar.getView();
//                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//                textView.setTextColor(Color.YELLOW);
                snackbar.show();

//                //Jump to ViewQuizPool.
//                Intent intent = new Intent(QuizCreatorActivity.this, CreateAQuiz.class);
//                startActivity(intent);
            }
        });

        btn_quizPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Jump to ViewQuizPool.
                Intent intent = new Intent(QuizCreatorActivity.this, ViewQuizPool.class);
                startActivity(intent);
            }
        });
    }
}
