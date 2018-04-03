package com.algonquin.finalgroupproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuizCreatorActivity extends AppCompatActivity {

    LinearLayout linearlayout;
    Button btn_createAQuiz;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.quiz_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_creator);

        linearlayout = findViewById(R.id.linearlayout);
        btn_createAQuiz = findViewById(R.id.Create_Quiz);

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
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.viewquizpool:
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //Jump to QuizCreaterActivity.
                        Intent intent = new Intent(QuizCreatorActivity.this, QuizMultiChoicePool.class);
                        startActivity(intent);
                        return true;
                    }
                });
                break;

            case R.id.help:
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
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
                        return true;
                    }
                });
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
