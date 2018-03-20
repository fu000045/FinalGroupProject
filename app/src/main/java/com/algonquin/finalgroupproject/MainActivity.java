package com.algonquin.finalgroupproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn_quizCreater;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

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
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(MainActivity.this, "Quiz Creater Selected!", Toast.LENGTH_SHORT).show();
                        //Jump to QuizCreaterActivity.
                        Intent intent = new Intent(MainActivity.this, QuizCreatorActivity.class);
                        startActivity(intent);
                        return true;
                    }
                });
                //break;
//
            case R.id.Patient:
                    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            Toast.makeText(MainActivity.this, "Patient form Selected!", Toast.LENGTH_SHORT).show();
                            //Jump to PatientFormActivity.
                            Intent intent = new Intent(MainActivity.this, PatientIntakeFormActivity.class);
                            startActivity(intent);
                            return true;
                        }
                    });
                   // break;

            default:
               // break;
                return super.onOptionsItemSelected(item);
        }
    }
}
