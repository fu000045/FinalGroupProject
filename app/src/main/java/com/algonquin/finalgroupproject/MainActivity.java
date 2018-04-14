package com.algonquin.finalgroupproject;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearlayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearlayout = findViewById(R.id.linearlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
 //       ActionBar ab = getSupportActionBar();
 //      ab.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.Quiz:
                Snackbar snackbar = Snackbar
                        .make(linearlayout, "Quiz Creator Selected!", Snackbar.LENGTH_LONG);
                snackbar.show();

                //Jump to QuizCreaterActivity.
                Intent intent = new Intent(MainActivity.this, QuizCreatorActivity.class);
                startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
