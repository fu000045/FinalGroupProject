package com.algonquin.finalgroupproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.algonquin.finalgroupproject.octranspo.OCBusNumberWindow;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearlayout;
    //private Button btn_movieInfo;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearlayout = findViewById(R.id.linearlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;

        switch (item.getItemId()){
            case R.id.Quiz:
                Toast.makeText(MainActivity.this, getString(R.string.toast_quizCreator), Toast.LENGTH_SHORT).show();
                //Jump to QuizCreaterActivity.
                intent = new Intent(MainActivity.this, QuizCreatorActivity.class);
                startActivity(intent);
                break;

            case R.id.Movie:
                Toast.makeText(MainActivity.this, "Movie List Selected!", Toast.LENGTH_SHORT).show();
                //Jump to MovieActivity.
                intent = new Intent(MainActivity.this, MovieActivity.class);
                startActivity(intent);
                break;

            case R.id.OCTrans:
                Toast.makeText(MainActivity.this, getString(R.string.oc_toast), Toast.LENGTH_SHORT).show();
                //Jump to OCTransActivity.
                intent = new Intent(MainActivity.this, OCBusNumberWindow.class);
                startActivity(intent);
                break;

            case R.id.Patient:
                 Toast.makeText(MainActivity.this, "Patient form Selected!", Toast.LENGTH_SHORT).show();
                 //Jump to PatientFormActivity.
                 intent = new Intent(MainActivity.this, PatientIntakeFormActivity.class);
                 startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

}
