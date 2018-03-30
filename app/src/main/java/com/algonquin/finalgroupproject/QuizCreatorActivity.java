package com.algonquin.finalgroupproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
